package com.nimble.sample.network.api.middleware


import com.nimble.sample.network.data.TokenStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class DefaultInterceptor(private val tokenStorage: TokenStorage) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = initializeHeader(chain)
    val request = builder.build()
    return chain.proceed(request)
  }

  private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
    val accessToken = tokenStorage.accessToken
    return if (!accessToken.isNullOrEmpty()) {
      chain.request().newBuilder().header("Accept", "application/json").addHeader("Authorization", "Bearer ".plus(accessToken))
    } else {
      chain.request().newBuilder()
    }
  }
}
