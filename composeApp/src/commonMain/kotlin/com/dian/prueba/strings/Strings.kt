package com.dian.prueba.strings

import androidx.compose.ui.text.AnnotatedString

data class Strings(
    val parameter: (locale: String) -> String,
    //val simple: String,
    /*val annotated: AnnotatedString,
    val parameter: (locale: String) -> String,
    val plural: (count: Int) -> String,
    val list: List<String>*/
)