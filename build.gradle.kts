plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    // For Firebase use
    id("com.google.gms.google-services") version "4.4.4" apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
}