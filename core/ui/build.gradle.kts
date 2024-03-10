plugins {
    id("awesomemanager.android.library.compose")
    id("awesomemanager.android.library")
}

android {
    namespace = "com.awesome.manager.core.ui"
    compileSdk = 34
}

dependencies {

    implementation(libs.core.ktx)


    implementation(project(":core:designsystem"))

}