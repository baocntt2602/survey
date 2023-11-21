package com.nimble.sample.network.di

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.nimble.sample.core.network.BuildConfig
import com.nimble.sample.network.api.SurveyApi
import com.nimble.sample.network.api.UserApi
import com.nimble.sample.network.api.middleware.DefaultInterceptor
import com.nimble.sample.network.data.DefaultTokenStorage
import com.nimble.sample.network.data.TokenStorage
import com.nimble.sample.network.either.EitherCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val READ_TIMEOUT: Long = 120
  private const val WRITE_TIMEOUT: Long = 120

  @Singleton
  @Provides
  fun provideTokenStorage(
    @ApplicationContext context: Context,
  ): TokenStorage {
    return DefaultTokenStorage.create(context)
  }

  @Singleton
  @Provides
  fun provideHeaderInterceptor(tokenStorage: TokenStorage): DefaultInterceptor = DefaultInterceptor(tokenStorage)

  @Provides
  @Singleton
  internal fun provideCache(application: Application): Cache {
    val cacheSize = (25 * 1024 * 1024).toLong() // 25 MB
    val httpCacheDirectory = File(application.cacheDir, "http-cache")
    return Cache(httpCacheDirectory, cacheSize)
  }

  @Provides
  fun provideOktHttpClient(
    cache: Cache,
    defaultInterceptor: DefaultInterceptor
  ): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
      .cache(cache)
      .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
      .hostnameVerifier { _, _ -> true }
      .addInterceptor(defaultInterceptor)
    if (BuildConfig.DEBUG) {
      val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }
      httpClientBuilder.addInterceptor(logging)
    }
    return httpClientBuilder.build()
  }

  @Singleton
  @Provides
  fun provideDefaultRetrofit(
    mapper: ObjectMapper,
    okHttpClient: OkHttpClient,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addCallAdapterFactory(EitherCallAdapterFactory())
      .client(okHttpClient)
      .addConverterFactory(JacksonConverterFactory.create(mapper))
      .build()
  }

  @Provides
  fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

  @Provides
  fun provideSurveyApi(retrofit: Retrofit): SurveyApi = retrofit.create(SurveyApi::class.java)
}
