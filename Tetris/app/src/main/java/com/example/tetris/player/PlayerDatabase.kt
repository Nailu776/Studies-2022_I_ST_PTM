package com.example.tetris.player

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Adnotacja @Database z listą encji
@Database(entities = [Player::class], version = 1, exportSchema = false)
// Extends RoomDatabase
abstract class PlayerDatabase : RoomDatabase() {
    // Funkcja zwracająca PlayerDao
    abstract fun playerDao(): PlayerDao

    companion object{
        @Volatile
        // Instancja bazy danych
        private var INSTANCE: PlayerDatabase? = null
        // stworzenie tej instancji
        fun getDatabase(context: Context): PlayerDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "player_database"
                )
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}