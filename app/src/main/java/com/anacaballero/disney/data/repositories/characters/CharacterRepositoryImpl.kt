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
import kotlin.math.ceil


// El que maneja el dato y lo tranforma de dto a entidad y viceversa
class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localCharactersDataSource: CharactersDao
) : CharactersRepository {

    override suspend fun getCharacters(): List<Character> {
        var page = 1
        return withContext(Dispatchers.IO) {
            val charactersDto = remoteDataSource.getCharacters()

            val charactersDbo = charactersDto.map { charactersDto ->
                charactersDto.toLocal()
            }
            val characters = charactersDto.map { characterDto ->
                characterDto.toDomain()
            }
            return@withContext characters


            val charactersAmount = 10
            val totalPages =
                ceil((charactersDbo.size / charactersAmount).toFloat())  //redondea para arriba

            for (character in charactersDbo.indices step charactersAmount) {
                val characters = charactersDbo.slice(
                    character until minOf(
                        character + charactersAmount,
                        charactersDbo.size
                    )
                )

                characters.forEach { character ->
                    character.page = page
                }

                localCharactersDataSource.saveAllCharacters(characters)
                page++
            }


            return@withContext characters
        }
    }

    override suspend fun getCharactersAndTotalPages(): Pair<List<Character>, Int> {
        var page = 1
        return withContext(Dispatchers.IO) {
            val charactersDto = remoteDataSource.getCharacters()

            val charactersDbo = charactersDto.map { charactersDto ->
                charactersDto.toLocal()
            }

            val characters = charactersDto.map { characterDto ->
                characterDto.toDomain()
            }

            val charactersAmount = 10
            val totalPages = ceil((charactersDbo.size / charactersAmount).toFloat())

            for (character in charactersDbo.indices step charactersAmount) {
                val characters = charactersDbo.slice(
                    character until minOf(
                        character + charactersAmount,
                        charactersDbo.size
                    )
                )

                characters.forEach { character ->
                    character.page = page
                }

                localCharactersDataSource.saveAllCharacters(characters)
                page++
            }

            val charactersDboInFirstPage = localCharactersDataSource.getCharactersByPage(1)
            val charactersFromLocal = charactersDboInFirstPage.map { it.toDomain() }
            return@withContext Pair(charactersFromLocal, totalPages.toInt())
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
        val charactersDbo = localCharactersDataSource.getCharactersByPage(page)
        val characters = charactersDbo.map { characterDbo -> characterDbo.toDomain() }
        return characters
    }


    private fun CharacterDto.toLocal(): CharacterDbo {
        return CharacterDbo(
            id = id,
            name = name,
            sourceUrl = sourceUrl,
            imageUrl = imageUrl ?: "",
            page = null
        )
    }

    private fun CharacterDbo.toDomain(): Character {
        return Character(
            id = id,
            name = name,
            multimedia = listOf(),
            videoGames = listOf(),
            sourceUrl = sourceUrl,
            image = imageUrl ?: ""
        )
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
}
