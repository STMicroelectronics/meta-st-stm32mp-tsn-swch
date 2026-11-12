
DEPENDS += "libsysrepo-plugin-helpers de-ptp-bin libtsn"

PR = "r0"
PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/ieee1588-ptp"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

FILES:${PN} += "${libdir}/sysrepo/* ${datadir}/yang/*"

EXTRA_OECMAKE += " -DLIBPTP=${STAGING_LIBDIR}/libptp.so.0 -DLIBPTP_LIBRARIES=${STAGING_LIBDIR}/libptp.so.0 -DLIBPTP_INCLUDE_DIR=${STAGING_INCDIR}"
EXTRA_OECMAKE += " -DLIBPTP_MANAGEMENT=${STAGING_LIBDIR}/libptp_management.so.0 -DLIBPTP_MANAGEMENT_LIBRARIES=${STAGING_LIBDIR}/libptp_management.so.0 -DLIBPTP_MANAGEMENT_INCLUDE_DIR=${STAGING_INCDIR}"


do_install:append() {
    install -d ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee1588-ptp-tt.yang ${D}${datadir}/yang/modules
    cp -r ${S}/../YANG_modules_repository/ieee802-dot1as-gptp.yang ${D}${datadir}/yang/modules

    if [ "${libdir}" != "/usr/lib" ];
    then
        if [ -d ${D}/usr/lib ]; then
            mv ${D}/usr/lib ${D}/usr/lib64
        fi
    fi
}

INSANE_SKIP:${PN} = "file-rdeps"
INSANE_SKIP:${PN}-dbg += "buildpaths"
