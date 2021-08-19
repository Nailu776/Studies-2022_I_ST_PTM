package com.example.tetris.player

import androidx.lifecycle.LiveData

class PlayerRepository(private val playerDao: PlayerDao) {

    val readAllData: LiveData<List<Player>> = playerDao.readAllData()

    suspend fun addPlayer(player: Player){
        playerDao.addPlayer(player)
    }

    suspend fun findByNick(nick : String) : Player? {
        return playerDao.findByNick(nick)
    }


}