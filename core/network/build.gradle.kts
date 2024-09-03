import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("awesomemanager.android.library")
    id("awesomemanager.android.hilt")
    id("awesomemanager.android.ktor")
}

val networkFile = rootProject.file("local.properties")
val networkProperties = Properties()
networkProperties.load(FileInputStream(networkFile))
//val localProperties = gradleLocalProperties(rootDir)

android {
    namespace = "com.awesome.manager.core.network"


    buildTypes {
        release{
            buildConfigField("String", "BASE_URL", "\"${networkProperties["base_url"]}\"")
            buildConfigField("String", "API_KEY", "\"${networkProperties["api_key"]}\"")
        }
        debug{
            buildConfigField("String", "BASE_URL", "\"${networkProperties["base_url"]}\"")
            buildConfigField("String", "API_KEY", "\"${networkProperties["api_key"]}\"")
        }
    }

}

dependencies {

    implementation(project(":core:datastore"))

}