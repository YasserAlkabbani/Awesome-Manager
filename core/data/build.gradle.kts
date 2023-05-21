plugins {
    id("awesome.android.library")
    id("awesome.android.hilt")
}

android {
    namespace = "com.awesome.manager.core.data"
}

dependencies {


    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)

}