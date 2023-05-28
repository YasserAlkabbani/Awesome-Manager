import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    id("awesome.android.library")
    id("awesome.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val networkFile = rootProject.file("local.properties")
val networkProperties = Properties()
networkProperties.load(FileInputStream(networkFile))

android {
    namespace = "com.awesome.manager.core.network"

    gradleLocalProperties(rootDir).getProperty("YOUR_PROP_NAME")


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

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

}