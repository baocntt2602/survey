package com.nimble.sample.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.nimble.sample.model.BuildConfig
import com.nimble.sample.model.GrantTypes

data class RefreshTokenRequest(
  @JsonProperty("refresh_token")
	val refreshToken: String,
  @JsonProperty("grant_type")
	val grantType: String = GrantTypes.REFRESH_TOKEN.name.lowercase(),
  @JsonProperty("client_secret")
	val clientSecret: String = BuildConfig.CLIENT_SECRETS,
  @JsonProperty("client_id")
	val clientId: String = BuildConfig.CLIENT_ID
)
