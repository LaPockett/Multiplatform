package com.dian.prueba.data.unused

enum class AmazonCategory(val categoryName: String){
    SUPERMERCADO("Supermercado"),
    LISTAS_ALEXA("Listas de Alexa"),
    PRIME("Prime"),
    VIDEO("Video")
}
fun getAllAmazonCategories(): List<AmazonCategory>{
    return listOf(
        AmazonCategory.SUPERMERCADO,
        AmazonCategory.LISTAS_ALEXA,
        AmazonCategory.PRIME,
        AmazonCategory.VIDEO
    )
}

fun getAmazonCategory(value: String): AmazonCategory?{
    val map = AmazonCategory.entries.associateBy(AmazonCategory::categoryName)
    return map[value]
}