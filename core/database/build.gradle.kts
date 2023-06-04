plugins {
    id("awesomemanager.android.library")
    id("awesomemanager.android.hilt")
    id("org.jetbrains.kotlin.android")
    id("awesomemanager.android.room")
}

android {
    namespace = "com.awesome.manager.core.database"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)

}