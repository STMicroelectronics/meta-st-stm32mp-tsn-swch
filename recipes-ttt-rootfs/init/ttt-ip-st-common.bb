SUMMARY = "ST Initialisation for TSN purpose with st specific action"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
PR = "r2"

inherit systemd

SRC_URI = " \
	file://st-specific_macaddress.sh \
	"

FILES:${PN} += "${systemd_unitdir} ${sysconfdir}"

S = "${UNPACKDIR}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/st-specific_macaddress.sh ${D}${bindir}/
}
