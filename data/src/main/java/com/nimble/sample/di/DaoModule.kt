package com.nimble.sample.di

import com.nimble.sample.database.SurveyDatabase
import com.nimble.sample.database.dao.SurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
  @Provides
  fun providesSurveyDao(
    database: SurveyDatabase,
  ): SurveyDao = database.surveyDao()
}
