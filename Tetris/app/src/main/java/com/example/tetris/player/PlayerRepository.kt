package com.example.tetris.player

import androidx.lifecycle.LiveData

class PlayerRepository(private val playerDao: PlayerDao) {

    // Lista wszystkich graczy
    val readAllData: LiveData<List<Player>> = playerDao.readAllData()

    // Funkcja dodająca nowego gracza
    suspend fun addPlayer(player: Player){
        playerDao.addPlayer(player)
    }

    // Funkcja szukająca gracza po pseudonimie w bazie danych
    suspend fun findByNick(nick : String) : Player? {
        return playerDao.findByNick(nick)
    }

    // Funkcja aktualizująca najwyższy wynik gracza o podanym pseudonimie
    suspend fun updateHighScore(highScore : Int, nick: String) {
        return playerDao.updateHighScore(highScore,nick)
    }

    // Funkcja pobierająca najwyższy wynik gracza o podanym pseudonimie
    fun getHighScore(nick: String) : Int {
        return playerDao.getHighScore(nick)
    }

}