
DEPENDS += "libsysrepo-plugin-helpers"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/module-versions"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/${BPN}/* ${datadir}/yang/*"

do_install:append() {
    install -d ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/sysrepo-module-versions.yang ${D}${datadir}/yang/modules
    install -d ${D}${datadir}/${BPN}
    cp -r ${S}/module-versions.data.xml ${D}${datadir}/${BPN}

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
