package com.anacaballero.disney.data.dataSources.characters.remote

import com.anacaballero.disney.data.dataSources.characters.remote.dto.CharacterDto

class CharacterRemoteMockDataSourceImpl: CharacterRemoteDataSource {

    private val mockCharactersDto: List<CharacterDto> = listOf(
        CharacterDto(
            id = 0,
            name = "Mickey Mouse",
            films = listOf("Toy Story", "Monsters Inc."),
            shortFilms = listOf("Toy Story Short film"),
            videoGames = listOf("Super Mario Bros.", "DuckTales"),
            sourceUrl = "https://disney.fandom.com/wiki/Mickey_Mouse",
            tvShows = listOf(),
            imageUrl = ""
        ),
        CharacterDto(
            id = 1,
            name = "Mickey Mouse",
            films = listOf("Steamboat Willie", "Fantasia", "Mickey's Christmas Carol"),
            shortFilms = listOf("Plane Crazy", "The Band Concert"),
            tvShows = listOf("Mickey Mouse Clubhouse", "Mickey Mouse Works"),
            videoGames = listOf("Kingdom Hearts", "Epic Mickey"),
            sourceUrl = "https://disney.fandom.com/wiki/Mickey_Mouse",
            imageUrl = "https://example.com/mickey.jpg"
        ),
        CharacterDto(
            id = 2,
            name = "Donald Duck",
            films = listOf("The Wise Little Hen", "Fantasia 2000"),
            shortFilms = listOf("Donald's Ostrich", "Donald's Nephews"),
            tvShows = listOf("Mickey Mouse Clubhouse", "Quack Pack"),
            videoGames = listOf("Kingdom Hearts", "DuckTales"),
            sourceUrl = "https://disney.fandom.com/wiki/Donald_Duck",
            imageUrl = "https://example.com/donald.jpg"
        ),
        CharacterDto(
            id = 3,
            name = "Goofy",
            films = listOf("A Goofy Movie", "Fantasia"),
            shortFilms = listOf("How to Play Football", "Goofy's Glider"),
            tvShows = listOf("Goof Troop", "Mickey Mouse Clubhouse"),
            videoGames = listOf("Kingdom Hearts", "Goof Troop (SNES)"),
            sourceUrl = "https://disney.fandom.com/wiki/Goofy",
            imageUrl = "https://example.com/goofy.jpg"
        ),
        CharacterDto(
            id = 4,
            name = "Simba",
            films = listOf("The Lion King", "The Lion King II: Simba's Pride", "The Lion King 1½"),
            shortFilms = emptyList(),
            tvShows = listOf("The Lion Guard"),
            videoGames = listOf("The Lion King (SNES)", "Kingdom Hearts II"),
            sourceUrl = "https://disney.fandom.com/wiki/Simba",
            imageUrl = "https://example.com/simba.jpg"
        ),
        CharacterDto(
            id = 5,
            name = "Ariel",
            films = listOf("The Little Mermaid", "The Little Mermaid II: Return to the Sea", "The Little Mermaid: Ariel's Beginning"),
            shortFilms = emptyList(),
            tvShows = listOf("The Little Mermaid (TV series)"),
            videoGames = listOf("Kingdom Hearts", "The Little Mermaid (NES)"),
            sourceUrl = "https://disney.fandom.com/wiki/Ariel",
            imageUrl = "https://example.com/ariel.jpg"
        )
    )

    override suspend fun getCharacters(): List<CharacterDto> {
        return mockCharactersDto
    }

    override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        return mockCharactersDto.filter { characterDto ->
            characterDto.name == name
            characterDto.name.equals(name, true)
            characterDto.name.lowercase() == name.lowercase()
            // Contains
            // Nombre original: Mickey Mouse
            // Búsqueda: Mick
            characterDto.name.contains(name)
            characterDto.name.contains(name, ignoreCase = true)
            characterDto.name.lowercase().contains(name.lowercase())
        }
    }

    override suspend fun getCharactersByPageSize(pageSize: String): List<CharacterDto> {
        return mockCharactersDto.take(pageSize.toInt())
    }
}