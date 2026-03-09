package com.dian.prueba

import android.os.Build
import coil3.ImageLoader
import coil3.imageLoader
import com.dian.prueba.utilities.AppImageLoader
import com.dian.prueba.view.MainActivity
import okhttp3.internal.platform.PlatformRegistry.applicationContext

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}
actual fun getPlatformType(): PlatformType = PlatformType.ANDROID

// Code in case you want to separate it by platform
/*actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}*/

//actual fun getImageLoader(): ImageLoader =
// (applicationContext as MainActivity).imageLoader
actual fun getImageLoader(): ImageLoader =
    MainActivity.imageLoader