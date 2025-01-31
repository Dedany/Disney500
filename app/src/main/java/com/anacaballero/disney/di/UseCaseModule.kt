package com.anacaballero.disney.di

import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCase
import com.anacaballero.disney.domain.useCases.characters.CharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCharactersUseCase(
        repository: CharactersRepository
    ): CharactersUseCase {
        return CharactersUseCaseImpl(
            repository = repository
        )
    }
}