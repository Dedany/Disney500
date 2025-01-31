package com.anacaballero.disney.domain.entities

data class Character(
    val id: Int,
    val name: String,
    val multimedia: List<String>,
    val videoGames: List<String>,
    val sourceUrl: String,
    val image: String,
)
