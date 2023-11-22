package com.nimble.sample.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenAttributes(
  @JsonProperty("access_token")
  val accessToken: String,
  @JsonProperty("token_type")
  val tokenType: String,
  @JsonProperty("expires_in")
  val expiresIn: Long,
  @JsonProperty("refresh_token")
  val refreshToken: String,
  @JsonProperty("created_at")
  val createdAt: Long
)

val TokenAttributes.toUserToken: UserToken
  get() = UserToken(accessToken, refreshToken)

