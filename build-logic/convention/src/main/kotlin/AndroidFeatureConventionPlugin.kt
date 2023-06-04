import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin:Plugin<Project>{
    override fun apply(target: Project) {
        with(target){


            pluginManager.apply{
                apply("awesomemanager.android.library")
                apply("awesomemanager.android.hilt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies{

                add("implementation", project(":core:ui"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:common"))

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                add("implementation",libs.findLibrary("lifecycle.runtime.compose").get())
                add("implementation",libs.findLibrary("lifecycle.viewmodel.compose").get())
                add("implementation",libs.findLibrary("timber").get())


//                add("testImplementation",libs.lifecycle.runtime.testing)
//                add("androidTestImplementation",(libs.compose.ui.test.junit4)
            }

        }
    }

}