package com.example.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.sample.database.entity.asExternalModel
import com.nimble.sample.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SurveyDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  surveyRepository: SurveyRepository
) : ViewModel() {

  val survey = savedStateHandle.getStateFlow<String?>(surveyIdArg, null)
    .flatMapLatest { id ->
      if (id != null) {
        surveyRepository.getSurveyByTitle(id)
      } else {
        flowOf(null)
      }
    }.map {
      it?.asExternalModel()
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = null
    )
}
