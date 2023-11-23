package com.example.testdouble

import com.nimble.sample.database.dao.SurveyDao
import com.nimble.sample.database.entity.SurveyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestSurveyDao : SurveyDao {

  private var entitiesStateFlow = MutableStateFlow(
    emptyList<SurveyEntity>(),
  )

  override fun getTopicEntities(): Flow<List<SurveyEntity>> = entitiesStateFlow

  override suspend fun insertOrReplaceSurveys(surveyEntity: List<SurveyEntity>) {
    entitiesStateFlow.update { oldValues ->
      (surveyEntity + oldValues)
        .distinctBy(SurveyEntity::title)
    }
  }

  override fun clearAllSurveys() {
    entitiesStateFlow.update {
      emptyList()
    }
  }
}
