
SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

TTTECH_DIR = "tsn_sw_base.sysrepo-plugins/${TTTECH_BINARY_ARCH}"
S = "${UNPACKDIR}/${BP}/${TTTECH_DIR}"

DEPENDS = "sysrepo libyang"
