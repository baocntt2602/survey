package com.nimble.sample.repository

import com.nimble.sample.network.data.TokenStorage
import javax.inject.Inject

interface PreferenceRepository {
  val accessToken: String?
}

class DefaultPreferenceRepository @Inject constructor(
  private val tokenStorage: TokenStorage
): PreferenceRepository {
  override val accessToken: String?
    get() = tokenStorage.accessToken
}
