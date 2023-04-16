import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin:Plugin<Project>{
    override fun apply(target: Project) {
        with(target){

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply{
                apply("nowinandroid.android.library")
                apply("nowinandroid.android.hilt")
            }


            dependencies{

                add("implementation",libs.findLibrary("lifecycle.runtime.ktx").get())
                add("implementation",libs.findLibrary("lifecycle.common.java8").get())
                add("implementation",libs.findLibrary("lifecycle.viewmodel.ktx").get())
                add("implementation",libs.findLibrary("lifecycle.viewmodel.savedstate").get())
                add("implementation",libs.findLibrary("lifecycle.viewmodel.compose").get())

//                add("testImplementation",libs.lifecycle.runtime.testing)
//                add("androidTestImplementation",(libs.compose.ui.test.junit4)
            }

        }
    }

}