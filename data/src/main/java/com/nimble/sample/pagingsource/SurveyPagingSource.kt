package com.nimble.sample.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nimble.sample.model.response.SurveyOverview
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.repository.SurveyRepository.Companion.SURVEY_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class SurveyPagingSource(
  private val surveyApi: SurveyApi
) : PagingSource<Int, SurveyOverview>() {
  override fun getRefreshKey(state: PagingState<Int, SurveyOverview>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyOverview> {
    val page = params.key ?: 1
    return try {
      surveyApi.getSurveyList(
        page = page,
        pageSize = SURVEY_PAGE_SIZE
      ).fold({
        LoadResult.Error(Throwable(it.prettyMessage))
      }, { res ->
        LoadResult.Page(
          data = res.data,
          prevKey = if (page > 1) page.minus(1) else null,
          nextKey = if (res.data.isNotEmpty() && res.data.size >= SURVEY_PAGE_SIZE) page.plus(1) else null
        )
      })
    } catch (exception: IOException) {
      return LoadResult.Error(exception)
    } catch (exception: HttpException) {
      return LoadResult.Error(exception)
    }
  }
}
