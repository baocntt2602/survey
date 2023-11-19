plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
  id("kotlinx-serialization")
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
  namespace = "com.nimble.sample.core.network"
  buildFeatures {
    buildConfig = true
  }

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

}

secrets {
  defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
  api(project(":core:model"))
  implementation(libs.core.ktx)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.guava)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp.logging)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)
  implementation(libs.datastore.preferences)
  implementation(libs.security.crypto)

  implementation(libs.jackson.core)
  implementation(libs.jackson.kotlin)
  implementation(libs.retrofit.converter.jackson)
  implementation(libs.arrow.core)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
}
