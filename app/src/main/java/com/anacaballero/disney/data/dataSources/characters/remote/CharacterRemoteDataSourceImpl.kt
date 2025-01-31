package com.anacaballero.disney.data.dataSources.characters.remote

import com.anacaballero.disney.data.dataSources.characters.remote.api.DisneyApi
import com.anacaballero.disney.data.dataSources.characters.remote.dto.CharacterDto
import com.anacaballero.disney.di.DisneyApi_
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    @DisneyApi_ private val disneyApi: DisneyApi
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(): List<CharacterDto> {
        val characters = disneyApi.getCharacters().body()?.data ?: emptyList()
        return characters
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        val characters = disneyApi.getCharactersByName(name).body()?.data ?: emptyList()
        return characters
    }

    override suspend fun getCharactersByPageSize(pageSize: String): List<CharacterDto> {
        val characters = disneyApi.getCharactersByPageSize(pageSize).body()?.data ?: emptyList()
        return characters
    }
}