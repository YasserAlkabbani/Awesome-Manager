plugins {
    id("awesomemanager.android.library")
}

android {
    namespace = "com.awesome.manager.core.model"
}

dependencies {
    implementation(project(":core:common"))
}