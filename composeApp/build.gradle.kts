import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.20"
    alias(libs.plugins.googleServices)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
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

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val androidMain by getting
        
        androidMain.dependencies {
            implementation(libs.play.services.appsearch)
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.ktor.client.content.negotiation)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlin.test)
            implementation(libs.ui.test.junit4)
            implementation(libs.core.splashscreen)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.common)
            implementation(libs.firebase.messaging.ktx)
        }
        
        commonMain.dependencies {
            implementation(libs.androidx.navigation.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            api(libs.compose.webview.multiplatform)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.test)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.material.icons.core)
            implementation(compose.materialIconsExtended)
            api(libs.kmpnotifier)
            implementation(libs.haze)
            implementation(libs.haze.materials)
            implementation(libs.compottie)
            implementation(libs.compottie.lite)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)
            implementation(libs.texty)
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            implementation(libs.coil3.coil.network.ktor3)
            implementation("io.lapockett:cmpglass:1.1.0")
            implementation(libs.ui.backhandler)
            implementation(libs.composemediaplayer)
            implementation("com.mikepenz:multiplatform-markdown-renderer:0.39.2")
            implementation("com.mikepenz:multiplatform-markdown-renderer-m3:0.39.2")
            implementation("com.mikepenz:multiplatform-markdown-renderer-coil3:0.39.2")
        }
        
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlin.test)
        }
        
        val skikoMain by creating {
            dependsOn(commonMain)
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

compose.resources {
    publicResClass = false
    packageOfResClass = "multiplatform.composeapp.generated.resources"
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

    signingConfigs {
        create("release") {
            storeFile = file("multiplatform_keystore.jks")
            storePassword = storePass
            keyAlias = keyAliasValue
            keyPassword = keyPass
        }
    }

    defaultConfig {
        applicationId = "com.dian.prueba"
        minSdk = libs.versions.android.minSdk.get().toInt()
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
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED")

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
    }
}
