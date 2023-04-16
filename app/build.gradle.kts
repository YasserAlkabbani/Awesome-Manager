plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.google.devtools.ksp)
//    alias(libs.plugins.google.dagger.hilt.android)
    id("awesome.android.application.compose")
    id("awesome.android.application")
}

@Suppress("UnstableApiUsage")
android {

    namespace = "com.awesome.manager"

    defaultConfig {
        applicationId = "com.awesome.manager"
        versionCode = 1
        versionName = "1.0"

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
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.5"
//    }
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

    implementation(libs.core.ktx)

    implementation(libs.activity.compose)

    implementation(libs.kotlinx.coroutines.android)

    implementation (libs.timber)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material3)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.compose.ui.test.junit4)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.lifecycle.viewmodel.compose)
    testImplementation (libs.lifecycle.runtime.testing)

//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    implementation(libs.room.paging)
//    ksp(libs.room.compiler)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    testImplementation(libs.paging.common)

    implementation(libs.navigation.compose)
    androidTestImplementation(libs.navigation.testing)

//    implementation(libs.hilt.android)
//    ksp(libs.hilt.android.compiler)

}