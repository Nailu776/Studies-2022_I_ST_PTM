package com.example.tetris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.find
import org.w3c.dom.Text
import java.io.PrintWriter
import java.util.*


class VersusActivity: AppCompatActivity(){

    var printWriter: PrintWriter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus)

        findViewById<Button>(R.id.send).setOnClickListener{send()}
        findViewById<Button>(R.id.refresh).setOnClickListener{receive()}

    }

    fun receive(){
        if(ConnectActivity.socket != null){
            val scanner = Scanner(ConnectActivity.socket!!.getInputStream())
            while(scanner.hasNextLine()){
                val read = scanner.nextLine()
                findViewById<TextView>(R.id.textView).text = read
            }
        }
        else{
            Toast.makeText(this, "Socket is null", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.textView).invalidate()

    }

    fun send(){
        ConnectActivity.socket!!.getOutputStream().write("Sending a message".toByteArray())
    }


}