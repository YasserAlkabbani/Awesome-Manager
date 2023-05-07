import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.awesome.manager.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin{
    plugins {
        register("androidApplicationCompose"){
            id="awesome.android.application.compose"
            implementationClass="AndroidApplicationConventionPlugin"
        }
        register("androidApplication"){
            id="awesome.android.application"
            implementationClass="AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose"){
            id="awesome.android.library.compose"
            implementationClass="AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary"){
            id="awesome.android.library"
            implementationClass="AndroidLibraryConventionPlugin"
        }
        register("androidHilt"){
            id="awesome.android.hilt"
            implementationClass="AndroidHiltConventionPlugin"
        }
        register("androidFeature"){
            id="awesome.android.feature"
            implementationClass="AndroidFeatureConventionPlugin"
        }
    }
}