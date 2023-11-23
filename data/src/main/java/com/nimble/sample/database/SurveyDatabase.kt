package com.nimble.sample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nimble.sample.database.dao.SurveyDao
import com.nimble.sample.database.entity.SurveyEntity

@Database(
  entities = [SurveyEntity::class],
  version = 1
)
abstract class SurveyDatabase: RoomDatabase() {
  abstract fun surveyDao(): SurveyDao
}
