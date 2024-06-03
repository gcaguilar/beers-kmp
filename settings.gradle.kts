import org.gradle.initialization.Environment
import java.io.FileInputStream
import java.util.Properties

rootProject.name = "KmmBeers"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.github.com/AlexanderEggers/ksecurestorage")
    }
}

dependencyResolutionManagement {
    val prop = Properties().apply {
        load(FileInputStream(File(rootProject.projectDir, "project.properties")))
    }
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            url = uri("https://maven.pkg.github.com/AlexanderEggers/ksecurestorage")
            credentials {
                username = prop.getProperty("username")
                password = prop.getProperty("token")
            }
        }
    }
}

include(":composeApp")