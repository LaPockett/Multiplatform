import org.gradle.kotlin.dsl.maven

rootProject.name = "Multiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
        gradlePluginPortal()
        mavenLocal()
        // Librería publicada en Github Packages
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/LaPockett/ui-logo")
            credentials {
                username = "LaPockett"
                password = providers.gradleProperty("gpr.key")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .getOrElse("")
            }
            content {
                includeGroup("io.lapockett")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal()
        maven("https://jogamp.org/deployment/maven")
        // Librería publicada en Github Packages
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/LaPockett/ui-logo")
            credentials {
                username = "LaPockett"
                password = providers.gradleProperty("gpr.key")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .getOrElse("")
            }
            content {
                includeGroup("io.lapockett")
            }
        }
    }
}

include(":composeApp")
