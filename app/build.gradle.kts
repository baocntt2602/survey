plugins {
    id("nimble.android.application")
    id("nimble.android.application.compose")
    id("nimble.android.application.flavors")
    id("nimble.android.hilt")
}

android {
    namespace = "com.nimble.survey.compose"

  buildFeatures {
    buildConfig = true
  }

    defaultConfig {
        applicationId = "com.nimble.survey.compose"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

  implementation(project(":feature:onboard"))
  implementation(project(":feature:survey"))

  api(project(":core:model"))
  api(project(":core:network"))
  api(project(":core:ui"))
  api(project(":core:utils"))
  api(project(":data"))
  api(project(":domain"))

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.guava)
  implementation(libs.kotlin.stdlib)
  implementation(libs.navigation)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.core.ktx)
  implementation(libs.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)
  implementation(libs.material)

  implementation(libs.androidx.acitivty.ktx)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.lifecycle.runtime.compose)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.navigation)
  implementation(libs.androidx.core.splashscreen)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.ui.test.junit4)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)
}
