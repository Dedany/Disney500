package com.anacaballero.disney.di

import android.app.Application
import com.anacaballero.disney.data.dataSources.RoomDb
import com.anacaballero.disney.data.dataSources.characters.local.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(application: Application): RoomDb {
        return RoomDb.invoke(application)
    }

    @Provides
    @Singleton
    fun provideCharactersDao(database: RoomDb): CharactersDao {
        return database.charactersDao()
    }

}