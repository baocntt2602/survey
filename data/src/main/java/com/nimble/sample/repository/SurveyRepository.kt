package com.nimble.sample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import arrow.core.Either
import com.nimble.sample.database.dao.SurveyDao
import com.nimble.sample.database.entity.SurveyEntity
import com.nimble.sample.mapper.asEntity
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.network.either.ErrorDetail
import com.nimble.sample.network.either.ErrorResponse
import com.nimble.sample.pagingsource.SurveyPagingSource
import com.nimble.sample.repository.SurveyRepository.Companion.SURVEY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface SurveyRepository {

  fun getCachedSurveys(): Flow<List<SurveyEntity>>

  suspend fun getRemoteSurveys(): Either<ErrorResponse, Unit>

  fun getSurveyByTitle(title: String): Flow<SurveyEntity>

  fun getSurveyPageData(): Flow<PagingData<SurveyOverview>>

  companion object {
    const val SURVEY_PAGE_SIZE = 5
  }
}

class DefaultSurveyRepository @Inject constructor(
  private val surveyApi: SurveyApi,
  private val surveyDao: SurveyDao,
) : SurveyRepository {

  override fun getCachedSurveys(): Flow<List<SurveyEntity>> {
    return surveyDao.getTopicEntities()
  }

  override suspend fun getRemoteSurveys(): Either<ErrorResponse, Unit> {
    val cachedSurveys = getCachedSurveys().first()
    return surveyApi.getSurveyList().fold({
      if (cachedSurveys.isEmpty()) {
        Either.Left(it)
      } else {
        Either.Right(Unit)
      }
    }, { res ->
      if (res.data.isEmpty() && cachedSurveys.isEmpty()) {
        Either.Left(ErrorResponse(listOf(ErrorDetail(detail = "Empty survey list"))))
      } else {
        surveyDao.insertOrReplaceSurveys(res.data.map { it.attributes.asEntity() })
        Either.Right(Unit)
      }
    })
  }

  override fun getSurveyByTitle(title: String): Flow<SurveyEntity> {
    return surveyDao.getTopicEntity(title)
  }

  override fun getSurveyPageData(): Flow<PagingData<SurveyOverview>> {
    return Pager(
      config = PagingConfig(
        pageSize = SURVEY_PAGE_SIZE
      ),
      pagingSourceFactory = {
        SurveyPagingSource(surveyApi)
      }
    ).flow
  }
}
