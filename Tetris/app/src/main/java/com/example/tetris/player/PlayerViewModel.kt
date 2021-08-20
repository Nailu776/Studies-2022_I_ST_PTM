package com.example.tetris.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Player>>

    private val repository: PlayerRepository


    init {
        val playerDao = PlayerDatabase.getDatabase(application).playerDao()
        repository = PlayerRepository(playerDao)
        readAllData = repository.readAllData
    }

    fun addPlayer(player: Player, nick : String){
        viewModelScope.launch(Dispatchers.IO){
            if(repository.findByNick(nick) == null)
          repository.addPlayer(player)
        }
    }

    fun updateHighScore(highScore: Int, nick: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateHighScore(highScore,nick)
        }
    }

    fun getHighScore(nick: String) : Int {
        return repository.getHighScore(nick) ?: 0
    }
}