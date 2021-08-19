package com.example.tetris.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val readAllData: LiveData<List<Player>>
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
}