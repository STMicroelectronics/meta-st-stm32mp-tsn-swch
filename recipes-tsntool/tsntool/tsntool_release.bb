SUMMARY = "TSN configuration tool"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.tsntool"
S = "${WORKDIR}/git/${TTTECH_DIR}"

SRC_URI += "file://LICENSE"

PV = "st-1.6.8"

EXTRA_OEMAKE = "-e "

DEPENDS = "coreutils-native libbsd"

do_compile(){
    echo ${STAGING_INCDIR}
    oe_runmake clean
    ${STAGING_BINDIR_NATIVE}/sync
    oe_runmake all
}

do_install(){
    rm -rf ${D}
    oe_runmake install DESTDIR=${D}
}

PACKAGES += "libtsn libtsn-dev"

FILES:${PN} = "${bindir}/tsntool ${mandir}/man8/tsntool.8"

FILES:libtsn = "${libdir}/lib*.so.*"

FILES:libtsn-dev = "${includedir}/libtsn/*.h ${libdir}/lib*.so"

FILES:libtsn-staticdev = "${libdir}/libtsn.a"

FILES:${PN}-dev = ""

RDEPENDS:${PN}-staticdev = ""
RDEPENDS:${PN}-dev = ""
RDEPENDS:${PN}-dbg = ""
RDEPENDS:${PN} = ""
RDEPENDS:${PN}-doc = ""
RDEPENDS:${PN}-locale = ""
RDEPENDS:${PN}-static = ""

PROVIDES = "tsntool libtsn"

SKIP_FILEDEPS:${PN} = "1"
SKIP_FILEDEPS:${PN}-staticdev = "1"
SKIP_FILEDEPS:${PN}-dev = "1"
SKIP_FILEDEPS:${PN}-dbg = "1"
SKIP_FILEDEPS:${PN}-doc = "1"
SKIP_FILEDEPS:${PN}-locale = "1"
SKIP_FILEDEPS:${PN}-static = "1"

RDEPENDS:libtsn-staticdev = ""
RDEPENDS:libtsn-dev = ""
RDEPENDS:libtsn-dbg = ""
RDEPENDS:libtsn = ""
RDEPENDS:libtsn-doc = ""
RDEPENDS:libtsn-locale = ""
RDEPENDS:libtsn-static = ""

RPROVIDES:libtsn = "libtsn.so libtsn.so.2"
RPROVIDES:libtsn-staticdev = "libtsn.a"

SKIP_FILEDEPS:libtsn = "1"
SKIP_FILEDEPS:libtsn-staticdev = "1"
SKIP_FILEDEPS:libtsn-dev = "1"
SKIP_FILEDEPS:libtsn-dbg = "1"
SKIP_FILEDEPS:libtsn-doc = "1"
SKIP_FILEDEPS:libtsn-locale = "1"
SKIP_FILEDEPS:libtsn-static = "1"

LEAD_SONAME_libtsn = "libtsn.so"

# get rids of new yocto qa error
TARGET_CC_ARCH += "${LDFLAGS}"
