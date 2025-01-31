package com.anacaballero.disney.data.dataSources.characters.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anacaballero.disney.data.dataSources.characters.local.dbo.CharacterDbo

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<CharacterDbo>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Int): List<CharacterDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCharacters(charactersDbo: List<CharacterDbo>)

    @Query("DELETE FROM characters WHERE id = :id")
    fun deleteCharacter(id: Int)

}