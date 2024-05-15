import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val isDesktop: Boolean = false
}

actual fun getPlatform(): Platform = IOSPlatform()