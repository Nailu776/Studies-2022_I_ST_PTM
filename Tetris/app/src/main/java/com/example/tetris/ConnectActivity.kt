package com.example.tetris

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import java.io.PrintWriter
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors

class ConnectActivity : AppCompatActivity() {

    private lateinit var listenButton: Button
    private lateinit var connectButton: Button
    private lateinit var IPButton: Button
    private lateinit var sendMove: Button
    private lateinit var proceed: Button

    private var printWriter: PrintWriter? = null
    private var serverSocket: ServerSocket? = null
    private var socketHost = "192.168.1.29" //192.168.2.44 127.0.0.1
    private val socketPort: Int = 50000 //50000 dla fizycznych urzadzen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)



        listenButton = findViewById<Button>(R.id.listen)
        connectButton = findViewById<Button>(R.id.connect)
        IPButton = findViewById<Button>(R.id.SetIp)
        proceed = findViewById<Button>(R.id.proceed)
        sendMove = findViewById<Button>(R.id.button2)
        fun showdialog(){
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Podaj adres IP")

            val input = EditText(this)

            input.setHint("IP")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                socketHost = input.text.toString()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

        IPButton.setOnClickListener {
            showdialog()
        }

        listenButton.setOnClickListener {
            listenButton.isEnabled = false
            Toast.makeText(this, "listening on $socketPort", Toast.LENGTH_SHORT).show()
            Executors.newSingleThreadExecutor().execute {
                ServerSocket(socketPort).let{ srvSkt ->
                    serverSocket = srvSkt
                    try {
                        SocketHandler.socket = srvSkt.accept()
                        receiveMove()
                    }
                    catch(e: SocketException) {
                    }
                }
            }
        }

        connectButton.setOnClickListener {
            Executors.newSingleThreadExecutor().execute {
                try {
                    SocketHandler.socket = Socket(socketHost, socketPort)
                    receiveMove()
                }
                catch (e: ConnectException){
                    runOnUiThread {
                        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        sendMove.setOnClickListener{
            sendMove()
        }
        proceed.setOnClickListener{startActivity(Intent(this, VersusActivity::class.java))
        }


    }

    private fun receiveMove(){
        val scanner = Scanner(SocketHandler.socket!!.getInputStream())
        printWriter = PrintWriter(SocketHandler.socket!!.getOutputStream(), true)
        while (scanner.hasNextLine()) {
            val move = scanner.nextLine()
            Log.i("move", move)
            SocketHandler.read = move
        }
    }

    private fun sendMove(){
        printWriter?.let{
            Executors.newSingleThreadExecutor().execute {
                it.println(2137)
            }
        }
    }

}
object SocketHandler {
    @get:Synchronized
    @set:Synchronized
    var socket: Socket? = null
    var read: String? = null
}



