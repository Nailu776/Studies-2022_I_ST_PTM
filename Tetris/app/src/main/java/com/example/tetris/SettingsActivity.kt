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
    // Przycisk zatwierdzenia
    private lateinit var applyChanges : Button
    // Edycja pseudonimu
    private lateinit var nickName : EditText
    private lateinit var mPlayerViewModel: PlayerViewModel
    private lateinit var startLevelEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        // Ustawienie przesyłanych parametrów
        playerNickName = intent.getStringExtra("playerNickName").toString()
        startLevel = intent.getIntExtra("startLevel",0)
        // Znalezienie widoków i ustawienie onClickListenera do przycisku zatwierdzenia
        applyChanges = findViewById(R.id.apply_Changes_Btn)
        applyChanges.setOnClickListener(this)
        nickName = findViewById(R.id.edit_nick)
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        startLevelEditText = findViewById(R.id.edit_start_level)
        // Wpisanie przesłanych parametrów w odpowiednie miejsca
        startLevelEditText.setText(startLevel.toString())
        nickName.setText(playerNickName)
    }
    override fun onClick(v: View?) {
        when(v){
            applyChanges -> {
                // Pobranie wybranego poziomu
                startLevel = startLevelEditText.text.toString().toInt()
                if(startLevel > 18) {
                    // Maksymalny poziom to 18
                    startLevel = 18
                }
                // Pobieranie wybranego nicku
                playerNickName = nickName.text.toString()
                // W przypadku gdy nie jest on pusty (lub samymi spacjami)
                if(playerNickName.trim().isNotEmpty()) {
                    // Dodaj gracza do bazy danych jeżeli gra pierwszy raz
                    insertPlayerToDatabaseIfFirstTime()
                }
                else {
                    // W przeciwnym wypadku ustaw pusty nick (nie będzie on obsługiwany dalej)
                    playerNickName = ""
                }
                // Ustaw rezultat aktywności
                setResult(Activity.RESULT_OK,
                        Intent()
                                .putExtra("startLevel", startLevel)
                                .putExtra("playerNickName", playerNickName))
                // Zakończ aktywność
                finish()
            }
        }
    }

    // W przypadku wciśnięcia przycisku cofnij zamiast apply button
    // po prostu przekaż otrzymane dane w rezultacie aktywności
    override fun onBackPressed() {
        setResult(Activity.RESULT_OK,
                Intent()
                        .putExtra("startLevel", startLevel)
                        .putExtra("playerNickName", playerNickName))
        finish()
        super.onBackPressed()
    }

    // Wstaw gracza do bazy danych jeżeli pierwszy raz gra
    private fun insertPlayerToDatabaseIfFirstTime() {
        // Stworzenie nowego gracza
        val player = Player(0,playerNickName,0)
        // Dodanie do bazy danych
        mPlayerViewModel.addPlayer(player,playerNickName)
        // Toast powitalny
        Toast.makeText(this, "Witaj",Toast.LENGTH_LONG).show()
    }

}