plugins {
    id("awesomemanager.android.library.compose")
}

android {
    namespace = "com.awesome.manager.core.ui"
}
dependencies {
    api(project(":core:design-system"))
}

