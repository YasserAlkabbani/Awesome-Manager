plugins {
    `kotlin-dsl`
}

//group = "com.newera.buildSrc"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
    }
}