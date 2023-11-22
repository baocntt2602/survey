package com.nimble.sample.network.api.middleware

import com.nimble.sample.network.data.UserTokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class DefaultInterceptor(private val userTokenDataStore: UserTokenDataStore) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = initializeHeader(chain)
    val request = builder.build()
    return chain.proceed(request)
  }

  private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
    val accessToken = runBlocking {
      userTokenDataStore.userToken.first().accessToken
    }
    return if (!accessToken.isNullOrEmpty()) {
      chain.request().newBuilder().header("Accept", "application/json").addHeader("Authorization", "Bearer ".plus(accessToken))
    } else {
      chain.request().newBuilder()
    }
  }
}
