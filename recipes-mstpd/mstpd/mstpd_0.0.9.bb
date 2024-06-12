SUMMARY = "MSTPD"
SECTION = "tsn-base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4325afd396febcb659c36b49533135d4"

SRCREV = "9bebaaf37c4a888b0a0be067f0dde57165c273a5"
PR = "r1"

SRC_URI = "git://github.com/mstpd/mstpd.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

export LDFLAGS=" -Wl,-O1 -Wl,--hash-style=gnu -Wl,--as-needed -ldl"

FILES:${PN} =+ "/"

export exec_prefix=""

inherit autotools-brokensep pkgconfig
