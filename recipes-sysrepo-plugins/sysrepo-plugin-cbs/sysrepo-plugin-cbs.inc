
DEPENDS += "libsysrepo-plugin-helpers libtsn libyang"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/${TTTECH_BINARY_ARCH}"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

# ------------------------------------------------------------------
TTTECH_BINARY_ARCH = "aarch64"
TTTECH_BINARY_DATE = "2026-09-29"
TTTECH_BINARY_TARBALL = "TTTECH-sysrepo-plugins-${TTTECH_BINARY_ARCH}-${TTTECH_BINARY_DATE}"
TTTECH_BINARIES_PATH = "${UNPACKDIR}/${BP}/tsn_sw_base.sysrepo-plugins"
inherit binaries-unpack

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/yang/*"

do_install() {
    install -d ${D}${datadir}/yang/modules ${D}/${libdir}/sysrepo/plugins/
    # yang modules
    install -m 0644 ${S}${datadir}/yang/modules/ieee802-dot1q-cbsa-bridge.yang ${D}${datadir}/yang/modules
    install -m 0644 ${S}${datadir}/yang/modules/ieee802-dot1q-cbsa.yang ${D}${datadir}/yang/modules

    # library
    install -m 0644 ${S}/${libdir}/sysrepo/plugins/libcbs.so ${D}/${libdir}/sysrepo/plugins/

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}
INSANE_SKIP:${PN}-dbg += "buildpaths"
