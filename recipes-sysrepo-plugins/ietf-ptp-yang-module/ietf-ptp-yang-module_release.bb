SUMMARY = "Netopeer2 is a set of tools implementing network configuration tools based on the NETCONF Protocol."
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."
LICENSE = "TTTECH-license"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/ietf-ptp-yang-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

PR = "st-1.6.7"

DEPENDS = "libbase de-ptp-bin libtsn libyang libnetconf2 sysrepo coreutils openssh openssl openssh-native libbsd"

FILES:${PN} += "${libdir}/sysrepo/* /etc/netopeer2/*"


do_install() {
    install -d ${D}/etc/netopeer2/yang ${D}/usr/lib/sysrepo/plugins
    cp -r ${S}//binaries/etc/netopeer2/yang/ietf-ptp.yang ${D}/etc/netopeer2/yang
    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
    cp ${S}/binaries/usr/lib/sysrepo/plugins/libietf-ptp.so ${D}/usr/lib/sysrepo/plugins
    #patch issue with libbase.so
    #patchelf --replace-needed libbase.so libbase.so.1 ${D}${libdir}/sysrepo/plugins/libietf-ptp.so
}
INSANE_SKIP:${PN} = "file-rdeps already-stripped"
