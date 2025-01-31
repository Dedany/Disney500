package com.anacaballero.disney.domain.repositories.characters

import com.anacaballero.disney.domain.entities.Character


interface CharactersRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getCharactersByName(name: String): List<Character>
    suspend fun getCharactersByPageSize(pageSize: String): List<Character>
    suspend fun getCharactersByPage(page: Int): List<Character>
}