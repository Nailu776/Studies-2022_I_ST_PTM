package com.example.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Pseudonim gracza
    private var userNickname = " "

    // Te przyciski będą zainicjalizowane onCreate
    private lateinit var play_solo_button : Button
    private lateinit var play_versus_button : Button
    private lateinit var settings_button : Button
    private lateinit var ranking_button : Button
    private lateinit var exit_game : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Fullscreen?
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

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


    }

    override fun onClick(v: View?) {
        when(v){
            play_solo_button ->{
                val intent: Intent =
                    Intent(this,TetrisGameActivity::class.java)
                startActivity(intent)
            }
            play_versus_button ->{
                val intent: Intent =
                    Intent(this,VersusGameActivity::class.java)
                startActivity(intent)
            }
            settings_button ->{
                val intent: Intent =
                    Intent(this,SettingsActivity::class.java)
                startActivity(intent)
            }
            ranking_button ->{
                val intent: Intent =
                    Intent(this,RankingActivity::class.java)
                startActivity(intent)
            }
            exit_game ->{
                finish()
                System.exit(0)
            }
        }
    }
}