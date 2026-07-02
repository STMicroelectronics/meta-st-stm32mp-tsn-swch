
# ----------------------------------------------
# list of packages to use for switch usage only
TSN_PACKAGES_SWITCH_systemd ?= " \
    ttt-ip-st-common \
    \
    ttt-ip-switch \
    "
TSN_PACKAGES_SWITCH_sysvinit ?= " \
    ttt-ip-switch-sysvinit \
    "

TSN_PACKAGES_SWITCH ?= "${@bb.utils.contains('DISTRO_FEATURES','systemd','${TSN_PACKAGES_SWITCH_systemd}','${TSN_PACKAGES_SWITCH_sysvinit}',d)}"


#  ----------------------------------------------
# list of packages to use for tsn
TSN_PACKAGES_TSN_COMMON ?= " \
    bridge-utils \
    lldpd \
    mstpd \
    tsntool \
    restart-script \
    de-ptp-bin \
    netopeer2-server \
    \
    ieee-8021-bridge-mib \
    ieee-8021-mstp-mib   \
    ieee8021qbridgemib \
    net-snmp-server \
    \
    sysrepo-plugin-interfaces \
    sysrepo-plugin-bridge-port \
    sysrepo-plugin-cbs \
    sysrepo-plugin-lldp \
    sysrepo-plugin-bridge \
    sysrepo-plugin-mstp \
    sysrepo-plugin-ieee1588-ptp \
    sysrepo-plugin-module-versions \
    sysrepo-plugin-frer \
    sysrepo-plugin-sid \
    sysrepo-plugin-sid-params \
    sysrepo-plugin-end-of-init \
"

# ----------------------------------------------
# list of packages to use for tsn with networkd with usage only
TSN_PACKAGES_TSN_NETWORKD_systemd  ?= " \
    ${TSN_PACKAGES_TSN_COMMON} \
    \
    ttt-ip-st-common \
    \
    ttt-ip-switch \
    ttt-ip-tsn-systemd \
    "
TSN_PACKAGES_TSN_NETWORKD ?= "${@bb.utils.contains('DISTRO_FEATURES','systemd','${TSN_PACKAGES_TSN_NETWORKD_systemd}','${TSN_PACKAGES_SWITCH_sysvinit}',d)}"

# ----------------------------------------------
# list of packages to use for tsn with networkd with usage only
TSN_PACKAGES_TSN_MONO ?= " \
    ${TSN_PACKAGES_TSN_COMMON} \
    \
    ttt-ip-st-common \
    \
    ttt-ip-tsn \
    "

TSN_PACKAGES_TSN_NETWORKD ?= "${@bb.utils.contains('DISTRO_FEATURES','systemd','${TSN_PACKAGES_TSN_NETWORKD_systemd}','${TSN_PACKAGES_TSN_MONO}',d)}"

# ----------------------------------------------
TSN_PACKAGES ??= " \
    ${@bb.utils.contains('TSN_PROFILE','SWICTH','${TSN_PACKAGES_SWITCH}','',d)} \
    ${@bb.utils.contains('TSN_PROFILE','TSN_NETWORKD','${TSN_PACKAGES_TSN_NETWORKD}','',d)} \
    ${@bb.utils.contains('TSN_PROFILE','TSN_MONO','${TSN_PACKAGES_TSN_MONO}','',d)} \
    "
