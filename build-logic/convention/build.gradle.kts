import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.awesome.manager.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.kotlinx.serialization.gradlePlugin)
}

gradlePlugin{
    plugins {
        register("androidApplication"){
            id="awesomemanager.android.application"
            implementationClass="AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id="awesomemanager.android.application.compose"
            implementationClass="AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary"){
            id="awesomemanager.android.library"
            implementationClass="AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id="awesomemanager.android.library.compose"
            implementationClass="AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt"){
            id="awesomemanager.android.hilt"
            implementationClass="HiltConventionPlugin"
        }
        register("androidFeature"){
            id="awesomemanager.android.feature"
            implementationClass="FeatureConventionPlugin"
        }
        register("androidRoom"){
            id="awesomemanager.android.room"
            implementationClass="RoomConventionPlugin"
        }
        register("androidKtor"){
            id="awesomemanager.android.ktor"
            implementationClass="KtorConventionPlugin"
        }
        register("androidWorkManager"){
            id="awesomemanager.android.workmanager"
            implementationClass="WorkManagerConventionPlugin"
        }
    }
}