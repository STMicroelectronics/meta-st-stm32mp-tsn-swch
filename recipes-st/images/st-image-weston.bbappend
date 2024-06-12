CORE_IMAGE_EXTRA_INSTALL += " \
    bridge-utils \
    lldpd \
    mstpd \
    tsntool \
    restart-script \
    de-ptp-bin \
    ietf-ptp-yang-module \
    ietf-interfaces-yang-module \
    ieee802-dot1q-bridge-yang-module \
    ieee802-dot1q-fqtss-yang-module \
    ieee802-dot1ab-lldp-yang-module \
    ieee8021-mstp-yang-module \
    sysrepo-module-versions-yang-module \
    ieee802-dot1cb-frer-yang-module \
    ieee802-dot1cb-stream-identification-yang-module \
    ieee802-dot1cb-stream-identification-params-yang-module \
    netopeer2-server \
    \
    ttt-ip-init \
    "

# MIB file
CORE_IMAGE_EXTRA_INSTALL += " \
    ieee-8021-bridge-mib \
    ieee-8021-mstp-mib   \
    ieee8021qbridgemib \
    net-snmp-server \
"
# tools
CORE_IMAGE_EXTRA_INSTALL += " \
    linuxptp \
    linuxptp-configs \
    iperf3 \
    iproute2-tc \
    iproute2-devlink \
    openssh-keygen \
    ebtables \
"
# kernel addons
CORE_IMAGE_EXTRA_INSTALL += " \
    kernel-module-edge \
    kernel-module-st-stm32-deip \
"
