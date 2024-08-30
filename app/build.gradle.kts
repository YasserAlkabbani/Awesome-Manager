plugins {
    id("awesomemanager.android.application")
    id("awesomemanager.android.application.compose")
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


}