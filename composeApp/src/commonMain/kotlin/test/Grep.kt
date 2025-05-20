package test

fun grep(lines: List<String>, pattern: String, action: (String) -> Unit) {
    val regex = pattern.toRegex()
    lines.filter(regex::containsMatchIn)
        .forEach(action)
}
fun suma (a: Int, b: Int): Int {
    return a + b
}

fun getStringLength(str: String): Int {
    return str.length
}

fun getInitials(fullname:String):String{
    val names = fullname.split("").filter { it.isNotBlank() }
    return when{
        names.size == 1 && names.first().length <= 1 -> {
            names.first().first().toString().uppercase()
        }
        names.size == 1 && names.first().length > 1 -> {
            val name = names.first().uppercase()
            "${name.first()}${name[1].uppercase()}"
        }
        else -> {
            val firstName = names.first().uppercase()
            val lastName = names.last().uppercase()
            "${firstName.first()}${lastName[1].uppercase()}"
        }

    }

}