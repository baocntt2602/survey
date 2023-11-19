import com.android.build.gradle.LibraryExtension
import com.nimble.android.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("nimble.android.library")
        apply("nimble.android.hilt")
      }
      extensions.configure<LibraryExtension> {
        defaultConfig {
          buildFeatures {
            buildConfig = true
          }
        }
      }

      dependencies {
        add("implementation", project(":core:model"))
        add("implementation", project(":core:ui"))
        add("implementation", project(":core:network"))
        add("implementation", project(":domain"))
        add("implementation", project(":data"))

        add("testImplementation", kotlin("test"))
        add("androidTestImplementation", kotlin("test"))
        add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())
        add("implementation", libs.findLibrary("lifecycle.viewmodel.compose").get())
        add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
        add("implementation", libs.findLibrary("arrow.core").get())
      }
    }
  }
}
