plugins {
    id("awesome.android.library")
    id("awesome.android.hilt")
}

android {
    namespace = "com.awesome.manager.core.datastore"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.datastore.preferences.core)

}