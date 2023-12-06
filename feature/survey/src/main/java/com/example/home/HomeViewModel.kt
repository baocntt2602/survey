package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.nimble.sample.repository.SurveyRepository
import com.nimble.sample.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  surveyRepository: SurveyRepository,
  private val userRepository: UserRepository
) : ViewModel() {

  val surveyPagingData = surveyRepository.getSurveyPageData()
    .mapLatest {
      it.map { overview ->
        overview.attributes
      }
    }
    .distinctUntilChanged()
    .cachedIn(viewModelScope)

  fun logout() {
    viewModelScope.launch {
      userRepository.logout()
    }
  }
}

sealed interface HomeUiState {
  data object Loading : HomeUiState
  data object SurveyLoaded : HomeUiState
  data class Error(val message: String?) : HomeUiState
}
