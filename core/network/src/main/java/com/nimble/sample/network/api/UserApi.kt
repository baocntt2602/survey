package com.nimble.sample.network.api

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.request.LoginRequest
import com.nimble.sample.model.request.RefreshTokenRequest
import com.nimble.sample.model.response.LoginResponse
import com.nimble.sample.model.response.RefreshTokenResponse
import com.nimble.sample.network.either.ErrorResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

  @POST("oauth/token")
  suspend fun refreshToken(@Body body: RefreshTokenRequest): Either<ErrorResponse, RefreshTokenResponse>

  @POST("oauth/token")
  suspend fun login(@Body body: LoginRequest): Either<ErrorResponse, ResponseWrapper<LoginResponse>>
}
