SUMMARY = "Library for accessing system data for YANG modules"
SECTION = "tsn-base"

PR = "r1"
PV = "st-1.6.10"

# ------------------------------------------------------------------
TTTECH_BINARY_ARCH = "aarch64"
TTTECH_BINARY_DATE = "2026-09-29"
TTTECH_BINARY_TARBALL = "TTTECH-sysrepo-plugins-${TTTECH_BINARY_ARCH}-${TTTECH_BINARY_DATE}"
TTTECH_BINARIES_PATH = "${UNPACKDIR}/${BP}/tsn_sw_base.sysrepo-plugins"
inherit binaries-unpack

FILES:${PN} = "${libdir}/libsysrepo-plugin-helpers*.so.*"
FILES:${PN}-dev = "${libdir}/libsysrepo-plugin-helpers.so"

do_install() {
    install -d ${D}/${libdir}
    # library
    install -m 0644 ${S}/${libdir}/libsysrepo-plugin-helpers.so.1.0.0 ${D}/${libdir}
    ln -s libsysrepo-plugin-helpers.so.1.0.0 ${D}/${libdir}/libsysrepo-plugin-helpers.so.1
    ln -s libsysrepo-plugin-helpers.so.1.0.0 ${D}/${libdir}/libsysrepo-plugin-helpers.so


    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}
INSANE_SKIP:${PN}-dbg += "buildpaths"
