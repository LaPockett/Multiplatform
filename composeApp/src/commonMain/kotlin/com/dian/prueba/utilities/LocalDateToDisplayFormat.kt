package com.dian.prueba.utilities

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toDisplayFormat(): String {
    val month = this.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    return "$day $month $year, $hour:$minute"
}