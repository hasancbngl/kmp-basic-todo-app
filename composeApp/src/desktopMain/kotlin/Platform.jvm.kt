class JVMPlatform: Platform {
    override val isDesktop: Boolean = true
}

actual fun getPlatform(): Platform = JVMPlatform()