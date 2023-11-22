package com.nimble.sample.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.nimble.sample.model.BuildConfig
import com.nimble.sample.model.GrantTypes

data class LoginRequest(
  @JsonProperty("grant_type")
  val grantType: String = GrantTypes.PASSWORD.name.lowercase(),
  val email: String,
  val password: String,
  @JsonProperty("client_id")
  val clientId: String = BuildConfig.CLIENT_ID,
  @JsonProperty("client_secret")
  val clientSecret: String = BuildConfig.CLIENT_SECRETS
)
