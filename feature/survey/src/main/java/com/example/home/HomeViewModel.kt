package com.example.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.sample.database.entity.asExternalModel
import com.nimble.sample.model.response.SurveyAttributes
import com.nimble.sample.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val surveyRepository: SurveyRepository
) : ViewModel() {

  private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  val survey: StateFlow<List<SurveyAttributes>> = surveyRepository.getCachedSurveys()
    .map { entities ->
      entities.map { it.asExternalModel() }
    }
    .onEach { cached ->
      _uiState.value = if (cached.isEmpty()) {
        refreshSurveys()
        HomeUiState.Loading
      } else {
        HomeUiState.SurveyLoaded
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = emptyList(),
    )

  @VisibleForTesting
  fun refreshSurveys() {
    _uiState.value = HomeUiState.Loading
    viewModelScope.launch {
      surveyRepository.getRemoteSurveys().fold({
        _uiState.value = HomeUiState.Error(it.errors.firstOrNull()?.detail)
      }, {
        _uiState.value = HomeUiState.SurveyLoaded
      })
    }
  }
}

sealed interface HomeUiState {
  data object Loading : HomeUiState
  data object SurveyLoaded : HomeUiState
  data class Error(val message: String?) : HomeUiState
}
