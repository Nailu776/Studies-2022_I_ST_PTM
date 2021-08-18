package com.example.tetris

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    // Ustawienia
    // Pseudonim gracza
    private var userNickname = ""
    // Poziom startowy (działa tylko na soloq)
    private var startLevel = 0
    private lateinit var applyChanges : Button
    private lateinit var nickName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        userNickname = intent.getStringExtra("userNickname").toString()
        startLevel = intent.getIntExtra("startLevel",0)
        applyChanges = findViewById(R.id.apply_Changes_Btn)
        applyChanges.setOnClickListener(this)
        nickName = findViewById(R.id.editTextTextPersonName)
    }
    override fun onClick(v: View?) {
        when(v){
            applyChanges -> {
                userNickname = nickName.text.toString()
                setResult(Activity.RESULT_OK,
                        Intent()
                                .putExtra("startLevel", startLevel)
                                .putExtra("userNickname", userNickname))
                finish()
            }
        }
    }

}