package com.nimble.sample.repository

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.UserToken
import com.nimble.sample.model.request.LoginRequest
import com.nimble.sample.model.response.LoginResponse
import com.nimble.sample.model.toUserToken
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.data.UserTokenDataStore
import com.nimble.sample.network.either.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {
  suspend fun isAuthenticated(): Boolean
  fun login(email: String, password: String): Flow<Either<ErrorResponse, ResponseWrapper<LoginResponse>>>
  fun userToken(): Flow<UserToken>
  suspend fun logout()
}

class DefaultUserRepository @Inject constructor(
  private val userApi: UserApi,
  private val userTokenDataStore: UserTokenDataStore
) : UserRepository {

  override suspend fun isAuthenticated(): Boolean {
    return userTokenDataStore.userToken.first().accessToken != null
  }

  override fun login(email: String, password: String): Flow<Either<ErrorResponse, ResponseWrapper<LoginResponse>>> {
    return flow {
      val res = userApi.login(LoginRequest(email = email, password = password))
      res.orNull()?.let { loginRes ->
        userTokenDataStore.saveTokens(loginRes.data.attributes.toUserToken)
      }
      emit(res)
    }
  }

  override fun userToken(): Flow<UserToken> {
    return userTokenDataStore.userToken
  }

  override suspend fun logout() {
    userTokenDataStore.saveTokens(UserToken(null, null))
  }
}
