SUMMARY = "Netopeer2 is a set of tools implementing network configuration tools based on the NETCONF Protocol."
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."
LICENSE = "TTTECH-license"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/ietf-interfaces-yang-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

SRC_URI += "file://0001-Patch-to-support-arm64-bits-compilation.patch"
PV = "st-1.6.8"

DEPENDS = "libbase libtsn libyang libnetconf2 sysrepo coreutils openssh openssl openssh-native libbsd"

FILES:${PN} += "${libdir}/sysrepo/* /etc/netopeer2/*"

inherit cmake pkgconfig

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
OECMAKE_C_FLAGS = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} ${TARGET_CPPFLAGS} -Wno-error=stringop-truncation -Wno-error=cpp"
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX=/usr -DCMAKE_BUILD_TYPE:String=Release -DSYSREPOCTL_EXECUTABLE=/usr/bin/sysrepoctl -DSYSREPOCFG_EXECUTABLE=/usr/bin/sysrepocfg -DCHMOD_EXECUTABLE=/bin/chmod "

do_install:append () {
    install -d ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ietf-interfaces.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/iana-if-type.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-preemption.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot3br-preempt.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-sched.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-ethernet-interface.yang ${D}/etc/netopeer2/yang
    # provided by ieee802-dot1q-bridge-yang-module
    #cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-bridge.yang ${D}/etc/netopeer2/yang
    #cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-bridge-delays.yang ${D}/etc/netopeer2/yang
    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}
INSANE_SKIP:${PN} = "file-rdeps"
