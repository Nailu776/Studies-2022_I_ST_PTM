package com.example.tetris


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Ustawienia
    // Pseudonim gracza
    private var userNickname = ""
    // Poziom startowy (działa tylko na soloq)
    private var startLevel = 0

    // Te przyciski będą zainicjalizowane onCreate
    private lateinit var play_solo_button : Button
    private lateinit var play_versus_button : Button
    private lateinit var settings_button : Button
    private lateinit var ranking_button : Button
    private lateinit var exit_game : Button

    //Wyświetlanie nicku
    private lateinit var nick_name : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        play_solo_button = findViewById(R.id.play_solo_button)
            play_solo_button.setOnClickListener(this)

        play_versus_button = findViewById(R.id.play_versus_button)
            play_versus_button.setOnClickListener(this)

        settings_button = findViewById(R.id.settings_button)
            settings_button.setOnClickListener(this)

        ranking_button = findViewById(R.id.ranking_button)
            ranking_button.setOnClickListener(this)

        exit_game = findViewById(R.id.exit_game)
            exit_game.setOnClickListener(this)

        nick_name = findViewById(R.id.nick_name)
    }
    override fun onResume() {
        super.onResume()
        if (userNickname.isNotEmpty()) {
            nick_name.visibility = View.VISIBLE
            nick_name.text = resources.getString(R.string.hi, "${userNickname}!")
        } else {
            nick_name.visibility = View.INVISIBLE
        }
    }

    var settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
        }
    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
    }

    override fun onClick(v: View?) {
        when(v){
            play_solo_button -> {
                startActivity(
                    Intent(this, TetrisSoloGameActivity::class.java))
            }
            play_versus_button -> {
                startActivity(
                    Intent(this, VersusGameActivity::class.java))
            }
            settings_button -> {
                startActivityForResult(
                        Intent(this, SettingsActivity::class.java)
                                .putExtra("startLevel", startLevel)
                                .putExtra("userNickname", userNickname),76)
            }
            ranking_button -> {
                startActivity(Intent(this, RankingActivity::class.java))
            }
            exit_game -> {
                finish()
                System.exit(0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            76 -> {
                userNickname = data?.getStringExtra("userNickname").toString()
            }
        }

    }
}