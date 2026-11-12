
DEPENDS += "libsysrepo-plugin-helpers"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/end-of-init"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/*"

do_install:append() {

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
