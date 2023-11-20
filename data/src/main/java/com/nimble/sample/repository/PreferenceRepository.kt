package com.nimble.sample.repository

import com.nimble.sample.model.TokenAttributes
import com.nimble.sample.network.data.TokenStorage
import javax.inject.Inject

interface PreferenceRepository {
  val accessToken: String?
  fun saveTokens(tokenAttributes: TokenAttributes)
}

class DefaultPreferenceRepository @Inject constructor(
  private val tokenStorage: TokenStorage
) : PreferenceRepository {
  override val accessToken: String?
    get() = tokenStorage.accessToken

  override fun saveTokens(tokenAttributes: TokenAttributes) {
    tokenStorage.apply {
      accessToken = tokenAttributes.accessToken
      refreshToken = tokenAttributes.refreshToken
    }
  }
}
