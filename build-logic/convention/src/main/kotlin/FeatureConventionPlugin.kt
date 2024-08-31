import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply("awesomemanager.android.library.compose")
                apply("androidx.navigation.safeargs.kotlin")
                apply("awesomemanager.android.hilt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {

                add("implementation", project(":core:ui"))

                add("implementation", project(":core:data"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:domain"))

                add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel.compose").get())

                add("implementation", libs.findLibrary("navigation.compose").get())

                add("implementation", libs.findLibrary("paging.compose").get())

                add("implementation", libs.findLibrary("hilt.navigation.compose").get())

            }

        }
    }

}