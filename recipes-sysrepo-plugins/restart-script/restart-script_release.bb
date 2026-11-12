SUMMARY = "The script for restart sysrepo daemons and netopeer2-server."
SECTION = "tsn-base"
INHIBIT_PACKAGE_STRIP = "1"
LICENSE = "TTTECH-license"

SRC_URI = "file://restore_datastore"

PV = "st-1.6.10"

S = "${UNPACKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install(){
    install -d ${D}/etc/sysrepo/
    install -Dm 0755 ${S}/restore_datastore ${D}/etc/sysrepo/
}

RDEPENDS:${PN} = "bash"
