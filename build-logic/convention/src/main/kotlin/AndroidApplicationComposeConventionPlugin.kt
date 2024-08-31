import com.android.build.api.dsl.ApplicationExtension
import com.awesome.manager.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {

                add("implementation", libs.findLibrary("activity.compose").get())

                add("implementation", libs.findLibrary("lifecycle.viewmodel.compose").get())
                add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())

                add("implementation", libs.findLibrary("navigation.compose").get())

                add("implementation", libs.findLibrary("hilt.navigation.compose").get())

                add("implementation", project(":core:ui"))

                add("implementation", project(":feature:intro"))
                add("implementation", project(":feature:auth"))
                add("implementation", project(":feature:menu"))
                add("implementation", project(":feature:home"))
                add("implementation", project(":feature:account:accounts"))
                add("implementation", project(":feature:account:account-editor"))
                add("implementation", project(":feature:account:account-details"))
                add("implementation", project(":feature:transaction:transaction-editor"))
                add("implementation", project(":feature:transaction:transactions"))
                add("implementation", project(":feature:transaction:transaction-details"))

            }

        }
    }
}