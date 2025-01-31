package com.anacaballero.disney.data.repositories.characters

import com.anacaballero.disney.data.dataSources.characters.local.CharactersDao
import com.anacaballero.disney.data.dataSources.characters.local.dbo.CharacterDbo
import com.anacaballero.disney.data.dataSources.characters.remote.CharacterRemoteDataSource
import com.anacaballero.disney.data.dataSources.characters.remote.dto.CharacterDto
import com.anacaballero.disney.domain.entities.Character
import com.anacaballero.disney.domain.repositories.characters.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// El que maneja el dato y lo tranforma de dto a entidad y viceversa
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localCharactersDataSource: CharactersDao
) : CharactersRepository {

    override suspend fun getCharacters(): List<Character> {
        return withContext(Dispatchers.IO) {
            val charactersDto = remoteDataSource.getCharacters()

            val charactersDbo = charactersDto.map { charactersDto ->
                charactersDto.toLocal()
            }
            var page = 1



            val characters = charactersDto.map { characterDto ->
                characterDto.toDomain()
            }

            localCharactersDataSource.saveAllCharacters(charactersDbo)

            return@withContext characters
        }
    }

    override suspend fun getCharactersByName(name: String): List<Character> {
        val characterDto = remoteDataSource.getCharactersByName(name)
        return characterDto.map { character ->
            character.toDomain()
        }
    }

    override suspend fun getCharactersByPageSize(pageSize: String): List<Character> {
        val charactersDto = remoteDataSource.getCharactersByPageSize(pageSize)
        val characters: List<Character> = charactersDto.map { characterDto: CharacterDto ->
            characterDto.toDomain()
        }
        return characters
    }

    override suspend fun getCharactersByPage(page: Int): List<Character> {
        TODO("Not yet implemented")
    }


    private fun CharacterDto.toDomain(): Character {
        return Character(
            id = id,
            name = name,
            multimedia = films.plus(shortFilms),
            videoGames = videoGames,
            sourceUrl = sourceUrl,
            image = imageUrl ?: ""
        )
    }

    private fun CharacterDto.toLocal(): CharacterDbo {
        return CharacterDbo(
            id = id,
            name = name,
            sourceUrl = sourceUrl,
            imageUrl = imageUrl ?: "",
            page= null
        )
    }
}