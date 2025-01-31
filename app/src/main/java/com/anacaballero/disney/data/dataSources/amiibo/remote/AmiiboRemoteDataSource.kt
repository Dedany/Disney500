package com.anacaballero.disney.data.dataSources.amiibo.remote

import com.anacaballero.disney.data.dataSources.amiibo.remote.dto.AmiiboDto

interface AmiiboRemoteDataSource {
    suspend fun getCharacters(): List<AmiiboDto>
    suspend fun getCharactersByName(name: String): List<AmiiboDto>
}