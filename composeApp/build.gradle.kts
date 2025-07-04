import org.jetbrains.compose.ExperimentalComposeLibrary
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    val ktorVersion= "3.0.0"
    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // https://mvnrepository.com/artifact/io.ktor/ktor-serialization-kotlinx-json
            //implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-content-negotiation
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-okhttp
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

            implementation(libs.kotlin.test)
            implementation(libs.ui.test.junit4)
            // Splash Screen
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {
            // Ktor
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            //implementation("org.jetbrains.kotlin:kotlin-serialization:2.2.0")
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm
            //runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.8.1")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            implementation(compose.material)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)
            // More dependencies
            api("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")
            implementation(libs.play.services.appsearch)

            //implementation ("androidx.compose.material:material-icons-extended:1.7.8")

            //https://github.com/russhwolf/multiplatform-settings
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
            //implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            //Json Web Token
            api("io.jsonwebtoken:jjwt-api:0.12.6")
            implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
            implementation("io.jsonwebtoken:jjwt-orgjson:0.12.6")
            //Russhwolf
            implementation("com.russhwolf:multiplatform-settings-test:1.3.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3") // Icons.Default.Menu
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            /*@OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.ui.test.junit4)*/
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
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
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(compose.material)
    implementation(libs.androidx.material3.android)
    implementation(libs.play.services.appsearch)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
}

