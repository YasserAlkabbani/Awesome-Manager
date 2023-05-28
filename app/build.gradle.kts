plugins {
    id("awesome.android.application.compose")
    id("awesome.android.application")
    id("awesome.android.hilt")
}

@Suppress("UnstableApiUsage")
android {

    namespace = "com.awesome.manager"

    defaultConfig {
        applicationId = "com.awesome.manager"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.activity.compose)
    implementation (libs.timber)
    implementation(libs.core.ktx)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    testImplementation (libs.lifecycle.runtime.testing)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.navigation.testing)

    implementation(project(":feature:intro"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:accounts"))
    implementation(project(":feature:menu"))
    implementation(project(":feature:home"))
    implementation(project(":feature:transactions"))

    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))


}