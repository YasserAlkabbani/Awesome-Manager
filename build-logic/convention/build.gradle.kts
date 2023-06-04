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
            id="awesomemanager.android.application.compose"
            implementationClass="AndroidApplicationConventionPlugin"
        }
        register("androidApplication"){
            id="awesomemanager.android.application"
            implementationClass="AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose"){
            id="awesomemanager.android.library.compose"
            implementationClass="AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary"){
            id="awesomemanager.android.library"
            implementationClass="AndroidLibraryConventionPlugin"
        }
        register("androidHilt"){
            id="awesomemanager.android.hilt"
            implementationClass="AndroidHiltConventionPlugin"
        }
        register("androidFeature"){
            id="awesomemanager.android.feature"
            implementationClass="AndroidFeatureConventionPlugin"
        }
        register("androidRoom"){
            id="awesomemanager.android.room"
            implementationClass="AndroidRoomConventionPlugin"
        }
    }
}