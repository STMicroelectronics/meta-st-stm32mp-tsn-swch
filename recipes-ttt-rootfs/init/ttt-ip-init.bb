SUMMARY = "ST Initialisation for TSN purpose"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
PR = "r1"

inherit update-rc.d systemd

SRC_URI = " \
	file://ttt-ip-init-sysvinit.sh \
	file://ttt-ip-init-systemd.sh \
	file://st-tsn.service \
	"

FILES:${PN} += "${systemd_unitdir} ${sysconfdir}"
CONFFILES:${PN} = "${sysconfdir}/ttt-ip-init.sh"
INITSCRIPT_NAME = "ttt-ip-init.sh"
INITSCRIPT_PARAMS = "defaults 99 99"
SYSTEMD_SERVICE:${PN} = "st-tsn.service"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/ttt-ip-init-sysvinit.sh ${D}${sysconfdir}/init.d/ttt-ip-init.sh
	sed -i -e "s/^REF_ETH_INTERFACE=.*$/REF_ETH_INTERFACE=${DEFAULT_ETHERNET_MAIN_TSN_BRIDGE_INTERFACE}/" ${D}${sysconfdir}/init.d/ttt-ip-init.sh

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -d ${D}${sbindir}
		install -m 0755 ${WORKDIR}/ttt-ip-init-systemd.sh ${D}${sbindir}
		sed -i -e "s/^REF_ETH_INTERFACE=.*$/REF_ETH_INTERFACE=${DEFAULT_ETHERNET_MAIN_TSN_BRIDGE_INTERFACE}/" ${D}${sbindir}/ttt-ip-init-systemd.sh


		install -d ${D}${systemd_unitdir}/system
		install -m 0755 ${WORKDIR}/st-tsn.service ${D}${systemd_unitdir}/system/
	fi
}
