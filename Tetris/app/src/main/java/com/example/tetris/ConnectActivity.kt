package com.example.tetris

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.Executors

class ConnectActivity : AppCompatActivity() {

    var hostIp = "192.168.1.28"
    val port = 50000
    lateinit var serverSocket: ServerSocket

    companion object{
         var socket: Socket? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        findViewById<Button>(R.id.connect).setOnClickListener{connect()}
        findViewById<Button>(R.id.SetIp).setOnClickListener{setIp()}
        findViewById<Button>(R.id.listen).setOnClickListener{listen()}

    }

    private fun connect(){
        Executors.newSingleThreadExecutor().execute {
            try {
                val socket = Socket(hostIp, port)
            }
            catch (e: ConnectException){
                Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
    private fun setIp(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Podaj adres IP")

        val input = EditText(this)

        input.setHint("IP")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            hostIp = input.text.toString()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
    private fun listen() {
        findViewById<Button>(R.id.listen).isEnabled = false
        Executors.newSingleThreadExecutor().execute {
            ServerSocket(port).let{ sckt ->
                serverSocket = sckt
                try{
                    socket = serverSocket.accept()
                }
                catch (e: SocketException){
                    e.printStackTrace()
                    Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                    findViewById<Button>(R.id.listen).isEnabled = true
                }
            }
        }
    }
}