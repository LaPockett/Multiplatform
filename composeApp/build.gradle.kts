import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import java.util.Properties

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
    alias(libs.plugins.ksp)
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
            // ShowKase Airbnb
            implementation("com.airbnb.android:showkase:1.0.4")
            implementation("com.airbnb.android:showkase-annotation:1.0.4")
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

            // KMPNotifier
            api(libs.kmpnotifier)

            // Librería de un componente de Dian publicado en Maven Local
            //implementation("com.lapockett.testlib:testlib:1.0.0")
            //implementation("com.lapockett:lib:1.1.1")
            //implementation("io.lapockett:idamgon-cmp:1.1.0")
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
    add("ksp", "com.airbnb.android:showkase-processor:1.0.4")
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
project.afterEvaluate {
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidMain")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidMain")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateActualResourceCollectorsForAndroidMain")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateComposeResClass")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateResourceAccessorsForCommonMain")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidDebug")
    project.tasks["kspDebugKotlinAndroid"].dependsOn("generateExpectResourceCollectorsForCommonMain")

    project.tasks["kspKotlinDesktop"].dependsOn("generateResourceAccessorsForDesktopMain")
    project.tasks["kspKotlinDesktop"].dependsOn("generateActualResourceCollectorsForDesktopMain")
    project.tasks["kspKotlinDesktop"].dependsOn("generateComposeResClass")
    project.tasks["kspKotlinDesktop"].dependsOn("generateResourceAccessorsForCommonMain")
    project.tasks["kspKotlinDesktop"].dependsOn("generateExpectResourceCollectorsForCommonMain")

    project.tasks["kspReleaseKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidRelease")
    project.tasks["kspReleaseKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidMain")
    project.tasks["kspReleaseKotlinAndroid"].dependsOn("generateActualResourceCollectorsForAndroidMain")
    project.tasks["kspReleaseKotlinAndroid"].dependsOn("generateComposeResClass")
    project.tasks["kspReleaseKotlinAndroid"].dependsOn("generateExpectResourceCollectorsForCommonMain")

    project.tasks["kspDebugUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTestDebug")
    project.tasks["kspDebugUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTest")
    project.tasks["kspDebugUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForCommonTest")
    project.tasks["kspDebugUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTestDebug")

    project.tasks["kspReleaseUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTestRelease")
    project.tasks["kspReleaseUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTest")
    project.tasks["kspReleaseUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForCommonTest")
    project.tasks["kspReleaseUnitTestKotlinAndroid"].dependsOn("generateResourceAccessorsForAndroidUnitTestDebug")

    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateResourceAccessorsForIosSimulatorArm64Main")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateActualResourceCollectorsForIosSimulatorArm64Main")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateResourceAccessorsForIosMain")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateResourceAccessorsForNativeMain")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateExpectResourceCollectorsForCommonMain")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateComposeResClass")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateResourceAccessorsForNativeMain")
    project.tasks["kspKotlinIosSimulatorArm64"].dependsOn("generateResourceAccessorsForNativeMain")
}