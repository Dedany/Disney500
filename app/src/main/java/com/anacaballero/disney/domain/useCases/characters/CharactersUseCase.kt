package com.anacaballero.disney.domain.useCases.characters

import com.anacaballero.disney.domain.entities.Character

interface CharactersUseCase {
    suspend fun getCharacters(): List<Character>
    suspend fun getCharactersByName(name: String): List<Character>
    suspend fun getCharactersByPageSize(pageSize: String): List<Character>
    suspend fun getCharactersByPage(page: Int): List<Character>
    suspend fun getCharactersAndTotalPages(): Pair<List<Character>, Int>
}