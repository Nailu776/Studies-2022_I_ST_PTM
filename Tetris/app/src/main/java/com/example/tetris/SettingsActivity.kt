package com.example.tetris

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.player.Player
import com.example.tetris.player.PlayerViewModel

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    // Ustawienia
    // Pseudonim gracza
    private var playerNickName = ""
    // Poziom startowy (działa tylko na soloq)
    private var startLevel = 0
    private lateinit var applyChanges : Button
    private lateinit var nickName : EditText
    private lateinit var mPlayerViewModel: PlayerViewModel
    private lateinit var startLevelEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        playerNickName = intent.getStringExtra("playerNickName").toString()
        startLevel = intent.getIntExtra("startLevel",0)
        applyChanges = findViewById(R.id.apply_Changes_Btn)
        applyChanges.setOnClickListener(this)
        nickName = findViewById(R.id.edit_nick)
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        startLevelEditText = findViewById(R.id.edit_start_level)
        startLevelEditText.setText(startLevel.toString())
        nickName.setText(playerNickName)
    }
    override fun onClick(v: View?) {
        when(v){
            applyChanges -> {
                startLevel = startLevelEditText.text.toString().toInt()
                if(startLevel > 19) startLevel = 19
                playerNickName = nickName.text.toString()
                if(playerNickName != "")
                insertPlayerToDatabaseIfFirstTime()
                setResult(Activity.RESULT_OK,
                        Intent()
                                .putExtra("startLevel", startLevel)
                                .putExtra("playerNickName", playerNickName))
                finish()

            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK,
                Intent()
                        .putExtra("startLevel", startLevel)
                        .putExtra("playerNickName", playerNickName))
        finish()
        super.onBackPressed()
    }


    private fun insertPlayerToDatabaseIfFirstTime() {
        val highScore = 0
        // Sprawdź czy w bazie danych jest dany gracz
        // Stworzenie nowego gracza
        val player = Player(0,playerNickName,highScore)
        // Dodanie do bazy danych
        mPlayerViewModel.addPlayer(player,playerNickName)
        Toast.makeText(this, "Witaj",Toast.LENGTH_LONG).show()
    }

}