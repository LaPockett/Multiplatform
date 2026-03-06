import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.10"
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    android {
        namespace = "com.lapockett.prueba.composeApp"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        //minSdk = libs.versions.android.minSdk.get().toInt()
        
        androidResources {

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
            implementation("dev.chrisbanes.haze:haze:1.6.10")
            implementation("dev.chrisbanes.haze:haze-materials:1.6.10")
            implementation(libs.compottie)
            implementation(libs.compottie.lite)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)
            implementation("com.arjunjadeja:texty:1.0.0-alpha")
            implementation("io.coil-kt.coil3:coil-compose:3.0.4")
            implementation("io.coil-kt.coil3:coil-svg:3.0.4")
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.4")
            implementation("io.lapockett:cmpglass:1.1.0")
            implementation("org.jetbrains.compose.ui:ui-backhandler:1.9.2")
            implementation("io.github.kdroidfilter:composemediaplayer:0.8.7")
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
