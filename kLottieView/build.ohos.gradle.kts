import com.tencent.kuikly.gradle.config.KuiklyConfig
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
    signing
}

version = MavenConfig.VERSION_OHOS
group = MavenConfig.GROUP

publishing {
    repositories {
        val username = MavenConfig.getUsername(project)
        val password = MavenConfig.getPassword(project)
        if (username.isNotEmpty() && password.isNotEmpty()) {
            maven {
                credentials {
                    setUsername(username)
                    setPassword(password)
                }
                url = uri(MavenConfig.getRepoUrl(version as String))
            }
        } else {
            mavenLocal()
            // 发布到build/repo目录
//            maven {
//                url = uri(layout.buildDirectory.dir("repo"))
//            }
        }

        publications.withType<MavenPublication>().configureEach {
            pom.configureMavenCentralMetadata()
            signPublicationIfKeyPresent(project)
        }
    }
}

kotlin {
    ohosArm64 {
        binaries.sharedLib {
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                compileOnly("com.tencent.kuikly-open:core:${Version.getKuiklyOhosVersion()}")
                compileOnly("com.tencent.kuikly-open:core-annotations:${Version.getKuiklyOhosVersion()}")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.kuikly.kuiklylottie.lottieview"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }
    sourceSets {
        named("main") {
            assets.srcDirs("src/commonMain/assets")
        }
    }
}

fun getCommonCompilerArgs(): List<String> {
    return listOf(
        "-Xallocator=std"
    )
}

fun getLinkerArgs(): List<String> {
    return listOf()
}