plugins {
    id("awesomemanager.android.library")
    id("awesomemanager.android.hilt")
}

android {
    namespace = "com.awesome.manager.core.data"
}

dependencies {

    implementation(libs.paging.runtime)

    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}