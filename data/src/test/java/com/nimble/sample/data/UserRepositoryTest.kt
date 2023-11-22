package com.nimble.sample.data

import arrow.core.Either
import com.example.testing.MainDispatcherRule
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.TokenAttributes
import com.nimble.sample.model.UserToken
import com.nimble.sample.model.response.LoginResponse
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.data.UserTokenDataStore
import com.nimble.sample.repository.DefaultUserRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var userRepository: DefaultUserRepository
  private val userApi = mockk<UserApi>()
  private val userTokenDataStore = mockk<UserTokenDataStore>()

  private val dummyResponse = LoginResponse(
    id = "ID",
    type = "type",
    TokenAttributes(
      accessToken = "123",
      tokenType = "type",
      expiresIn = 1234567,
      refreshToken = "456",
      createdAt = 1234567
    )
  )

  @Before
  fun setUp() {
    coEvery { userTokenDataStore.saveTokens(any()) } just Runs
    userRepository = DefaultUserRepository(userApi, userTokenDataStore)
  }

  @Test
  fun `test save user token`() = runTest {
    coEvery { userApi.login(any()) } returns Either.Right(ResponseWrapper(dummyResponse))
    val userTokenSlot = slot<UserToken>()
    val collectJob = launch(UnconfinedTestDispatcher()) {
      userRepository.login("username", "password").collect()
    }

    coVerify {
      userTokenDataStore.saveTokens(capture(userTokenSlot))
    }

    userTokenSlot.captured.let {
      Assert.assertEquals(it.accessToken, dummyResponse.attributes.accessToken)
      Assert.assertEquals(it.refreshToken, dummyResponse.attributes.refreshToken)
    }
    collectJob.cancel()
  }
}
