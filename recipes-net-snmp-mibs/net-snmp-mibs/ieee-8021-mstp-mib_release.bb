SUMMARY = "ieee-8021-bridge-mib"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

DEPENDS = "libbase net-snmp libpcre libnl"

SRC_URI = "\
    ${TSN_SRC_URI} \
    "
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.net-snmp-mibs/ieee8021MstpMib-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

PV = "st-1.6.8"

SRC_URI += "file://Makefile_MSTP.patch"

PARALLEL_MAKE = ""
BBCLASSEXTEND = "native"

EXTRA_OEMAKE += " CC='${CC}' CFLAGS='${CFLAGS} -Ulinux -Dlinux=linux -Wall -Wno-error=format-truncation -Wstrict-prototypes -Wwrite-strings -Wcast-qual -I/home/devel/smb_share/poky-krogoth-15.0.2/build-dir/tmp/sysroots/cyclone5/usr/include/libnl3 -I. -I${STAGING_INCDIR} -I${STAGING_INCDIR}/net-snmp/agent/ -DNETSNMP_NO_INLINE -I${STAGING_DIR_HOST}/usr/include/libbase -lbase'"
EXTRA_OEMAKE += " DLFLAGS='${LDFLAGS} -fPIC -shared -nostartfiles -g -Wl,-O1 -Wl,--hash-style=gnu -Wl,--as-needed -L${STAGING_LIBDIR} -lnetsnmpmibs -ldl -lpcre -lnetsnmpagent -lpcre -lnetsnmp -lcrypto -lnl-3 -lm'"

FILES:${PN} += "${libdir}/ieee8021MstpMib.so ${datadir}/snmp/mibs/*MIB*"

do_configure () {
    echo "Nothing to configure"
}

do_compile() {
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021MstpCistPortTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021MstpCistTable/*
    oe_runmake clean
    oe_runmake ieee8021MstpMib.so
}

do_install() {
    install -d ${D}/${libdir}
    rm -f ${D}/${libdir}/*
    install -d ${D}/${datadir}/snmp/mibs
    install -m 744 ${S}/ieee8021MstpMib.so ${D}/${libdir}
    install -m 666 ${S}/*MIB*  ${D}/${datadir}/snmp/mibs/
}
INSANE_SKIP:${PN} = "file-rdeps"
