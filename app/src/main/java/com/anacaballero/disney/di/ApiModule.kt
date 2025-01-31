package com.anacaballero.disney.di

import com.anacaballero.disney.data.dataSources.characters.remote.api.DisneyApi
import com.anacaballero.disney.data.dataSources.amiibo.remote.api.AmiiboApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @DisneyApi_
    @Singleton
    fun providesRetrofitDisney(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.disneyapi.dev")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AmiiboApi_
    @Singleton
    fun providesRetrofitAmiibo(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.amiiboapi.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @DisneyApi_
    fun providesDisneyApi(@DisneyApi_ retrofit: Retrofit): DisneyApi {
        return retrofit.create(DisneyApi::class.java)
    }

    @Provides
    @Singleton
    @AmiiboApi_
    fun providesAmiiboApi(@AmiiboApi_ retrofit: Retrofit): AmiiboApi {
        return retrofit.create(AmiiboApi::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DisneyApi_

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AmiiboApi_
