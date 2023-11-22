package com.nimble.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.sample.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  userRepository: UserRepository
) : ViewModel() {

  val isAuthenticated = runBlocking {
    userRepository.isAuthenticated()
  }

  // listen to user token data store to handle token expired or user explicitly logout
  val state: StateFlow<MainUiState> = userRepository.userToken()
    .map {
      if (it.accessToken == null) {
        MainUiState.UserLogout
      } else {
        MainUiState.Default
      }
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = MainUiState.Default
    )
}

sealed interface MainUiState {
  data object Default : MainUiState
  data object UserLogout : MainUiState
}
