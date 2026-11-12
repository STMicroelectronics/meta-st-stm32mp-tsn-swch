
DEPENDS += "libsysrepo-plugin-helpers libtsn"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/bridge"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/yang/*"

do_install:append() {
    install -d ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-types.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-types.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-stream-filters-gates.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-stream-filters-gates-bridge.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-psfp.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-psfp-bridge.yang ${D}${datadir}/yang/modules
    # ieee802-dot1q-bridge.yang managed by sysrepo-plugin-bridge-port
    # cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-bridge.yang ${D}${datadir}/yang/modules

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
