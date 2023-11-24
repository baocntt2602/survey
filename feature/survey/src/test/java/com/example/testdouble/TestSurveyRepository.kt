package com.example.testdouble

import arrow.core.Either
import com.nimble.sample.database.entity.SurveyEntity
import com.nimble.sample.network.either.ErrorResponse
import com.nimble.sample.repository.SurveyRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestSurveyRepository : SurveyRepository {

  private val surveyFlow: MutableSharedFlow<List<SurveyEntity>> =
    MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  override fun getCachedSurveys(): Flow<List<SurveyEntity>> = surveyFlow

  override suspend fun getRemoteSurveys(): Either<ErrorResponse, Unit> = Either.Right(Unit)

  override fun getSurveyByTitle(title: String): Flow<SurveyEntity> {
    return surveyFlow.map { it.first { it.title == title } }
  }

  fun sendSurveyEntity(entities: List<SurveyEntity>) {
    surveyFlow.tryEmit(entities)
  }
}
