package com.nimble.sample.di

import com.nimble.sample.repository.DefaultPreferenceRepository
import com.nimble.sample.repository.DefaultSurveyRepository
import com.nimble.sample.repository.DefaultUserRepository
import com.nimble.sample.repository.PreferenceRepository
import com.nimble.sample.repository.SurveyRepository
import com.nimble.sample.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
  @Binds
  @Singleton
  fun bindsUserRepository(userRepository: DefaultUserRepository): UserRepository

  @Binds
  @Singleton
  fun bindPreferenceRepository(preferenceRepository: DefaultPreferenceRepository): PreferenceRepository

  @Binds
  @Singleton
  fun bindSurveyRepository(surveyRepository: DefaultSurveyRepository): SurveyRepository
}
