package com.nimble.sample.network.api

import arrow.core.Either
import com.nimble.sample.model.ResponseWrapper
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.either.ErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SurveyApi {
  @GET("surveys")
  suspend fun getSurveyList(
    @Query("page[number]") page: Int = 1,
    @Query("page[size]") pageSize: Int = 5,
  ): Either<ErrorResponse, ResponseWrapper<List<SurveyOverview>>>
}
