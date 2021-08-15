package com.example.tetris

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Pseudonim gracza
    private var userNickname = ""

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
    override fun onClick(v: View?) {
        when(v){
            play_solo_button -> {
                val intent =
                        Intent(this, TetrisSoloGameActivity::class.java)
                startActivity(intent)
            }
            play_versus_button -> {
                val intent =
                        Intent(this, VersusGameActivity::class.java)
                startActivity(intent)
            }
            settings_button -> {
                val intent =
                        Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            ranking_button -> {
                val intent =
                        Intent(this, RankingActivity::class.java)
                startActivity(intent)
            }
            exit_game -> {
                finish()
                System.exit(0)
            }
        }
    }
}