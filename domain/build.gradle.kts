plugins {
  id("nimble.android.library")
  id("nimble.android.hilt")
}

android {
    namespace = "com.nimble.sample.domain"

  buildFeatures {
    buildConfig = true
  }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {
  api(project(":core:utils"))
  api(project(":data"))

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.guava)
  implementation(libs.kotlin.stdlib)
  implementation(libs.core.ktx)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
}
