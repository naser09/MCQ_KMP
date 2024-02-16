package core


object EmailValidator {
    private val allowedProviders = listOf(
        "gmail.com",
        "yahoo.com",
        "outlook.com",
        "hotmail.com",
        "aol.com",
        "icloud.com",
        "protonmail.com",
        "zoho.com",
        "yandex.com",
        "gmx.com",
        "mail.com",
        "tutanota.com",
        "fastmail.com",
        "hushmail.com",
        "mail.ru",
        "inbox.com",
        "runbox.com",
        "lycos.com",
        "earthlink.net",
        "disroot.org"
    )
    fun getAllowedProvider() = allowedProviders
    fun isValidEmail(email: String, allowedProvider: List<String> = allowedProviders): Boolean {
        val t = get(allowedProvider)
        val emailRegex = "^[A-Za-z0-9._%+-]+@($t)$"
        val pattern = Regex(pattern = emailRegex)
        //val pattern = Pattern.compile(emailRegex)
//        val matcher = pattern.matcher(email)
//        return matcher.matches()
        return pattern.matches(email)
    }
    private fun get(list: List<String>):String{
        return list.map { it.replace(".","\\.") }.map { "$it|" }.joinToString("").removePrefix("|")
    }
}