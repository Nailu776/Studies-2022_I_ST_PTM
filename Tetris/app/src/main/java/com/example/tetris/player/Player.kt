package com.example.tetris.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
class Player(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nick") val nick: String?,
    @ColumnInfo(name = "high_score") val highScore: Int = 0){

}