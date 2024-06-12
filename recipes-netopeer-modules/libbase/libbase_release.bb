SUMMARY = "Library for accessing system data for YANG modules"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

PR = "st-1.6.7"

TTTECH_DIR = "tsn_sw_base.netopeer-modules/libbase"
S = "${WORKDIR}/git/${TTTECH_DIR}"

DEPENDS = "coreutils-native"

EXTRA_OEMAKE = "-e -j 1 CCFLAGS='${CFLAGS} -fPIC -D_GNU_SOURCE -I${S}/include -DVER_FULL=\"1.0\"'"

do_compile(){
  oe_runmake clean
  ${STAGING_BINDIR_NATIVE}/sync
  oe_runmake all
}

do_install(){
  rm -rf ${D}
  ${STAGING_BINDIR_NATIVE}/sync
  mkdir -p ${D}${includedir}/libbase
  ${STAGING_BINDIR_NATIVE}/sync
  mkdir -p ${D}${libdir}
  ${STAGING_BINDIR_NATIVE}/sync
  install -Dm 0777 ${S}/build/*.so.* ${D}${libdir}/
  install -Dm 0777 ${S}/build/lib*.a ${D}${libdir}/
  ${STAGING_BINDIR_NATIVE}/sync
  install -m 0644 ${S}/include/*.h ${D}/${includedir}/libbase
}

FILES:${PN} = "${libdir}/lib*.so.*"

FILES:${PN}-dev += " ${includedir}/libbase/*"

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
