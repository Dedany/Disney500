package com.anacaballero.disney.data.dataSources.amiibo.remote.dto

import com.google.gson.annotations.SerializedName

data class AmiiboDto(
    val head: String,
    val tail: String,
    val name: String,
    @SerializedName("amiiboSeries")
    val serie: String,
    @SerializedName("gameSeries")
    val game: String,
    val image: String,
    val type: String
)
