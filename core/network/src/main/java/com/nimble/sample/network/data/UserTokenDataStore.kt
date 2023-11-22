package com.nimble.sample.network.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.nimble.sample.model.UserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserTokenDataStore {
  val userToken: Flow<UserToken>
  suspend fun saveTokens(userToken: UserToken)
}

class DefaultUserTokenDataStore @Inject constructor(
  @ApplicationContext private val context: Context,
) : UserTokenDataStore {

  private val Context.dataStore: DataStore<UserToken> by dataStore(
    fileName = "user-token.json",
    serializer = UserTokenSerializer(CryptoManager())
  )

  override val userToken: Flow<UserToken>
    get() = context.dataStore.data

  override suspend fun saveTokens(userToken: UserToken) {
    context.dataStore.updateData {
      userToken
    }
  }
}
