#!/bin/bash

set -e

# avoid problems with sudo path
SYSREPOCFG=`su -c "which sysrepocfg" $USER`
OPENSSL=`su -c "which openssl" $USER`

# check that there is no SSH key with this name yet
KEYSTORE_KEY=`$SYSREPOCFG -X -x "/ietf-keystore:keystore/asymmetric-keys/asymmetric-key[name='genkey']/name"`
if [ -z "$KEYSTORE_KEY" ]; then

test -d /etc/ssh || mkdir -p /etc/ssh/

#test if ssh_host_rsa_key are present
if [ -e /etc/ssh/ssh_host_rsa_key ]; then
    rm -f /etc/ssh/ssh_host_rsa_key
fi

# force creation of ssh key
#$OPENSSL genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -outform PEM > /etc/ssh/ssh_host_rsa_key

# sysvinit
if [ -f /etc/init.d/dropbear ]; then
    rm -f /etc/ssh/ssh_host_rsa_key /etc/dropbear/dropbear_rsa_host_key
    /usr/sbin/dropbearkey -t rsa -f /etc/dropbear/dropbear_rsa_host_key -s 2048 2>/dev/null
    /usr/sbin/dropbearconvert dropbear openssh /etc/dropbear/dropbear_rsa_host_key /etc/ssh/ssh_host_rsa_key 2>/dev/null
fi
# systemd
if [ -f /lib/systemd/system/dropbear.socket ]; then
    rm -f /etc/ssh/ssh_host_rsa_key /etc/dropbear/dropbear_rsa_host_key /etc/ssh/ssh_host_openssh_key
    /usr/sbin/dropbearkey -t rsa -f /etc/dropbear/dropbear_rsa_host_key -s 2048 2>/dev/null
    /usr/sbin/dropbearconvert dropbear openssh /etc/dropbear/dropbear_rsa_host_key /etc/ssh/ssh_host_openssh_key 2>/dev/null
    cat /etc/ssh/ssh_host_openssh_key | ssh-keygen -N "" -m pem -f /etc/ssh/ssh_host_rsa_key
fi

PRIVPEM=$(cat /etc/ssh/ssh_host_rsa_key)
# remove header/footer
PRIVKEY=`grep -v -- "-----" - <<STDIN
$PRIVPEM
STDIN`
# get public key
PUBPEM=`$OPENSSL rsa -pubout 2>/dev/null <<STDIN
$PRIVPEM
STDIN`
# remove header/footer
PUBKEY=`grep -v -- "-----" - <<STDIN
$PUBPEM
STDIN`

# generate edit config
CONFIG="<keystore xmlns=\"urn:ietf:params:xml:ns:yang:ietf-keystore\">
    <asymmetric-keys>
        <asymmetric-key>
            <name>genkey</name>
            <algorithm>rsa2048</algorithm>
            <public-key>$PUBKEY</public-key>
            <private-key>$PRIVKEY</private-key>
        </asymmetric-key>
    </asymmetric-keys>
</keystore>"
TMPFILE=`mktemp -u`
printf -- "$CONFIG" > $TMPFILE

cat /etc/ssh/ssh_host_rsa_key | $OPENSSL rsa -pubout 2>/dev/null 1>/etc/ssh/ssh_host_rsa_key.pub

# apply it to startup and running
$SYSREPOCFG --edit=$TMPFILE -d startup -f xml -m ietf-keystore -v2
$SYSREPOCFG -C startup -m ietf-keystore -v2
# remove the tmp file
rm $TMPFILE
fi
