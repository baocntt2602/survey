plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
  id("nimble.android.library.compose")
}

android {
  namespace = "com.nimble.sample.ui"

  buildFeatures {
    buildConfig = true
  }

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }
}

dependencies {
  implementation(libs.compose.foundation)
  implementation(platform(libs.compose.bom))
  implementation(libs.core.ktx)
  implementation(libs.appcompat)
  implementation(libs.navigation)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)

  api(libs.compose.foundation)
  api(libs.ui)
  api(libs.ui.graphics)
  api(libs.material3)
  api(libs.ui)
  api(libs.material3)
  api(libs.material)
  api(libs.ui.tooling.preview)
  api(libs.ui.tooling)
  api(libs.activity.compose)
}
