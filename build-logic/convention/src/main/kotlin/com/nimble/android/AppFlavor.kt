package com.nimble.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
  contentType
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class AppFlavor(
  val dimension: FlavorDimension,
  val applicationIdSuffix: String? = null,
  val baseUrl: String
) {
  dev(
    FlavorDimension.contentType,
    applicationIdSuffix = ".dev",
    "\"https://survey-api.nimblehq.co/api/v1/\""
  ),
  prod(
    FlavorDimension.contentType,
    null,
    "\"https://survey-api.nimblehq.co/api/v1/\""
  )
}

fun configureFlavors(
  commonExtension: CommonExtension<*, *, *, *, *>,
  flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
  commonExtension.apply {
    flavorDimensions += FlavorDimension.contentType.name
    productFlavors {
      AppFlavor.values().forEach {
        create(it.name) {
          dimension = it.dimension.name
          buildConfigField("String", "BASE_URL", it.baseUrl)
          flavorConfigurationBlock(this, it)
          if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
            if (it.applicationIdSuffix != null) {
              applicationIdSuffix = it.applicationIdSuffix
            }
          }
        }
      }
    }
  }
}
