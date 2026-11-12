
DEPENDS += "libsysrepo-plugin-helpers libtsn"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/interfaces"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/yang/*"

do_install:append() {
    install -d ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ietf-interfaces.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ietf-routing.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ietf-yang-types.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/iana-if-type.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-preemption.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-preemption-bridge.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-ethernet-mac-merge.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-sched.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1q-sched-bridge.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-ethernet-interface.yang ${D}${datadir}/yang/modules

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
