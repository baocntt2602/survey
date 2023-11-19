package com.nimble.sample.repository

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.request.LoginRequest
import com.nimble.sample.model.response.LoginResponse
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.either.ErrorResponse
import javax.inject.Inject

interface UserRepository {
  suspend fun login(email: String, password: String): Either<ErrorResponse, ResponseWrapper<LoginResponse>>
}

class DefaultUserRepository @Inject constructor(
  private val userApi: UserApi
) : UserRepository {
  override suspend fun login(email: String, password: String): Either<ErrorResponse, ResponseWrapper<LoginResponse>> {
    return userApi.login(LoginRequest(email = email, password = password))
  }
}
