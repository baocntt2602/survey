package com.example.testing.repository

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.either.ErrorResponse
import com.nimble.sample.repository.SurveyRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestSurveyRepository : SurveyRepository {

  private val surveyFlow: MutableSharedFlow<Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>> =
    MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override fun surveys(): Flow<Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>> {
    return surveyFlow
  }

  fun sendSurvey(either: Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>) {
    surveyFlow.tryEmit(either)
  }
}
