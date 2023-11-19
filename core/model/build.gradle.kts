plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
}

android {
  namespace = "com.nimble.sample.model"

  buildFeatures {
    buildConfig = true
  }

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

}

dependencies {
  implementation(libs.core.ktx)
  implementation(libs.appcompat)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.datetime)
}
