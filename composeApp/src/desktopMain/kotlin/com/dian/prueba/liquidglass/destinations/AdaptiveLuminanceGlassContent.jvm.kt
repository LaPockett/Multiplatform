package com.dian.prueba.liquidglass.destinations

internal actual fun String.format(vararg args: Any?): String {
    return java.lang.String.format(this, *args)
}
