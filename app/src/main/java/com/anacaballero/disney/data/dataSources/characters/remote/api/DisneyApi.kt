package com.anacaballero.disney.data.dataSources.characters.remote.api


import com.anacaballero.disney.data.dataSources.characters.remote.dto.CharactersDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DisneyApi {

    @GET("/character")
    suspend fun getCharacters(): Response<CharactersDataDto>

    @GET("/character")
    suspend fun getCharactersByName(
        @Query("name") name: String
    ): Response<CharactersDataDto>

    @GET("/character")
    suspend fun getCharactersByPageSize(
        @Query("pageSize") pageSize: String
    ): Response<CharactersDataDto>
}