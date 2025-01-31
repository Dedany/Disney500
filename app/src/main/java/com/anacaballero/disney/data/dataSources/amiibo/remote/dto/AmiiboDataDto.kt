package com.anacaballero.disney.data.dataSources.amiibo.remote.dto

import com.google.gson.annotations.SerializedName

data class AmiiboDataDto(
    @SerializedName("amiibo")
    val data: List<AmiiboDto>
)
