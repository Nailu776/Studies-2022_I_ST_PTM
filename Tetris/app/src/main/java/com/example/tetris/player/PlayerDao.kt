package com.example.tetris.player

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player_table")
    fun getAll(): List<Player>

    @Query("SELECT * FROM player_table WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>

    @Query("SELECT * FROM player_table WHERE nick LIKE :nick")
    fun findByNick(nick: String): Player

//    @Insert
//    fun insertAll(vararg players: Player)

    @Delete
    fun delete(player: Player)

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player: Player)


    @Query("SELECT * FROM player_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Player>>
}