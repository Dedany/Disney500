package com.anacaballero.disney.data.repositories.characters

import com.anacaballero.disney.data.dataSources.amiibo.remote.AmiiboRemoteDataSource
import com.anacaballero.disney.data.dataSources.amiibo.remote.dto.AmiiboDto
import com.anacaballero.disney.di.AmiiboApi_
import com.anacaballero.disney.domain.entities.Character
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import javax.inject.Inject

class AmiiboRepositoryImpl @Inject constructor(
    @AmiiboApi_ private val dataSource: AmiiboRemoteDataSource,
): CharactersRepository {
    override suspend fun getCharacters(): List<Character> {
        val amiibos = dataSource.getCharacters()

        return amiibos.mapNotNull { amiiboDto ->
            amiiboDto.toDomain()
        }
    }

    override suspend fun getCharactersAndTotalPages(): Pair<List<Character>, Int> {
        return Pair(listOf(),0)
    }

    override suspend fun getCharactersByName(name: String): List<Character> {
        val amiibos = dataSource.getCharactersByName(name)
        return amiibos.mapNotNull { amiiboDto ->
            amiiboDto.toDomain()
        }
    }

    override suspend fun getCharactersByPageSize(pageSize: String): List<Character> {
        val amiibos = dataSource.getCharacters()

        return amiibos.mapNotNull { amiiboDto ->
            amiiboDto.toDomain()
        }
    }

    override suspend fun getCharactersByPage(page: Int): List<Character> {
       return listOf()
    }

    private fun AmiiboDto.toDomain(): Character? {
        if (head.toIntOrNull() != null) {
            return Character(
                id = head.toInt(),
                name = name,
                multimedia = listOf(serie),
                videoGames = listOf(game),
                sourceUrl = "",
                image = image
            )
        } else {
            return null
        }
    }
}