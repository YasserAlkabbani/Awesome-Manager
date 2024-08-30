import com.android.build.api.dsl.ApplicationExtension
import com.awesome.manager.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class AndroidApplicationConventionPlugin:Plugin<Project> {
    override fun apply(target: Project) {
        with(target){

            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("androidx.navigation.safeargs.kotlin")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("awesomemanager.android.hilt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies{

                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("lifecycle.runtime.testing").get())

                add("androidTestImplementation", libs.findLibrary("navigation.testing").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("espresso.core").get())

                add("implementation", libs.findLibrary("timber").get())

                add("implementation", libs.findLibrary("core.ktx").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("kotlinx.serialization").get())

                add("implementation", project(":core:data"))
                add("implementation", project(":core:model"))

            }

        }
    }
}