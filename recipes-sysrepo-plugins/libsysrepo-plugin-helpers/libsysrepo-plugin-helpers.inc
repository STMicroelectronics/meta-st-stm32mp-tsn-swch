SUMMARY = "Library for accessing system data for YANG modules"
SECTION = "tsn-base"

PR = "r1"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/libsysrepo-plugin-helpers"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} = "${libdir}/libsysrepo-plugin-helpers*.so.*"
FILES:${PN}-dev = "${libdir}/libsysrepo-plugin-helpers.so ${libdir}/libsysrepo-plugin-helpers.a ${includedir}/sysrepo-plugin-helpers/*"

do_install() {
    mkdir -p ${D}${libdir} ${D}${includedir}/sysrepo-plugin-helpers
    cp -d ${B}/libsysrepo-plugin-helpers* ${D}${libdir}/
    install -m 0644 ${S}/include/*.h ${D}/${includedir}/sysrepo-plugin-helpers
}
