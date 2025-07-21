import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // For Ktor use
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0-Beta1"
    // For Firebase use
    alias(libs.plugins.googleServices)
    //For Storytale use
    alias(libs.plugins.storytale)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant{
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies{
                androidTestImplementation(libs.androidx.ui.test.junit4.android)
                debugImplementation(libs.androidx.ui.test.manifest)
            }
        }
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
    jvm("desktop")
    sourceSets {
        val desktopMain by getting
        androidMain.dependencies {
            implementation(libs.androidx.navigation.runtime.android)
            implementation(compose.material)
            implementation(libs.androidx.material3.android)
            implementation(libs.play.services.appsearch)
            implementation(libs.androidx.room.runtime.android)
            implementation(libs.androidx.lifecycle.livedata.core.ktx)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

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

            implementation(compose.material)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Kevinn Zou WebView
            api(libs.compose.webview.multiplatform)

            // Russhwolf Settings
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.test)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.material.icons.core) // Icons.Default.Menu

            // KMPNotifier
            api(libs.kmpnotifier)

        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.play.services.appsearch)
            implementation(libs.ktor.client.darwin)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}
compose.resources{
    publicResClass = false
    packageOfResClass = "multiplatform.composeapp.generated.resources"
    generateResClass = auto
}
android {
    namespace = "com.dian.prueba"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    signingConfigs {
        create("release") {
            storeFile = file("multiplatform_keystore.jks")
            storePassword = System.getenv("ORG_GRADLE_PROJECT_storePassword") ?: ""
            keyAlias = System.getenv("ORG_GRADLE_PROJECT_keyAlias") ?: ""
            keyPassword = System.getenv("ORG_GRADLE_PROJECT_keyPassword")?: ""
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