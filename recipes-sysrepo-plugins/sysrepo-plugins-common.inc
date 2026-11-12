
SRC_URI = "${TSN_SRC_URI}"
SRCREV = "${TSN_SRCREV}"

inherit cmake pkgconfig

DEPENDS = "sysrepo libyang"

EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX=/usr -DCMAKE_BUILD_TYPE:String=Release "
