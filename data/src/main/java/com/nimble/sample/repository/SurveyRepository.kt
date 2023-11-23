package com.nimble.sample.repository

import arrow.core.Either
import com.nimble.sample.database.dao.SurveyDao
import com.nimble.sample.database.entity.SurveyEntity
import com.nimble.sample.mapper.asEntity
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.network.either.ErrorDetail
import com.nimble.sample.network.either.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface SurveyRepository {

  fun getCachedSurveys(): Flow<List<SurveyEntity>>

  suspend fun getRemoteSurveys(): Either<ErrorResponse, Unit>
}

class DefaultSurveyRepository @Inject constructor(
  private val surveyApi: SurveyApi,
  private val surveyDao: SurveyDao,
) : SurveyRepository {

  override fun getCachedSurveys(): Flow<List<SurveyEntity>> {
    return surveyDao.getTopicEntities()
  }

  override suspend fun getRemoteSurveys(): Either<ErrorResponse, Unit> {
    return surveyApi.getSurveyList().fold({
      if (getCachedSurveys().first().isEmpty()) {
        Either.Left(it)
      } else {
        Either.Right(Unit)
      }
    }, { res ->
      if (res.data.isEmpty()) {
        Either.Left(ErrorResponse(listOf(ErrorDetail(detail = "Empty survey list"))))
      } else {
        surveyDao.insertOrReplaceSurveys(res.data.map { it.attributes.asEntity() })
        Either.Right(Unit)
      }
    })
  }
}
