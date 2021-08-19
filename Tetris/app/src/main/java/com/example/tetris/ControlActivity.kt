package com.example.tetris

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture

class ControlActivity: AppCompatActivity(){

    companion object {
        var myUUID: UUID = UUID.fromString("")
        var bluetoothSocket: BluetoothSocket?= null
        lateinit var progres: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false
        lateinit var address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        address = intent.getStringExtra(VersusGameActivity.EXTRA_ADDRESS).toString()
        ConnectToDevice(this).execute()

        findViewById<Button>(R.id.control_led_on).setOnClickListener{sendCommand("a")}
        findViewById<Button>(R.id.control_led_off).setOnClickListener{sendCommand("b")}
        findViewById<Button>(R.id.disconnect).setOnClickListener{ disconnect() }

    }

    private fun sendCommand(input: String){
        try {
            bluetoothSocket!!.outputStream.write(input.toByteArray())
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
    private fun disconnect(){
        if (bluetoothSocket != null){
            try {
                bluetoothSocket!!.close()
                bluetoothSocket = null
                isConnected = false
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }
    private class ConnectToDevice(c: Context): AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context
        init{
            this.context = c
        }

        override fun doInBackground(vararg params: Void?): String {
            TODO("Not yet implemented")
        }

    }
}