package com.nimble.sample.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nimble.sample.database.entity.SurveyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
  @Query(value = "SELECT * FROM survey")
  fun getTopicEntities(): Flow<List<SurveyEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrReplaceSurveys(surveyEntity: List<SurveyEntity>)

  @Query(value = "DELETE FROM survey")
  fun clearAllSurveys()
}
