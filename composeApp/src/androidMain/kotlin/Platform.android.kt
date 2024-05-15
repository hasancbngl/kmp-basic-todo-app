import android.os.Build

class AndroidPlatform() : Platform {
    override val isDesktop: Boolean = false
}

actual fun getPlatform(): Platform = AndroidPlatform()