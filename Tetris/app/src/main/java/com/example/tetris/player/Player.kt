package com.example.tetris.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Encja gracza
@Entity(tableName = "player_table")
class Player(
    // Identyfikator
    @PrimaryKey(autoGenerate = true) val id: Int,
    // Pseudonim
    @ColumnInfo(name = "nick") val nick: String,
    // Najwyższy wynik, ktory można porownywać z najwyższym wynikiem innego gracza
    @ColumnInfo(name = "high_score") val highScore: Int = 0): Comparable<Player> {
        override fun compareTo(other: Player): Int = this.highScore.compareTo(other.highScore)
    }