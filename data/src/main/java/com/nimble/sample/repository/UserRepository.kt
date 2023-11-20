package com.nimble.sample.repository

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.request.LoginRequest
import com.nimble.sample.model.response.LoginResponse
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.either.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {
  suspend fun login(email: String, password: String): Flow<Either<ErrorResponse, ResponseWrapper<LoginResponse>>>
}

class DefaultUserRepository @Inject constructor(
  private val userApi: UserApi,
  private val preferenceRepository: PreferenceRepository
) : UserRepository {

  override suspend fun login(email: String, password: String): Flow<Either<ErrorResponse, ResponseWrapper<LoginResponse>>> {
    return flow {
      val res = userApi.login(LoginRequest(email = email, password = password))
      res.orNull()?.let { loginRes ->
        preferenceRepository.saveTokens(loginRes.data.attributes)
      }
      emit(res)
    }
  }
}
