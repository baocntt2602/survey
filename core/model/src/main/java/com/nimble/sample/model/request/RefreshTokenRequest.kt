package com.nimble.sample.model.request

data class RefreshTokenRequest(
	val refreshToken: String? = null,
	val grantType: String? = null,
	val clientSecret: String? = null,
	val clientId: String? = null
)
