package com.example.tetris

import android.content.Intent
import android.net.IpPrefix
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.find
import org.w3c.dom.Text
import java.io.PrintWriter
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors


class VersusActivity: AppCompatActivity(){

    lateinit var printWriter: PrintWriter
    var port = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus)

        printWriter = PrintWriter(SocketHandler.socket!!.getOutputStream(),true)

        findViewById<Button>(R.id.send).setOnClickListener{send()}
        findViewById<Button>(R.id.refresh).setOnClickListener{receive()}

    }

    fun receive(){
            val scanner = Scanner(SocketHandler.socket!!.getInputStream())
            while(scanner.hasNextLine()){
                val read = scanner.nextLine()
                findViewById<TextView>(R.id.textView).text = read
            }

        findViewById<TextView>(R.id.textView).invalidate()

    }

    fun send(){
        printWriter = PrintWriter(SocketHandler.socket!!.getOutputStream(), true)
        printWriter.let{
            Executors.newSingleThreadExecutor().execute {
                it.println(2137)
            }
        }
    }


}
