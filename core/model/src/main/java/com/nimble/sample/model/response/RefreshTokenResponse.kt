package com.nimble.sample.model.response

import com.nimble.sample.model.TokenAttributes

data class RefreshTokenResponse(
  val data: RefreshTokenData? = null
)

data class RefreshTokenData(
  val attributes: TokenAttributes,
  val id: Int,
  val type: String
)
