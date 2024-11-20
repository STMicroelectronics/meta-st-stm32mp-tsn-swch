SUMMARY = "Netopeer2 is a set of tools implementing network configuration tools based on the NETCONF Protocol."
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."
LICENSE = "TTTECH-license"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/ieee1588-ptp-yang-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

PV = "st-1.6.8"

DEPENDS = "libbase de-ptp-bin libtsn libyang libnetconf2 sysrepo coreutils openssh openssl openssh-native libbsd"

FILES:${PN} += "${libdir}/sysrepo/* /etc/netopeer2/*"

#inherit cmake pkgconfig


# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX=${prefix} -DCMAKE_BUILD_TYPE:String=Release -DSYSREPOCTL_EXECUTABLE=${bindir}/sysrepoctl -DSYSREPOCFG_EXECUTABLE=${bindir}/sysrepocfg -DCHMOD_EXECUTABLE=/bin/chmod "
EXTRA_OECMAKE += "-DLIBPTP=${STAGING_LIBDIR}/libptp.so.0 -DLIBPTP_MANAGEMENT=${STAGING_LIBDIR}/libptp_management.so.0"

do_install:append() {
    install -d ${D}/etc/netopeer2/yang ${D}/usr/lib/sysrepo/plugins/
    if [ -d ${S}/binaries/ ]; then
        install -m 0644 ${S}/binaries/etc/netopeer2/yang/*.yang ${D}/etc/netopeer2/yang
        install -m 0644 ${S}/binaries/usr/lib/sysrepo/plugins/*.so ${D}/usr/lib/sysrepo/plugins/
    else
        install -m 0644 ${S}/../YANG_modules_repository/ieee1588-ptp.yang ${D}/etc/netopeer2/yang
    fi

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}
INSANE_SKIP:${PN} = "file-rdeps"

