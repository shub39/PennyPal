package shub39.payu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform