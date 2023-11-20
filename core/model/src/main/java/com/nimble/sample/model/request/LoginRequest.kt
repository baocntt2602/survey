package com.nimble.sample.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.nimble.sample.model.GrantTypes

data class LoginRequest(
  @JsonProperty("grant_type")
  val grantType: String = GrantTypes.PASSWORD.name.lowercase(),
  val email: String,
  val password: String,
  @JsonProperty("client_id")
  val clientId: String = "6GbE8dhoz519l2N_F99StqoOs6Tcmm1rXgda4q__rIw",
  @JsonProperty("client_secret")
  val clientSecret: String = "_ayfIm7BeUAhx2W1OUqi20fwO3uNxfo1QstyKlFCgHw"
)
