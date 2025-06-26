import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0-Beta1"
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
    
    jvm("desktop")
    /*repositories {
        mavenLocal()
    }*/
    
    sourceSets {
        val desktopMain by getting
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // https://mvnrepository.com/artifact/androidx.preference/preference-ktx
            //implementation("androidx.preference:preference-ktx:1.2.1")
            // Test de android
            implementation("androidx.compose.ui:ui-test-manifest:1.8.2")
            // https://mvnrepository.com/artifact/androidx.compose.ui/ui-test-junit4
            implementation("androidx.compose.ui:ui-test-junit4:1.9.0-alpha03")

        }
        commonTest.dependencies {
            // Test
            implementation(libs.kotlin.test)
            // https://mvnrepository.com/artifact/org.jetbrains.compose.ui/ui-test-junit4
            implementation("org.jetbrains.compose.ui:ui-test-junit4:1.5.11")
            // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-test-junit5
            //implementation("org.jetbrains.kotlin:kotlin-test-junit5:2.2.0-Beta2")
            //implementation("io.mockk:mockk:1.14.2")//El mock es lo que causaba error
            implementation("com.russhwolf:multiplatform-settings-test:1.3.0")
            // https://mvnrepository.com/artifact/androidx.preference/preference-ktx
            //implementation("androidx.preference:preference-ktx:1.2.1")
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            // https://mvnrepository.com/artifact/junit/junit
            //implementation("junit:junit:4.13.2")
        }
        commonMain.dependencies {
            //Junit test
            implementation("com.russhwolf:multiplatform-settings-test:1.3.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
            // https://mvnrepository.com/artifact/androidx.preference/preference-ktx
            //implementation("androidx.preference:preference-ktx:1.2.1")
            // https://mvnrepository.com/artifact/io.mockk/mockk
            //implementation("io.mockk:mockk:1.14.2")
            implementation(libs.kotlin.test)
            implementation(libs.ui.test.junit4)
            //implementation(libs.junit.jupiter)
            //implementation(libs.junit.vintage.engine)
            // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-test-junit5
            //implementation("org.jetbrains.kotlin:kotlin-test-junit5:2.2.0-Beta2")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            // Ya viene por defecto el viewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            // https://mvnrepository.com/artifact/androidx.compose.material3/material3
            implementation("androidx.compose.material3:material3:1.4.0-alpha13")
            implementation ("androidx.compose.material:material:1.8.0")
            api("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")
            implementation(libs.play.services.appsearch)
            //implementation ("androidx.compose.material:material-icons-extended:1.7.8")
            // Ktor
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-core:3.1.2")
            // https://mvnrepository.com/artifact/io.ktor/ktor-serialization-kotlinx-json
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-content-negotiation
            implementation("io.ktor:ktor-client-content-negotiation:3.1.2")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-okhttp
            implementation("io.ktor:ktor-client-okhttp:3.1.2")
            //https://github.com/russhwolf/multiplatform-settings
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
            //implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            //Json Web Token
            api("io.jsonwebtoken:jjwt-api:0.12.6")
            implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
            implementation("io.jsonwebtoken:jjwt-orgjson:0.12.6")
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3") // Icons.Default.Menu

        }
        desktopMain.dependencies {

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
    sourceSets.commonMain{
        kotlin.srcDir("build/generated/ksp/metadata")
    }
}

android {
    namespace = "com.dian.prueba"
    compileSdk = 35
    signingConfigs {
        create("release") {
            storeFile = file("key.jks")
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
        getByName("debug"){
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.android)
    implementation(libs.play.services.appsearch)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    implementation(libs.androidx.junit.ktx)
    //implementation(libs.androidx.material3)
    debugImplementation(compose.uiTooling)

}

compose.desktop {
    application {
        mainClass = "com.dian.prueba.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dian.prueba"
            packageVersion = "1.0.0"
        }
    }
}

