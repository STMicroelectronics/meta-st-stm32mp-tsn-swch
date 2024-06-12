SUMMARY = "ieee8021QBridgeMib"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

DEPENDS = "libbase libtsn net-snmp libpcre libnl"

SRC_URI = "\
    ${TSN_SRC_URI} \
    "
SRCREV = "${TSN_SRCREV}"

PR = "st-1.6.7"

TTTECH_DIR = "tsn_sw_base.net-snmp-mibs/ieee8021QBridgeMib-mib-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

SRC_URI += "file://Makefile_q_bridge.patch"

PARALLEL_MAKE = ""
BBCLASSEXTEND = "native"

EXTRA_OEMAKE += " CC='${CC}' CFLAGS='${CFLAGS} -Ulinux -Dlinux=linux -Wall -Wno-error=format-truncation -Wstrict-prototypes -Wwrite-strings -Wcast-qual -I/home/devel/smb_share/poky-krogoth-15.0.2/build-dir/tmp/sysroots/cyclone5/usr/include/libnl3 -I. -I${STAGING_INCDIR} -I${STAGING_INCDIR}/net-snmp/agent/ -DNETSNMP_NO_INLINE -I${STAGING_DIR_HOST}/usr/include/libbase -lbase -I${STAGING_DIR_HOST}/usr/include/libtsn -ltsn'"
EXTRA_OEMAKE += " DLFLAGS='${LDFLAGS} -fPIC -shared -nostartfiles -g -Wl,-O1 -Wl,--hash-style=gnu -Wl,--as-needed -L${STAGING_LIBDIR} -lnetsnmpmibs -ldl -lpcre -lnetsnmpagent -lpcre -lnetsnmp -lcrypto -lnl-3 -lm'"

FILES:${PN} += "${libdir}/ieee8021QBridgeMib.so ${datadir}/snmp/mibs/*MIB*"

do_configure() {
    echo "Nothing to configure"
}

do_compile() {
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021QBridgeTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021QBridgeVlanStaticTable/*
    oe_runmake clean
    oe_runmake ieee8021QBridgeMib.so
}

do_install() {
    install -d ${D}/${libdir}
    rm -f ${D}/${libdir}/*
    install -d ${D}/${datadir}/snmp/mibs
    install -m 0755 ${S}/ieee8021QBridgeMib.so ${D}/${libdir}
    install -m 0644 ${S}/*MIB* ${D}/${datadir}/snmp/mibs/
}

# It looks like having upper case letters in packages breaks Yocto and it complains about libc rdepends
INSANE_SKIP:${PN} = "dep-cmp build-deps file-rdeps pkgvarcheck"
