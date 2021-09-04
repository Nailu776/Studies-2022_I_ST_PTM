package com.example.tetris.player

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
// Data Access Object
interface PlayerDao {

    // Add player
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player: Player)

    // get all players for ranking
    @Query("SELECT * FROM player_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Player>>

    // Update whole player
    @Update
    fun update(player: Player)

    // Delete player
    @Delete
    fun delete(player: Player)

    // Get all players
    @Query("SELECT * FROM player_table")
    fun getAll(): List<Player>

    // Find player by nick
    @Query("SELECT * FROM player_table WHERE nick LIKE :nick")
    suspend fun findByNick(nick: String): Player?

    // Update high score
    @Query("UPDATE player_table SET high_score =:highScore WHERE nick LIKE :nick" )
    suspend fun updateHighScore(highScore :Int, nick: String)

    // Get High score
    @Query("SELECT high_score FROM player_table WHERE nick LIKE :nick")
    fun getHighScore(nick: String) : Int

}