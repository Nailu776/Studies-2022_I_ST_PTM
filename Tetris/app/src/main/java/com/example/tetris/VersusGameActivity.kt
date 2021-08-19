package com.example.tetris

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class VersusGameActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private val REQEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus_game)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter==null){
            Toast.makeText(this.applicationContext, "This device doesn't support bluetooth.", Toast.LENGTH_SHORT).show()
        }
        if(!bluetoothAdapter!!.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQEST_ENABLE_BLUETOOTH)
        }

        findViewById<Button>(R.id.select_device_refresh).setOnClickListener{pairedDeviceList()}
    }
    private fun pairedDeviceList(){
        pairedDevices = bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if(!pairedDevices.isEmpty()){
            for(device: BluetoothDevice in pairedDevices){
                list.add(device)
                Log.i("device", ""+device)
            }
        } else{
            Toast.makeText(this.applicationContext, "No paired devices found.", Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        findViewById<ListView>(R.id.select_device_list).adapter = adapter
        findViewById<ListView>(R.id.select_device_list).onItemClickListener = AdapterView.OnItemClickListener {_,_, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address

            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS, address)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                if(bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this.applicationContext, "Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this.applicationContext, "Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this.applicationContext, "Bluetooth enabling has been canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}