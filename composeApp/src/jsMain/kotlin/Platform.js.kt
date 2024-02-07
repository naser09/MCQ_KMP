actual fun getPlatform(): Platform {
    return  object :Platform{
        override val name: String
            get() = "JS Target"
    }
}