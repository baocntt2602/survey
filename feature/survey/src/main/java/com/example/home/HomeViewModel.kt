package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  surveyRepository: SurveyRepository
) : ViewModel() {

  val uiState: StateFlow<HomeUiState> = surveyRepository.surveys()
    .map {
      it.fold({ err ->
        HomeUiState.Error(err.errors.firstOrNull()?.detail)
      }, { res ->
        HomeUiState.SurveyLoaded(res.data)
      })
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = HomeUiState.Loading,
    )
}

sealed interface HomeUiState {
  data object Loading : HomeUiState
  data class SurveyLoaded(val surveys: List<SurveyOverview>) : HomeUiState
  data class Error(val message: String?) : HomeUiState
}
