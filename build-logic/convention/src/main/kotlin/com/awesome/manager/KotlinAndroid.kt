package com.awesome.manager

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *>) {

    commonExtension.apply {

        compileSdk = 33

        defaultConfig {
            minSdk = 26
        }

        compileOptions {
            sourceCompatibility=JavaVersion.VERSION_17
            targetCompatibility=JavaVersion.VERSION_17
//            isCoreLibraryDesugaringEnabled=true
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {

                jvmTarget = JavaVersion.VERSION_17.toString()

//                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    //            val warningsAsErrors:String? by project
    //            allWarningsAsErrors=warningsAsErrors.toBoolean()
    //
    //            freeCompilerArgs = freeCompilerArgs + listOf(
    //                "-opt-in=kotlin.RequiresOptIn",
    //                // Enable experimental coroutines APIs, including Flow
    //                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    //                "-opt-in=kotlinx.coroutines.FlowPreview",
    //                "-opt-in=kotlin.Experimental",
    //            )


            }
        }


    }

}