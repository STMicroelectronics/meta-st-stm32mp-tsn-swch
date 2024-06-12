SUMMARY = "DE-PTP from TTTECH"
LICENSE = "TTTECH-license"

SRC_URI = "\
    ${TSN_SRC_URI} \
    file://deptp.service \
    file://ptp_config.xml \
    "
SRCREV = "${TSN_SRCREV}"

PR = "st-1.6.7-2.5.2-${DE_PTP_DATE}"

TTTECH_DIR = "de-ptp/aarch64"
S = "${WORKDIR}/git/${TTTECH_DIR}"
# ------------------------------------------------------------------
DE_PTP_ARCH = "aarch64"
DE_PTP_DATE = "2024-06-28"
DE_PTP_TARBALL = "TTTECH-de-ptp-${DE_PTP_ARCH}-${DE_PTP_DATE}"
DE_PTP_PATH = "${WORKDIR}/git/de-ptp"

# ------------------------------------------------------------------
# ------------------------------------------------------------------
# Overwrite of do_unpack to untar the tarball
python() {
    # Make sure that we're dealing with recipe that enables externalsrc class
    if bb.data.inherits_class('externalsrc', d):
        bb.build.addtask('do_de_ptp_binaries_unpack', 'do_populate_lic', None, d)
        bb.build.addtask('do_de_ptp_binaries_unpack', 'do_configure', None, d)
}

do_de_ptp_binaries_unpack[depends] += "xz-native:do_populate_sysroot"
do_de_ptp_binaries_unpack() {
    cd ${DE_PTP_PATH}

    if [ -f "${DE_PTP_TARBALL}.bin" ]; then
        sh ${DE_PTP_TARBALL}.bin --auto-accept
    else
        bbfatal "Missing '${DE_PTP_TARBALL}.bin' file in ${S} folder."
    fi
    if [ -d ${DE_PTP_ARCH} ]; then
        #copy the license
        cp ${TSN_BASE_LAYER}/recipes-TTTECH-license/TTTECH-license/files/TTTECH_license.txt ${DE_PTP_ARCH}
    fi
}

do_unpack[depends] += "xz-native:do_populate_sysroot"
python do_unpack() {
    eula = d.getVar('ACCEPT_EULA_'+d.getVar('MACHINE'))
    eula_file = d.getVar('EULA_FILE_ST')
    machine = d.getVar('MACHINE')
    pkg = d.getVar('PN')
    if eula == None:
        bb.fatal("To use '%s' you need to accept the STMicroelectronics EULA at '%s'. "
                 "Please read it and in case you accept it, write: "
                 "ACCEPT_EULA_%s = \"1\" in your local.conf." % (pkg, eula_file, machine))
    elif eula == '0':
        bb.fatal("To use '%s' you need to accept the STMicroelectronics EULA." % pkg)
    else:
        bb.note("STMicroelectronics EULA has been accepted for '%s'" % pkg)

    try:
        externalsrc = d.getVar('EXTERNALSRC')
        if not externalsrc:
            bb.build.exec_func('base_do_unpack', d)
            bb.build.exec_func('do_de_ptp_binaries_unpack', d)
    except:
        raise
}
do_unpack[vardepsexclude] += "EULA_FILE_ST"
# ------------------------------------------------------------------
# ------------------------------------------------------------------

inherit systemd bash-completion update-rc.d bin_package

INITSCRIPT_NAME = "deptp"
# INITSCRIPT_PARAMS = "defaults 90 20"
# disable daemon at startup
INITSCRIPT_PARAMS = "stop 20 0 1 6 ."

SYSTEMD_SERVICE:${PN} = "deptp.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 644 ${TSN_BASE_LAYER}/recipes-de-ptp/de-ptp/files/deptp.service ${D}${systemd_unitdir}/system/
    fi
    install -m 644 ${TSN_BASE_LAYER}/recipes-de-ptp/de-ptp/files/ptp_config.xml ${D}${sysconfdir}/deptp/
}

RDEPENDS:${PN} = "${@bb.utils.contains('DISTRO_FEATURES','sysvinit','initscripts','',d)}"
CONFFILES:${PN} += "${sysconfdir}/init.d/deptp"

RPROVIDES:${PN} = "libclock_if.so libclock_if.so.0 libpacket_if.so libpacket_if.so.0 libhost_clock_adj.so libhost_clock_adj.so.0"

# This gets rid of new qa error
TARGET_CC_ARCH += "${LDFLAGS}"

# Needed for binary package to pass Yocto QA
INSANE_SKIP:${PN} = "already-stripped file-rdeps"
ALL_QA:remove = "libdir"
