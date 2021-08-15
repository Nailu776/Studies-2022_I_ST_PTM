package com.example.tetris

import android.graphics.drawable.Drawable

class Constants() {
    companion object{
        // Stałe do timera
        val START_TIME_IN_MS: Long = 20000
        val COUNT_DOWN_INITIAL_INTERVAL = 1000L
        // Schemat kwadracika
        val TEMP_SQUARE = R.layout.temp_sq
        // Tablica stałych kolorów Tetrimino
        var TABLE_OF_SQUERES : IntArray = intArrayOf(
                R.drawable.square_red,          // "I"
                R.drawable.square_purple,       // "J"
                R.drawable.square_yellow,       // "L"
                R.drawable.square_light_blue,   // "O"
                R.drawable.square_blue,         // "S"
                R.drawable.square_gray,         // "T"
                R.drawable.square_green         // "Z"
        )
    }
}