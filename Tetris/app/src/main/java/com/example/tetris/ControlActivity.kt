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
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
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
        ConnectToDevice(this).start()

        findViewById<Button>(R.id.send).setOnClickListener{sendCommand("Sending a message.")}
        findViewById<Button>(R.id.refresh).setOnClickListener{receive()}
    }

    private fun receive(){
        var bytes: Int
        val byteArray: ByteArray = ByteArray(1024)
        var message: String = ""
        try{
            //Dot is the end of the message.
            while(!(message.contains('.'))){
                bytes = bluetoothSocket!!.inputStream.read(byteArray)
                message = message + String(byteArray, 0, bytes)
                Log.i("message",message);
            }
            if(message.equals("")){
                message = "Message was not sent."
            }
        } catch(e: Exception){
            e.printStackTrace()
        }
        findViewById<TextView>(R.id.textView).text = message;
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
    private class ConnectToDevice(c: Context): Thread() {
        private var connectSuccess: Boolean = true
        private val context: Context = c

        init{
            progress = ProgressDialog.show(context,"Connecting...", "Please wait")
        }

        override fun run(){
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
            onPostExecute()
        }

         fun onPostExecute() {
            if(!connectSuccess){
                Log.i("data", "Couldn't connect")
                Log.i("data", address)
            } else{
                isConnected = true
            }
            progress.dismiss()
        }
    }


}