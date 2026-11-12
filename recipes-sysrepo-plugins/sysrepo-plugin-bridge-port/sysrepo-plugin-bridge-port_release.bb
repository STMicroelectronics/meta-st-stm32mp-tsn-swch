SUMMARY = "Netopeer2 is a set of tools implementing network configuration tools based on the NETCONF Protocol."
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."

require ../sysrepo-plugins-common.inc
require sysrepo-plugin-bridge-port.inc

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
OECMAKE_C_FLAGS = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} ${TARGET_CPPFLAGS} -Wno-error=stringop-truncation -Wno-error=cpp"
