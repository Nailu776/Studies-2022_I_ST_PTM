package com.example.tetris

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
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
        findViewById<Button>(R.id.button).setOnClickListener{send()}

    }

    fun receive(){
        if(socket != null){
            val scanner = Scanner(socket!!.getInputStream())
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
        socket!!.getOutputStream().write("Sending a message".toByteArray())
        receive()
    }

    private fun connect(){
     Executors.newSingleThreadExecutor().execute(){
         try{
             socket = Socket(hostIp, port)
             startActivity(Intent(this,VersusActivity::class.java))
         }catch (e: ConnectException){
             Toast.makeText(this,"Failed to connect", Toast.LENGTH_SHORT).show()
             findViewById<Button>(R.id.listen).isEnabled = true

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
        Executors.newSingleThreadExecutor().execute() {
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