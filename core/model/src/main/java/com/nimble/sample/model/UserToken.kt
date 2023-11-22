package com.nimble.sample.model

import kotlinx.serialization.Serializable

@Serializable
data class UserToken(
  val accessToken: String? = null,
  val refreshToken: String? = null
)
