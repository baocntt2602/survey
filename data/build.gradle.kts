plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
}

android {
    namespace = "com.nimble.sample.data"

  buildFeatures {
    buildConfig = true
  }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
  api(project(":core:model"))
  api(project(":core:utils"))
  api(project(":core:network"))

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.guava)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.datetime)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.logging)
  implementation(libs.core.ktx)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)
  implementation(libs.datastore.preferences)
  implementation(libs.security.crypto)
  implementation(libs.arrow.core)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
}
