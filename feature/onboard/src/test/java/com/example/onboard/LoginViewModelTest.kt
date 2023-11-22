package com.example.onboard

import arrow.core.Either
import com.example.testing.MainDispatcherRule
import com.nimble.sample.network.either.ErrorResponse
import com.nimble.sample.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
  @get:Rule
  val dispatcherRule = MainDispatcherRule()

  private lateinit var loginViewModel: LoginViewModel
  private val userRepository = mockk<UserRepository>()

  @Before
  fun setUp() {
    loginViewModel = LoginViewModel(userRepository)
  }

  @Test
  fun `test init state`() = runTest {
    assertEquals(LoginUiState.Idle, loginViewModel.uiState.value)
  }

  @Test
  fun `test login success`() = runTest {
    val collectJob = launch(UnconfinedTestDispatcher()) {
      loginViewModel.uiState.collect()
    }

    every { userRepository.login(any(), any()) } returns flowOf(
      Either.Right(mockk())
    )

    loginViewModel.onLoginClicked()

    assertEquals(true, loginViewModel.uiState.value is LoginUiState.LoginSuccess)
    collectJob.cancel()
  }

  @Test
  fun `test login failed`() = runTest {
    val collectJob = launch(UnconfinedTestDispatcher()) { loginViewModel.uiState.collect() }
    every { userRepository.login(any(), any()) } returns flowOf(
      Either.Left(ErrorResponse(emptyList()))
    )

    loginViewModel.onLoginClicked()

    assertEquals(true, loginViewModel.uiState.value is LoginUiState.LoginFailed)
    collectJob.cancel()
  }
}
