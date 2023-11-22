plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
}

android {
  namespace = "com.example.testing"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {

  implementation(project(":data"))

  api(libs.junit4)
  api(libs.kotlinx.coroutines.test)
  api(libs.androidx.test.rules)
  api(libs.androidx.test.runner)
  api(libs.mockk)

  implementation(libs.arrow.core)

  implementation(libs.core.ktx)
  implementation(libs.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
}
