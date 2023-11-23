package com.example.home

import arrow.core.Either
import com.example.testdouble.TestSurveyDao
import com.example.testing.MainDispatcherRule
import com.nimble.sample.database.entity.SurveyEntity
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.response.SurveyAttributes
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.repository.DefaultSurveyRepository
import com.nimble.sample.repository.SurveyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

val defaultEntities = listOf(
  testEntity("1"),
  testEntity("2"),
  testEntity("3"),
)

class SurveyRepositoryTest {
  @get:Rule
  val dispatcherRule = MainDispatcherRule()

  private val surveyDao = TestSurveyDao()
  private val surveyApi = mockk<SurveyApi>()
  private lateinit var surveyRepository: SurveyRepository

  @Before
  fun setUp() {
    surveyRepository = DefaultSurveyRepository(surveyApi, surveyDao)
  }

  @After
  fun tearDown() {
    surveyDao.clearAllSurveys()
  }

  @Test
  fun `test get cached survey`() = runTest {
    insertDefaultEntities()
    val result = surveyRepository.getCachedSurveys().first()
    assertEquals(result.size, 3)
  }

  @Test
  fun `test get remote surveys success - empty response and empty cache`() = runTest {
    coEvery { surveyApi.getSurveyList() } returns Either.Right(ResponseWrapper(emptyList()))
    val result = surveyRepository.getRemoteSurveys()
    assertEquals(true, result is Either.Left)
  }

  @Test
  fun `test get remote surveys success - valid response`() = runTest {
    val fakeRemoteSurvey = SurveyOverview(
      id = "",
      type = "",
      attributes = SurveyAttributes(
        coverImageUrl = ""
      )
    )
    coEvery { surveyApi.getSurveyList() } returns Either.Right(ResponseWrapper(listOf(fakeRemoteSurvey)))
    val result = surveyRepository.getRemoteSurveys().orNull()

    assertEquals(true, result != null)
    assertEquals(1, surveyDao.getTopicEntities().first().size)
  }

  @Test
  fun `test get remote surveys fail - empty cache`() = runTest {
    coEvery { surveyApi.getSurveyList() } returns Either.Left(mockk())
    val res = surveyRepository.getRemoteSurveys()
    assertEquals(true, res.isLeft())
  }

  @Test
  fun `test get remote surveys fail - available cache`() = runTest {
    insertDefaultEntities()
    coEvery { surveyApi.getSurveyList() } returns Either.Left(mockk())
    val res = surveyRepository.getRemoteSurveys()
    assertEquals(true, res.isRight())
  }

  private suspend fun insertDefaultEntities() {
    surveyDao.insertOrReplaceSurveys(defaultEntities)
  }
}

private fun testEntity(title: String): SurveyEntity =
  SurveyEntity(
    title = title,
    description = "",
    coverImageUrl = "",
    createdAt = ""
  )
