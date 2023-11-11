import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin():Plugin<Project>{

    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<KspExtension>{

            }

            val libs=extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies{
                add("implementation",libs.findLibrary("hilt.android").get())
                add("ksp",libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }

}
