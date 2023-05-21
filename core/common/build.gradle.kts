plugins {
    id("awesome.android.library")
}

android {
    namespace = "com.awesome.manager.core.common"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)

}