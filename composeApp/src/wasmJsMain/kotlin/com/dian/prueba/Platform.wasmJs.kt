package com.dian.prueba

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatformType(): PlatformType = PlatformType.WEB