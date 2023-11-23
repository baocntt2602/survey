package com.example.home

import com.example.testdouble.TestSurveyRepository
import com.example.testing.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

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
  fun `test init ui state`() = runTest {
    assertEquals(HomeUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun `test init survey state`() = runTest {
    assertEquals(true, viewModel.survey.value.isEmpty())
  }

  @Test
  fun `test get cache survey - valid case`() = runTest {
    val collectJob = launch(UnconfinedTestDispatcher()) {
      viewModel.survey.collect()
      viewModel.uiState.collect()
    }

    surveyRepository.sendSurveyEntity(defaultEntities)

    assertEquals(HomeUiState.SurveyLoaded,viewModel.uiState.value)
    assertEquals(defaultEntities.size,viewModel.survey.value.size)

    collectJob.cancel()
  }

  @Test
  fun `test get cache survey - empty case`() = runTest {
    val spyRepository = spyk(surveyRepository)
    viewModel = HomeViewModel(spyRepository)
    val collectJob = launch(UnconfinedTestDispatcher()) {
      viewModel.survey.collect()
      viewModel.uiState.collect()
    }

    spyRepository.sendSurveyEntity(emptyList())

    coVerify {
      spyRepository.getRemoteSurveys()
    }

    collectJob.cancel()
  }
}
