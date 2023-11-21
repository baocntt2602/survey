package com.nimble.sample.repository

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.network.either.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SurveyRepository {

  fun surveys(): Flow<Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>>
}

class DefaultSurveyRepository @Inject constructor(
  private val surveyApi: SurveyApi
) : SurveyRepository {

  override fun surveys(): Flow<Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>> {
    return flow {
      emit(surveyApi.getSurveyList())
    }
  }
}
