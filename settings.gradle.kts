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
            url = uri("https://maven.pkg.github.com/LaPockett/cmp-library")
            credentials {
                username = "LaPockett"
                password = providers.gradleProperty("TOKEN")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .getOrElse("")
            }
            content {
                includeGroup("com.lapockett")
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
            url = uri("https://maven.pkg.github.com/LaPockett/cmp-library")
            credentials {
                username = "LaPockett"
                password = providers.gradleProperty("TOKEN")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .getOrElse("")
            }
            content {
                includeGroup("com.lapockett")
            }
        }
    }
}

include(":composeApp")