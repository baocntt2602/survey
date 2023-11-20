package com.nimble.survey

import androidx.lifecycle.ViewModel
import com.nimble.sample.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val preferenceRepository: PreferenceRepository
) : ViewModel() {

  val isAuthenticated: Boolean
    get() = preferenceRepository.accessToken != null
}
