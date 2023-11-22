package com.nimble.sample.network.api.middleware

import com.nimble.sample.model.UserToken
import com.nimble.sample.model.request.RefreshTokenRequest
import com.nimble.sample.model.toUserToken
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.data.UserTokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
  private val userTokenDataStore: UserTokenDataStore,
  private val userApi: Provider<UserApi>
) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    return runBlocking {
      val refreshToken = userTokenDataStore.userToken.first().refreshToken
      val result = userApi.get().refreshToken(RefreshTokenRequest(refreshToken = refreshToken.orEmpty()))
      result.fold(
        {
          userTokenDataStore.saveTokens(UserToken())
          null
        }, {
          it.data?.attributes?.let { attr ->
            userTokenDataStore.saveTokens(attr.toUserToken)
          }
          newRequestWithAccessToken(response.request, it.data?.attributes?.accessToken.orEmpty())
        })
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
    return request.newBuilder()
      .header("Authorization", "Bearer $accessToken")
      .build()
  }
}
