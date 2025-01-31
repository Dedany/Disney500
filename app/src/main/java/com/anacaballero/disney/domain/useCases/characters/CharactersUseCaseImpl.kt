package com.anacaballero.disney.domain.useCases.characters

import com.anacaballero.disney.data.repositories.characters.CharacterRepositoryImpl
import com.anacaballero.disney.di.AmiiboRepository
import com.anacaballero.disney.domain.entities.Character
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import javax.inject.Inject

class CharactersUseCaseImpl @Inject constructor(
    @AmiiboRepository private val repository: CharactersRepository
) : CharactersUseCase {

    override suspend fun getCharacters(): List<Character> {
        val characters = repository.getCharacters()
        return characters
    }

    override suspend fun getCharactersByName(name: String): List<Character> {
        val characters = if (name.isNotBlank()) {
            repository.getCharactersByName(name.trim())
        } else {
            repository.getCharacters()
        }

        return characters
    }

    override suspend fun getCharactersByPageSize(pageSize: String): List<Character> {
        val characters = if (pageSize.isNotBlank()) {
            repository.getCharactersByPageSize(pageSize).take(pageSize.toInt())
        } else {
            repository.getCharacters()
        }
        return characters
    }

    override suspend fun getCharactersByPage(page: Int): List<Character> {
        val characters = repository.getCharactersByPage(page)
        return characters
    }

    override suspend fun getCharactersAndTotalPages(): Pair<List<Character>, Int> {
        val pair = repository.getCharactersAndTotalPages()
        return pair
    }
}






