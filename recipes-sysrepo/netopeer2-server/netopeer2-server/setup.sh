#!/bin/bash

if [ $# -lt 2 ]; then
    echo "Usage $0 <sysrepoctl-path> <sysrepocfg-path> <netopeer2-modules-directory> [<module-owner>]"
    exit
fi

SYSREPOCTL=$1
SYSREPOCFG=$2
MODDIR=$3
OWNER=${4:-$(id -un)}
GROUP=${5:-$(id -gn $OWNER)}

# Args: {-i|-U} <yang-module-name>[@<revision>] [-e <feature>]...
install_module()
{
	op="$1"
	module="$2"
	shift 2
	echo "$module"
	# Must be done in two phases per module:
	# - Only one -i or -U per command allowed
	# - Cannot combine -e <feature> -o <owner> -g <group> with -i or -U
	# - Cannot use -c :ALL to set features
	$SYSREPOCTL $op $MODDIR/$module.yang -v3 -a -s $MODDIR
	$SYSREPOCTL -c ${module%%@*} "$@" -o $OWNER -g $GROUP -v3 -a
}
# sysrepo-plugin-bridge: ieee802-dot1q-types
install_module -i ieee802-dot1q-types
# netopeer2-server: ietf-netconf-acm
install_module -i ietf-netconf-acm@2018-02-14
# netopeer2-server: ietf-netconf
install_module -U ietf-netconf@2013-09-29 -e writable-running  -e candidate -e rollback-on-error -e validate -e startup -e url -e xpath
# netopeer2-server: ietf-netconf-monitoring
install_module -i ietf-netconf-monitoring@2010-10-04
# netopeer2-server: ietf-datastores
install_module -i ietf-datastores@2017-08-17
# netopeer2-server: ietf-netconf-nmda
install_module -i ietf-netconf-nmda@2019-01-07 -e origin -e with-defaults
# netopeer2-server: notification modules
install_module -i nc-notifications@2008-07-14
install_module -i notifications@2008-07-14
# netopeer2-server: ietf-netconf-server modules
install_module -i ietf-x509-cert-to-name@2014-12-10
install_module -i ietf-crypto-types@2019-07-02
install_module -i ietf-keystore@2019-07-02 -e keystore-supported
install_module -i ietf-truststore@2019-07-02 -e truststore-supported -e x509-certificates
install_module -i ietf-tcp-common@2019-07-02 -e keepalives-supported
install_module -i ietf-ssh-server@2019-07-02 -e local-client-auth-supported
install_module -i ietf-tls-server@2019-07-02 -e local-client-auth-supported
install_module -i ietf-netconf-server@2019-07-02 -e ssh-listen -e tls-listen -e ssh-call-home -e tls-call-home
# sysrepo-plugin-cbs
install_module -i ieee802-dot1q-cbsa
install_module -i ieee802-dot1q-cbsa-bridge -e credit-based-shaper-algorithm
# sysrepo-plugin-interfaces
install_module -i ietf-interfaces -e if-mib
# 
install_module -i ieee802-dot1q-bridge -e extended-filtering-services
# sysrepo-plugin-lldp
install_module -i ieee802-dot1ab-lldp
# sysrepo-plugin-interfaces
install_module -i ietf-routing
# 
install_module -i iana-if-type
# sysrepo-plugin-interfaces
install_module -i ieee802-dot1q-sched -e scheduled-traffic
install_module -i ieee802-dot1q-sched-bridge
install_module -i ieee802-dot1q-preemption
install_module -i ieee802-dot1q-preemption-bridge -e frame-preemption
install_module -i ieee802-ethernet-interface -e ethernet-pfc -e ethernet-pause
install_module -i ieee802-ethernet-mac-merge -e mac-merge
# sysrepo-plugin-module-versions
install_module -i sysrepo-module-versions
# sysrepo-plugin-mstp
install_module -i ieee802-dot1q-rstp
install_module -i ieee802-dot1q-rstp-bridge
install_module -i ieee802-dot1q-mstp
install_module -i ieee802-dot1q-mstp-bridge
# sysrepo-plugin-ieee1588-ptp
install_module -i ieee1588-ptp-tt
install_module -i ieee802-dot1as-gptp
# sysrepo-plugin-sid
install_module -i ieee802-dot1cb-stream-identification-types
install_module -i ieee802-dot1cb-stream-identification
# sysrepo-plugin-sid-params
install_module -i ieee802-dot1cb-stream-identification-params
# sysrepo-plugin-frer
install_module -i ieee802-dot1cb-frer-types
install_module -i ieee802-dot1cb-frer
# sysrepo-plugin-bridge
install_module -i ieee802-dot1q-stream-filters-gates
install_module -i ieee802-dot1q-stream-filters-gates-bridge
install_module -i ieee802-dot1q-psfp
install_module -i ieee802-dot1q-psfp-bridge
# sysrepo-plugin-bridge-port
install_module -i ieee802-dot1q-bridge-delays

echo "setup sysrepo plugins boot order"
$SYSREPOCFG --import=/usr/share/netopeer2-server/sysrepo_plugins_boot_order.xml --datastore=startup

cp -L /usr/share/sysrepo-plugin-module-versions/module-versions.data.xml \
	/var/lib/sysrepo/data/.

rm -f /dev/shm/sr_* /dev/shm/srsub_*
