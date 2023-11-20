package com.nimble.sample.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.nimble.sample.model.TokenAttributes

data class LoginResponse(
  @JsonProperty("id")
    val id: String,
  @JsonProperty("type")
    val type: String,
  @JsonProperty("attributes")
    val attributes: TokenAttributes
)
