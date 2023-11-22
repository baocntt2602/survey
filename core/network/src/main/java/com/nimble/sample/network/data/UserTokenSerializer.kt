package com.nimble.sample.network.data

import androidx.datastore.core.Serializer
import com.nimble.sample.model.UserToken
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserTokenSerializer(
  private val cryptoManager: CryptoManager
) : Serializer<UserToken> {
  override val defaultValue: UserToken
    get() = UserToken()

  override suspend fun readFrom(input: InputStream): UserToken {
    val decryptedBytes = cryptoManager.decrypt(input)
    return try {
      Json.decodeFromString(
        deserializer = UserToken.serializer(),
        string = decryptedBytes.decodeToString()
      )
    } catch (e: SerializationException) {
      e.printStackTrace()
      defaultValue
    }
  }

  override suspend fun writeTo(t: UserToken, output: OutputStream) {
    cryptoManager.encrypt(
      bytes = Json.encodeToString(
        serializer = UserToken.serializer(),
        value = t
      ).encodeToByteArray(),
      outputStream = output
    )
  }
}
