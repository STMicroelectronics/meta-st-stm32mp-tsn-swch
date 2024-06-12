SUMMARY = "ieee-8021-bridge-mib"
SECTION = "tsn-base"
LICENSE = "TTTECH-license"

DEPENDS = "libbase libtsn net-snmp libpcre libnl"

SRC_URI = "\
    ${TSN_SRC_URI} \
    "
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.net-snmp-mibs/ieee8021BridgeMib-mib-module"
S = "${WORKDIR}/git/${TTTECH_DIR}"

SRC_URI += "file://Makefile_Bridge.patch"

PR = "st-1.6.7"

PARALLEL_MAKE = ""
BBCLASSEXTEND = "native"

EXTRA_OEMAKE += " CC='${CC}' CFLAGS='${CFLAGS} -Ulinux -Dlinux=linux -Wall -Wno-error=format-truncation -Wstrict-prototypes -Wwrite-strings -Wcast-qual -I/home/devel/smb_share/poky-krogoth-15.0.2/build-dir/tmp/sysroots/cyclone5/usr/include/libnl3 -I. -I${STAGING_INCDIR} -I${STAGING_INCDIR}/net-snmp/agent/ -DNETSNMP_NO_INLINE -I${STAGING_DIR_HOST}/usr/include/libbase -lbase -I${STAGING_DIR_HOST}/usr/include/libtsn -ltsn'"
EXTRA_OEMAKE += " DLFLAGS='${LDFLAGS} -fPIC -shared -nostartfiles -g -Wl,-O1 -Wl,--hash-style=gnu -Wl,--as-needed -L${STAGING_LIBDIR} -lnetsnmpmibs -ldl -lpcre -lnetsnmpagent -lpcre -lnetsnmp -lcrypto -lnl-3 -lm'"

FILES:${PN} += "${libdir}/ieee8021BridgeMib.so ${datadir}/snmp/mibs/IEEE8021-TC-MIB ${datadir}/snmp/mibs/IEEE8021-BRIDGE-MIB"

do_configure() {
    echo "Nothing to configure"
}

do_compile() {
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021BridgeBasePortTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021BridgeBaseTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021BridgePhyPortTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021BridgeTrafficClassTable/*
    sed -i 's/U64/uint64_t/g' ${S}/ieee8021BridgePortPriorityTable/*
    oe_runmake clean
    oe_runmake ieee8021BridgeMib.so
}

# Needed to update dynamic library name in elf file
DEPENDS += "patchelf-native"
do_install() {
    install -d ${D}/${libdir}
    rm -f ${D}/${libdir}/*
    install -d ${D}/${datadir}/snmp/mibs
    install -m 0755 ${S}/ieee8021BridgeMib.so ${D}/${libdir}
    install -m 0644 ${S}/IEEE8021*  ${D}/${datadir}/snmp/mibs/

    #patch issue with libbase.so and libtsn
    patchelf --replace-needed libbase.so libbase.so.1 ${D}${libdir}/ieee8021BridgeMib.so
    patchelf --replace-needed libtsn.so libtsn.so.2 ${D}${libdir}/ieee8021BridgeMib.so

}
INSANE_SKIP:${PN} = "file-rdeps"
