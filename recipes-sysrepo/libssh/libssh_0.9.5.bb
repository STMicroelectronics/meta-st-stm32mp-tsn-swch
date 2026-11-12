SUMMARY = "Multiplatform C library implementing the SSHv2 and SSHv1 protocol"
HOMEPAGE = "http://www.libssh.org"
SECTION = "libs"

DEPENDS = "zlib openssl libgcrypt"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=dabb4958b830e5df11d2b0ed8ea255a0"

SRCREV = "9c4af47965d284b2de26407bcd80473aba4ee4c9"
SRC_URI = "git://git.libssh.org/projects/libssh.git;protocol=https;branch=stable-0.9 \
           file://0001-Update-cmake_minimum_required-to-3.5-for-CMake-4.x.patch \
           "

EXTRA_OECMAKE = " \
    -DWITH_GCRYPT=0 \
    -DWITH_PCAP=1 \
    -DWITH_SFTP=1 \
    -DWITH_ZLIB=1 \
    -DLIB_SUFFIX=${@d.getVar('baselib').replace('lib', '')} \
    "

PACKAGECONFIG ??= ""
PACKAGECONFIG[gssapi] = "-DWITH_GSSAPI=1, -DWITH_GSSAPI=0, krb5, "

inherit cmake

do_configure:prepend () {
    # Disable building of examples
    sed -i -e '/add_subdirectory(examples)/s/^/#DONOTWANT/' ${S}/CMakeLists.txt \
        || bbfatal "Failed to disable examples"
}

FILES:${PN}-dev += "${libdir}/cmake"
TOOLCHAIN = "gcc"
PKGR = "${PR}~tttech1"
