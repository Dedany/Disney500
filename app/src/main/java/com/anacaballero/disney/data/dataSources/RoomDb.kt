package com.anacaballero.disney.data.dataSources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anacaballero.disney.data.dataSources.characters.local.CharactersDao
import com.anacaballero.disney.data.dataSources.characters.local.dbo.CharacterDbo

@Database(entities = [CharacterDbo::class] , version = 1) // 2. Anotación de @Database pasando entities en vacio y version en 1
abstract class RoomDb: RoomDatabase() { // 1. La clase creada debe ser "abstract"

    abstract fun charactersDao(): CharactersDao

    // 3. Crear companion object
    companion object {
        private const val DATABASE_NAME = "characters" // Nombre de nuestra Database

        @Volatile // 4. anotación Volatile para permitir que nuestra instancia de base de datos esté al alcance de todos los hilos.
        private var instance: RoomDb? = null
        private val obj = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(obj) { // 5. Funcion invoke que lleva la lógica de llamar a crear la base de datos
            instance ?: createDatabase(context).also { db -> instance = db } // 5.1. Creamos un metodo "createDatabase"
        }

        //6. Llamamos dentro del metodo createDatabase al Room.databaseBuilder y rellenando los parametros del constructor procederemos a hacer un .build()
        private fun createDatabase(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}