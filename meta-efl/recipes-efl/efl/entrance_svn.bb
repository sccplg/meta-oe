DESCRIPTION = "Login manager for Enlightenment"
DEPENDS = "efreet eina eet ecore elementary"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
SECTION = "e/apps"

inherit e gettext systemd
SRC_URI = "${E_SVN}/trunk/PROTO;module=${SRCNAME};protocol=http;scmdata=keep \
    file://0001-pam-use-common-auth-instead-of-system-auth.patch \
    file://entrance.service \
"
S = "${WORKDIR}/${SRCNAME}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[consolekit] = "--enable-consolekit,--disable-consolekit,consolekit"

EXTRA_OECONF = "--with-edje-cc=${STAGING_BINDIR_NATIVE}/edje_cc ${@base_contains('DISTRO_FEATURES', 'pam', '', '--disable-pam', d)}"

PR = "r4"
PV = "0.0.4+svnr${SRCPV}"
SRCREV = "${EFL_SRCREV}"

RDEPENDS_${PN} += "${PN}-themes sessreg xauth"
CONFFILES_${PN} += "${sysconfdir}/entrance.conf"

RCONFLICTS_${PN} += "xserver-nodm-init"
RREPLACES_${PN} += "xserver-nodm-init"

RCONFLICTS_${PN} += "xserver-nodm-init-systemd"
RREPLACES_${PN} += "xserver-nodm-init-systemd"

RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"

SYSTEMD_SERVICE_${PN} = "entrance.service"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/entrance.service ${D}${systemd_unitdir}/system
}
