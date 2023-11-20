package com.nimble.sample.network.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

interface TokenStorage {
  var accessToken: String?
  var refreshToken: String?

  fun clearToken()

}

internal class DefaultTokenStorage private constructor(
  private val sharedPreferences: SharedPreferences
) : TokenStorage {

  companion object {
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"

    fun create(context: Context): DefaultTokenStorage {
      val masterKeyAlias =
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
      val sharedPreferences = EncryptedSharedPreferences.create(
        "token_cache_shared_pref",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
      )
      return DefaultTokenStorage(sharedPreferences)
    }
  }

  override var accessToken: String?
    get() = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    set(value) {
      sharedPreferences.edit {
        this.putString(ACCESS_TOKEN_KEY, value)
      }
    }

  override var refreshToken: String?
    get() = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    set(value) {
      sharedPreferences.edit {
        this.putString(REFRESH_TOKEN_KEY, value)
      }
    }

  override fun clearToken() {
    accessToken = null
    refreshToken = null
  }
}
