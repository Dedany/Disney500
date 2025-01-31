package com.anacaballero.disney.data.dataSources.characters.remote

import com.anacaballero.disney.data.dataSources.characters.remote.dto.CharacterDto

interface CharacterRemoteDataSource {
    suspend fun getCharacters(): List<CharacterDto>
    suspend fun getCharactersByName(name: String): List<CharacterDto>
    suspend fun getCharactersByPageSize(pageSize: String): List<CharacterDto>
}