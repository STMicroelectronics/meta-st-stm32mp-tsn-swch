FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LICENSE = "TTTECH-license"

SRC_URI:append = " file://TTTECH_license.txt "
do_patch[postfuncs] += "copy_tttech_license_on_source"
copy_tttech_license_on_source() {
    # for create_spdx
    if [ ! "${UNPACKDIR}" = "${S}" ]; then
        cp -f ${UNPACKDIR}/TTTECH_license.txt ${S}
    fi
}

NO_GENERIC_LICENSE[TTTECH-license] = "TTTECH_license.txt"

LICENSE:${PN} = "TTTECH-license"

LIC_FILES_CHKSUM += "file://TTTECH_license.txt;md5=3c3cb74099b58d8872679cc6b03c0391"
