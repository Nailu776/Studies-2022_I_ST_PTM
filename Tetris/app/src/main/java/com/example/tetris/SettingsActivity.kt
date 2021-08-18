package com.example.tetris

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.player.Player
import com.example.tetris.player.PlayerDatabase
import com.example.tetris.player.PlayerViewModel

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    // Ustawienia
    // Pseudonim gracza
    private var userNickname = ""
    // Poziom startowy (działa tylko na soloq)
    private var startLevel = 0
    private lateinit var applyChanges : Button
    private lateinit var nickName : EditText
    private lateinit var mPlayerViewModel: PlayerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        userNickname = intent.getStringExtra("userNickname").toString()
        startLevel = intent.getIntExtra("startLevel",0)
        applyChanges = findViewById(R.id.apply_Changes_Btn)
        applyChanges.setOnClickListener(this)
        nickName = findViewById(R.id.editTextTextPersonName)
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
    }
    override fun onClick(v: View?) {
        when(v){
            applyChanges -> {
                userNickname = nickName.text.toString()
                insertPlayerToDatabaseIfFirstTime()
                setResult(Activity.RESULT_OK,
                        Intent()
                                .putExtra("startLevel", startLevel)
                                .putExtra("userNickname", userNickname))
                finish()
            }
        }
    }

    private fun insertPlayerToDatabaseIfFirstTime() {
        val highScore = 0
        // Sprawdź czy w bazie danych jest dany gracz
//        if(inputCheck)
        // Stworzenie nowego gracza
        val player = Player(0,userNickname,highScore)
        // Dodanie do bazy danych
        mPlayerViewModel.addPlayer(player)
        Toast.makeText(this,"Dodano gracza",Toast.LENGTH_LONG).show()
    }

//    private fun inputCheck(nick: String): Boolean{
//        return PlayerDatabase.getDatabase(this).
//    }

}