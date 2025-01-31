package com.anacaballero.disney.data.dataSources.amiibo.remote

import com.anacaballero.disney.data.dataSources.amiibo.remote.api.AmiiboApi
import com.anacaballero.disney.data.dataSources.amiibo.remote.dto.AmiiboDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AmiiboRemoteDataSourceImpl @Inject constructor(private val amiiboApi: AmiiboApi) :
    AmiiboRemoteDataSource {

    override suspend fun getCharacters(): List<AmiiboDto> {
        val amiibos = amiiboApi.getCharacters().body()?.data ?: emptyList()
        return amiibos
    }

    override suspend fun getCharactersByName(name: String): List<AmiiboDto> {
        val amiibos = amiiboApi.getCharactersByName(name).body()?.data ?: emptyList()
        return amiibos
    }
}