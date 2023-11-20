package com.example.onboard

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.R
import com.nimble.sample.repository.UserRepository
import com.nimble.ui.component.textfield.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}".toRegex()

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val userRepository: UserRepository
) : ViewModel() {

  private val btnLoginTapped = MutableSharedFlow<Long>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
  )

  val uiState: StateFlow<LoginUiState> = btnLoginTapped.distinctUntilChanged()
    .flatMapLatest {
      userRepository.login(emailTextFieldState.text, passwordTextFieldState.text)
        .map {
          it.fold({ err ->
            LoginUiState.LoginFailed(err.errors.firstOrNull()?.detail ?: "Unknown error")
          }, {
            LoginUiState.LoginSuccess
          })
        }
        .onStart {
          emit(LoginUiState.Loading)
        }
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = LoginUiState.Idle,
    )

  val emailTextFieldState = TextFieldState(
    {
      it.matches(emailRegex)
    }, {
      R.string.error_email_invalid
    }
  )

  val passwordTextFieldState = TextFieldState(
    // validate password pattern if necessary
    {
      it.isNotEmpty()
    }, {
      R.string.error_password_empty
    }
  )

  val loginFormValid by derivedStateOf {
    emailTextFieldState.isValid && passwordTextFieldState.isValid
  }

  fun onLoginClicked() {
    btnLoginTapped.tryEmit(System.currentTimeMillis())
  }
}

sealed interface LoginUiState {
  data object Idle : LoginUiState
  data object Loading : LoginUiState
  data object LoginSuccess : LoginUiState
  data class LoginFailed(val errorMessage: String) : LoginUiState
}
