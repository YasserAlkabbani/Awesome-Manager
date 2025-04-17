import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("awesomemanager.android.library")
    id("awesomemanager.android.hilt")
    id("awesomemanager.android.ktor")
}

val propertiesFile = rootProject.file("gradle.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(propertiesFile))
//val localProperties = gradleLocalProperties(rootDir)

android {
    namespace = "com.awesome.manager.core.network"


    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"${localProperties["base_url"]}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties["api_key"]}\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"${localProperties["base_url"]}\"")
            buildConfigField("String", "API_KEY", "\"${localProperties["api_key"]}\"")
        }
    }

}

dependencies {

    implementation(project(":core:datastore"))

}