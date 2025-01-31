package com.anacaballero.disney.data.dataSources.amiibo.remote.api

import com.anacaballero.disney.data.dataSources.amiibo.remote.dto.AmiiboDataDto
import com.anacaballero.disney.data.dataSources.amiibo.remote.dto.AmiiboDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AmiiboApi {

    @GET("/api/amiibo/")
    suspend fun getCharacters(): Response<AmiiboDataDto>


    @GET("/api/amiibo/")
    suspend fun getCharactersByName(
        @Query("name") name: String
    ): Response<AmiiboDataDto>
}