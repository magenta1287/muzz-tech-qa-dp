import java.util.Properties
import java.io.FileInputStream

plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("app-plugin")
}

// Load properties from local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.test.muzz"

    defaultConfig {
        // Make the credentials available as build config fields
        buildConfigField("String", "TEST_USER", localProperties.getProperty("test.user", ""))
        buildConfigField("String", "TEST_PASS", localProperties.getProperty("test.pass", ""))
    }
}

dependencies {
    api(libs.bundles.core)
    api(libs.timber)
    api(libs.kotlin.collections.immutable)
    api(libs.bundles.ui)
    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.ui.util)
    api(libs.compose.runtime)
    api(libs.compose.runtime.lifecycle)

    implementation(libs.compose.paging)
    implementation(libs.bundles.ui)
    implementation(libs.androidx.core)
    implementation(libs.accompanist.system.ui.controller)
    implementation(libs.accompanist.webview)
    implementation(libs.compose.paging)
    implementation(libs.compose.constraint.layout)
    implementation(libs.compose.hilt.navigation)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.javax.annotation)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    kapt(libs.room.compiler)
    implementation(libs.javajwt)
    implementation(libs.javax.annotation)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.android.compiler)

    implementation(libs.glide.glide)
    kapt(libs.glide.compiler)

    testImplementation(libs.bundles.testUnit)

    androidTestImplementation(libs.bundles.testUi)
    kaptAndroidTest(libs.hilt.android.compiler)
}
