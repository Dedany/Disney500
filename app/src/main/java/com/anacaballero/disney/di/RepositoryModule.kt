package com.anacaballero.disney.di

import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSource
import com.anacaballero.disney.data.dataSources.characters.local.CharactersDao
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSource
import com.anacaballero.disney.data.repositories.characters.AmiiboRepositoryImpl
import com.anacaballero.disney.data.repositories.characters.CharacterRepositoryImpl
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(
        @RemoteDataSource remoteCharactersDataSource: CharacterRemoteDataSource,
        localCharactersDataSource: CharactersDao
    ): CharactersRepository {
        return CharacterRepositoryImpl(
            remoteCharactersDataSource,
            localCharactersDataSource
        )
    }

    @AmiiboRepository
    @Provides
    @Singleton
    fun provideAmiiboRepository(@AmiiboRepository dataSource: AmiiboRemoteDataSource): CharactersRepository {
        return AmiiboRepositoryImpl(dataSource)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AmiiboRepository