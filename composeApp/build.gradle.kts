import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
//import org.jetbrains.compose.resources.ExperimentalResourceApi

/*@OptIn(ExperimentalResourceApi::class)
suspend fun loadData(): List<WebContent.Data> {
    val readBytes = Res.readBytes("files/data.json")
    val jsonString = String(readBytes)
    return Json.decodeFromString(jsonString)
}*/
plugins {
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // For Ktor use
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
    // For Firebase use
    alias(libs.plugins.googleServices)
}

kotlin {
    androidTarget {
        /*@OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant{
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies{
                androidTestImplementation(libs.androidx.ui.test.junit4.android)
                debugImplementation(libs.androidx.ui.test.manifest)
            }
        }*/
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export(libs.kmpnotifier)
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    applyDefaultHierarchyTemplate()

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    /*wasmJs {
        browser()
        binaries.executable()
    }*/
    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.services.appsearch)

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Ktor
            implementation(libs.ktor.ktor.client.content.negotiation)
            implementation(libs.ktor.client.okhttp)

            implementation(libs.kotlin.test)
            implementation(libs.ui.test.junit4)
            // Splash Screen
            implementation(libs.core.splashscreen)
            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.common)
            implementation(libs.firebase.messaging.ktx)
        }
        commonMain.dependencies {
            implementation(libs.androidx.navigation.compose)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Kevinn Zou WebView
            api(libs.compose.webview.multiplatform)

            // Russhwolf Settings
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.test)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.material.icons.core) // Icons.Default.Menu
            implementation(compose.materialIconsExtended)
            // KMPNotifier
            api(libs.kmpnotifier)

            // Haze - Glassmorphism
            implementation("dev.chrisbanes.haze:haze:1.6.10")
            implementation("dev.chrisbanes.haze:haze-materials:1.6.10")
            // Compotti
            implementation(libs.compottie)
            implementation(libs.compottie.lite)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)
            // Texty to display text with various styles, effects and animations
            implementation("com.arjunjadeja:texty:1.0.0-alpha")
            // Coil SVG
            implementation("io.coil-kt.coil3:coil-compose:3.0.4")
            implementation("io.coil-kt.coil3:coil-svg:3.0.4")
            // To load remote images from the network
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.4")
            // Own library
            implementation("io.lapockett:logoui:1.2.0")
            // Liquid glasss library
            implementation("io.lapockett:cmpglass:1.1.0")
            // Ref: https://stackoverflow.com/questions/79340066/backhandler-on-compose-multiplatform-android-and-ios
            implementation("org.jetbrains.compose.ui:ui-backhandler:1.9.2")
            // CMP - Media Player (probar con móvil físico)
            implementation("network.chaintech:compose-multiplatform-media-player:1.0.52")
            // CMP - Media PLayer de kdroidFilter
            implementation("io.github.kdroidfilter:composemediaplayer:0.8.7")
        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlin.test)
        }
        val skikoMain by creating {
            dependsOn(commonMain.get())
        }
        iosMain {
            dependsOn(skikoMain)
            dependencies {
                implementation(libs.play.services.appsearch)
                implementation(libs.ktor.client.darwin)
            }
        }
        jvmMain {
            dependsOn(skikoMain)
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)
            }
        }
    }
}

compose.resources{
    publicResClass = false
    packageOfResClass = "multiplatform.composeapp.generated.resources"
    generateResClass = auto
}
val secretsFile = rootProject.file("secrets.gradle.properties")
if (secretsFile.exists()) {
    val secretsProps = Properties().apply {
        load(secretsFile.inputStream())
    }
    secretsProps.forEach { key, value ->
        project.extensions.extraProperties[key.toString()] = value
    }
}

val storePass = project.findProperty("storePassword") as? String ?: ""
val keyAliasValue = project.findProperty("keyAlias") as? String ?: ""
val keyPass = project.findProperty("keyPassword") as? String ?: ""

android {
    namespace = "com.dian.prueba"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    /*Para debug
    println("DEBUG - Store password: '$storePass'")
    println("DEBUG - Key alias: '$keyAliasValue'")
    println("DEBUG - Key password: '$keyPass'")
    println("DEBUG - Keystore exists: ${file("multiplatform_keystore.jks").exists()}")*/
    signingConfigs {
        create("release") {
            storeFile = file("multiplatform_keystore.jks")
            storePassword = storePass
            keyAlias = keyAliasValue
            keyPassword =keyPass
        }
    }

    defaultConfig {
        applicationId = "com.dian.prueba"
        minSdk = libs.versions.android.minSdk.get().toInt()
        //noinspection OldTargetApi
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    implementation(libs.kcef)
}

compose.desktop {
    application {
        mainClass = "com.dian.prueba.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dian.prueba"
            packageVersion = "1.0.0"
            jvmArgs("-Djcef.helper.path=./jcef_helper")
            jvmArgs("-Djcef.gpu.disable=true")
        }
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED") // recommended but not necessary

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
    }
}