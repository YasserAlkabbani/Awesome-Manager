plugins {
    id("awesome.android.library.compose")
    id("awesome.android.library")
}

android {
    namespace = "com.awesome.manager.core.designsystem"
}

dependencies {

    api(libs.compose.material3)
    api(libs.compose.material.icons.extended)
    api(libs.compose.ui)
    api(libs.compose.foundation)
    api(libs.compose.runtime)
    api(libs.compose.ui.tooling.preview)
    debugApi(libs.compose.ui.tooling)

    implementation(libs.core.ktx)

}