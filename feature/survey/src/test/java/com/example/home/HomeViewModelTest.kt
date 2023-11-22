package com.example.home

import arrow.core.Either
import com.example.testing.MainDispatcherRule
import com.example.testing.repository.TestSurveyRepository
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.network.either.ErrorResponse
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()


  private lateinit var viewModel: HomeViewModel
  private val surveyRepository = TestSurveyRepository()

  @Before
  fun setUp() {
    viewModel = HomeViewModel(surveyRepository)
  }

  @Test
  fun `test init state`() = runTest {
    Assert.assertEquals(HomeUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun `test get surveys success`() = runTest {
    val collectJob = launch(UnconfinedTestDispatcher()) {
      viewModel.uiState.collect()
    }

    surveyRepository.sendSurvey(Either.Right(ResponseWrapper(listOf(mockk()))))

    Assert.assertEquals(true, viewModel.uiState.value is HomeUiState.SurveyLoaded)
    collectJob.cancel()
  }

  @Test
  fun `test get survey failed`() = runTest {
    val collectJob = launch(UnconfinedTestDispatcher()) {
      viewModel.uiState.collect()
    }

    surveyRepository.sendSurvey(Either.Left(ErrorResponse(listOf())))

    Assert.assertEquals(true, viewModel.uiState.value is HomeUiState.Error)
    collectJob.cancel()
  }
}
