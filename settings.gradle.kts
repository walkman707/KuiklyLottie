pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "KuiklyLottie"
include(":androidApp")
include(":shared")
include(":h5App")
include(":miniApp")
include(":kLottieViewAndroid")
include(":kLottieView")
