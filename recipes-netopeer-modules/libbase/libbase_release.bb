SUMMARY = "Library for accessing system data for YANG modules"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

PV = "st-1.6.10"

TTTECH_DIR = "tsn_sw_base.netopeer-modules/${TTTECH_BINARY_ARCH}"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

# ------------------------------------------------------------------
TTTECH_BINARY_ARCH = "aarch64"
TTTECH_BINARY_DATE = "2026-09-29"
TTTECH_BINARY_TARBALL = "TTTECH-libbase-${TTTECH_BINARY_ARCH}-${TTTECH_BINARY_DATE}"
TTTECH_BINARIES_PATH = "${UNPACKDIR}/${BP}/tsn_sw_base.netopeer-modules"
inherit binaries-unpack

inherit bin_package

do_install:append() {
    rm ${D}/TTTECH_license.txt
    rm ${D}/libbase
}
FILES:${PN} = "${libdir}/lib*.so.*"

RDEPENDS:${PN}-staticdev = ""
RDEPENDS:${PN}-dev = ""
RDEPENDS:${PN}-dbg = ""
RDEPENDS:${PN} = ""
RDEPENDS:${PN}-doc = ""
RDEPENDS:${PN}-locale = ""
RDEPENDS:${PN}-static = ""

PROVIDES = "libbase"

RPROVIDES:${PN} = "libbase.so libbase.so.1"
RPROVIDES:${PN}-staticdev = "libbase.a"

SKIP_FILEDEPS:${PN} = "1"
SKIP_FILEDEPS:${PN}-staticdev = "1"
SKIP_FILEDEPS:${PN}-dev = "1"
SKIP_FILEDEPS:${PN}-dbg = "1"
SKIP_FILEDEPS:${PN}-doc = "1"
SKIP_FILEDEPS:${PN}-locale = "1"
SKIP_FILEDEPS:${PN}-static = "1"

LEAD_SONAME = "libbase.so"

# This gets rid of new qa error
TARGET_CC_ARCH += "${LDFLAGS}"
