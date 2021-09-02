package com.example.tetris.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    // Lista wszystkich graczy
    val readAllData: LiveData<List<Player>>

    // Repozytorium do wywoływania metod
    private val repository: PlayerRepository


    init {
        // Data Access Object
        val playerDao = PlayerDatabase.getDatabase(application).playerDao()
        // Inicjalizacja repozytorium oraz listy wszystkich graczy
        repository = PlayerRepository(playerDao)
        readAllData = repository.readAllData
    }

    // Funkcja dodająca gracza do bazy danych
    fun addPlayer(player: Player, nick : String){
        viewModelScope.launch(Dispatchers.IO){
            // jeżeli nie ma danego gracza już w bazie danych
            if(repository.findByNick(nick) == null){
                repository.addPlayer(player)
            }
        }
    }

    // Funkcja aktualizująca najwyższy wynik gracza o podanym pseudonimie
    fun updateHighScore(highScore: Int, nick: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateHighScore(highScore,nick)
        }
    }

    // Funkcja pobierająca najwyższy wynik po pseudonimie gracza
    fun getHighScore(nick: String) : Int {
        return repository.getHighScore(nick) ?: 0
    }
}