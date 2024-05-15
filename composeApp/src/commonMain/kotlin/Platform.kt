interface Platform {
    val isDesktop : Boolean
}

expect fun getPlatform(): Platform