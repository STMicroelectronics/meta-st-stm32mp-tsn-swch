SUMMARY = "Netopeer2 is a set of tools implementing network configuration tools based on the NETCONF Protocol."
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."
LICENSE = "TTTECH-license"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

SRC_URI += "file://0001-Patch-to-support-arm64-bits-compilation.patch"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/ieee802-dot1q-bridge-yang-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

PR = "st-1.6.7"

DEPENDS = "libbase libtsn libyang libnetconf2 sysrepo coreutils openssh openssl openssh-native libbsd"

FILES:${PN} += "${libdir}/sysrepo/* /etc/netopeer2/*"

inherit cmake pkgconfig
# Needed to update dynamic library name in elf file
DEPENDS += "patchelf-native"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
OECMAKE_C_FLAGS = "${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} ${TARGET_CPPFLAGS} -Wno-error=format-truncation -Wno-error=cpp"
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX=/usr -DCMAKE_BUILD_TYPE:String=Release -DSYSREPOCTL_EXECUTABLE=/usr/bin/sysrepoctl -DSYSREPOCFG_EXECUTABLE=/usr/bin/sysrepocfg -DCHMOD_EXECUTABLE=/bin/chmod"

do_install:append () {
    install -d ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-bridge.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-bridge-delays.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-types.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-stream-filters-gates.yang ${D}/etc/netopeer2/yang
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-psfp.yang ${D}/etc/netopeer2/yang
    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
    #patch issue with libbase.so
    patchelf --replace-needed libbase.so libbase.so.1 ${D}${libdir}/sysrepo/plugins/libieee802-dot1q-bridge-yang-module.so
    patchelf --replace-needed libtsn.so libtsn.so.2 ${D}${libdir}/sysrepo/plugins/libieee802-dot1q-bridge-yang-module.so
}
INSANE_SKIP:${PN} = "file-rdeps"
