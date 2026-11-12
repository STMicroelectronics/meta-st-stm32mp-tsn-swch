
DEPENDS += "libsysrepo-plugin-helpers libtsn"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/frer"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/yang/*"

do_install:append() {
    install -d ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1cb-frer.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1cb-frer-types.yang ${D}${datadir}/yang/modules
    # ieee802-dot1cb-stream-identification managed by sysrepo-plugin-sid
    #cp -r ${S}/../YANG_modules_repository/ieee802-dot1cb-stream-identification.yang ${D}${datadir}/yang/modules
    #cp -r ${S}/../YANG_modules_repository/ieee802-dot1cb-stream-identification-types.yang ${D}${datadir}/yang/modules

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
