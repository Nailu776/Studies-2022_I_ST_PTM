package com.example.tetris

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tetris.adapter.SquareAdapter
import com.example.tetris.tetrimino.*
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt


class ControlActivity: AppCompatActivity(){

    companion object {
        var myUUID: UUID = UUID.fromString("b7da2b1f-9311-402b-99c0-c7cb668ea199 ")
        var bluetoothSocket: BluetoothSocket?= null
        lateinit var progress: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false
        lateinit var address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        address = intent.getStringExtra(VersusGameActivity.EXTRA_ADDRESS).toString()
        ConnectToDevice(this).execute()

        findViewById<Button>(R.id.send).setOnClickListener{sendCommand("Sending a message.")}
    }

    private fun receive(){
        var bytes: Int
        var byteArray: ByteArray = ByteArray(1024)
        var message: String = ""
        try{
            //Dot is the end of the message.
            while(!(message.contains('.'))){
                bytes = bluetoothSocket!!.inputStream.read(byteArray)
                message = message + String(byteArray, 0, bytes)
            }
        } catch(e: Exception){
            e.printStackTrace()
        }
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

        override fun onPreExecute() {
            super.onPreExecute()
            progress = ProgressDialog.show(context,"Connecting...", "Please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if(bluetoothSocket == null || !isConnected){
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    bluetoothSocket!!.connect()
                }
            }catch (e: IOException){
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!connectSuccess){
                Log.i("data", "Couldn't connect")
            } else{
                isConnected = true
            }
            progress.dismiss()
        }
    }

}