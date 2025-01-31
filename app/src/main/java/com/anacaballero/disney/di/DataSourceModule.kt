package com.anacaballero.disney.di

import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSource
import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSourceImpl
import com.anacaballero.disney.data.dataSources.amiibo.remote.api.AmiiboApi
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSource
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSourceImpl
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteMockDataSourceImpl
import com.anacaballero.disney.data.dataSources.characters.remote.api.DisneyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    // CHARACTERS
    @RemoteDataSource
    @Provides
    @Singleton
    fun provideCharacterRemoteDataSource(@DisneyApi_ disneyApi: DisneyApi): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(disneyApi)
    }

    @MockDataSource
    @Provides
    @Singleton
    fun provideCharacterRemoteMockDataSource(): CharacterRemoteDataSource {
        return CharacterRemoteMockDataSourceImpl()
    }

    // AMIIBO
    @Provides
    @Singleton
    fun provideAmiiboRemoteDataSource(@AmiiboApi_ amiiboApi: AmiiboApi): AmiiboRemoteDataSource {
        return AmiiboRemoteDataSourceImpl(amiiboApi)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockDataSource
