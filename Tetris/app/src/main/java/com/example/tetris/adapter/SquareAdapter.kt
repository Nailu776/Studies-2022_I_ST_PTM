package com.example.tetris.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.tetris.Constants
import com.example.tetris.R
import com.example.tetris.tetrimino.Tetrimino
import java.util.*


class SquareAdapter(private val context: Context, tetriminoList: ArrayList<Tetrimino>, tNum: Int) : BaseAdapter() {

    // Adapter kwadratów w gridView

    private var inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val tetriminoes: ArrayList<Tetrimino> = tetriminoList
    private val tetrimino = tNum
    private var mid: Int = 0
    private lateinit var name: String
    private lateinit var background: ImageView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // elvis operator - jeżeli convertView == null to view = inflater.inflate(tetrimino, null)
        // else view = convertView
        val view : View = convertView ?: inflater.inflate(tetrimino, null)

        // Parametry tetrimino w określonej pozycji w gridView (środek oraz nazwa/kolor)
        mid = tetriminoes[position].mid
        name = tetriminoes[position].name
        // Ustawienie tła widoku kostki
        background = view.findViewById(R.id.temp_sq)
        // Wyłaczenie widzialności kostki w siatce
        view.visibility = View.GONE

        // Jeżeli dany kwadracik nie jest pusty (czyli jest częścią jakiegoś tetrimino)
        if(name != ""){
            if (position == mid) {
                // Włączenie widzialności kostki w siatce
                view.visibility = View.VISIBLE
                // W zależności od rodzaju Tetrimino ustalony jest kolor wzorowany na Wikipedii
                when (name) {
                    "I" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[0])
                    }
                    "J" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[1])
                    }
                    "L" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[2])
                    }
                    "O" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[3])
                    }
                    "S" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[4])
                    }
                    "T" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[5])
                    }
                    "Z" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[6])
                    }
                    "X" -> {
                        background.setBackgroundResource(Constants.TABLE_OF_SQUARES[7])
                    }
                }
            }
        }
        // Zwrócenie kostki
        return view
    }

    // Zwrócenie ilości kostek na liście
    override fun getCount(): Int {
        return tetriminoes.size
    }

    // Zwrócenie id kostki (pozycji)
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Zwrócenie kostki z danej pozycji
    override fun getItem(position: Int): Any? {
        return tetriminoes[position]
    }
}