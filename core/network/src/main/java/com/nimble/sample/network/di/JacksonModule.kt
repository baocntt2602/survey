package com.nimble.sample.network.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object JacksonModule {

  @Provides
  fun provideObjectMapper(): ObjectMapper {
    return JsonMapper().apply {
      val module = SimpleModule().apply {
        configure(SerializationFeature.INDENT_OUTPUT, true)
        configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      }
      registerModule(module)
      registerModule(KotlinModule())
    }
  }
}
