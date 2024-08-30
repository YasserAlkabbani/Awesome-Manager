import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KtorConventionPlugin:Plugin<Project> {
    override fun apply(target: Project) {

        with(target){

            val libs=extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {

                add("implementation",libs.findLibrary("ktor.client.core").get())
                add("implementation",libs.findLibrary("ktor.client.okhttp").get())
                add("implementation",libs.findLibrary("ktor.client.logging").get())
                add("implementation",libs.findLibrary("ktor.client.resources").get())
                add("implementation",libs.findLibrary("ktor.client.auth").get())
                add("implementation",libs.findLibrary("ktor.client.content.negotiation").get())
                add("implementation",libs.findLibrary("ktor.serialization.kotlinx.json").get())

                add("debugImplementation",libs.findLibrary("chucker.debug").get())
                add("releaseImplementation",libs.findLibrary("chucker.release").get())

            }

        }

    }
}