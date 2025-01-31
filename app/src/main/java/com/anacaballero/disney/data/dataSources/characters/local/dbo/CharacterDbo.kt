package com.anacaballero.disney.data.dataSources.characters.local.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDbo(
    @PrimaryKey val id: Int,
    val name: String,
    /*
    val films: List<String>,
    @ColumnInfo(name = "short_films") val shortFilms: List<String>,
    @ColumnInfo(name = "tv_shows") val tvShows: List<String>,
    @ColumnInfo(name = "video_games") val videoGames: List<String>,
    */
    @ColumnInfo(name = "source_url") val sourceUrl: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "page") var page: Int?

)