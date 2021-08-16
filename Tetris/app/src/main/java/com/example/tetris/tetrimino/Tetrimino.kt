package com.example.tetris.tetrimino

import com.example.tetris.R

open class Tetrimino {

    // Środek kwadracika (indeks w liście)
    var mid: Int = 0
    // Pusta nazwa
    open var name: String = ""
    // Obrót == 0 == nieobrócone Tetrimino
    open var rotation = 0
    // Indeksy poszczególnych kwadratów Tetrimino
    open var square1 = 0
    open var square2 = 0
    open var square3 = 0
    open var square4 = 0
    open var square5 = 0
    open var square6 = 0
    open var square7 = 0
    open var square8 = 0
    // Kolor
    open var color = R.drawable.square_blue

    // Rotacja
    open fun rotation(tetriminoList: ArrayList<Tetrimino>) : Boolean{
        return false
    }

    // Ruch w dół
    open fun moveDown(tetriminoList: ArrayList<Tetrimino>){}

    // Ruch w prawo
    open fun moveRight(tetriminoList: ArrayList<Tetrimino>){}

    // Ruch w lewo
    open fun moveLeft(tetriminoList: ArrayList<Tetrimino>){}

    // Sprawdź czy można ruszyć w prawo
    open fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean{
        return false
    }

    // Sprawdź czy można ruszyć w lewo
    open fun checkLeft(tetriminoList: ArrayList<Tetrimino>) : Boolean{
        return false
    }

    // Sprawdź czy można ruszyć w dół
    open fun checkDown(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        return false
    }

    // Sprawdź ściany by móc obrócić
    open fun detectWall(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        return false
    }

    // Skasuj Tetrimino
    fun removeTetrimino(tetriminoList: ArrayList<Tetrimino>){

        tetriminoList[mid] = Tetrimino()

        // Przechodzimy po każdym kwadraciku
        // i jeżeli był niepusty (część Tetrimino) to go czyścimy
        // a jak był pusty to indeks kwadracika zerujemy
        if (tetriminoList[square1].name != "") {
            tetriminoList[square1] = Tetrimino()
        }else {
            square1 = 0
        }
        if (tetriminoList[square2].name != "") {
            tetriminoList[square2] = Tetrimino()
        } else {
            square2 = 0
        }
        if (tetriminoList[square3].name != "") {
            tetriminoList[square3] = Tetrimino()
        }else {
            square3 = 0
        }
        if (tetriminoList[square4].name != "") {
            tetriminoList[square4] = Tetrimino()
        }else {
            square4 = 0
        }
        if (tetriminoList[square5].name != "") {
            tetriminoList[square5] = Tetrimino()
        }else {
            square5 = 0
        }
        if (tetriminoList[square6].name != "") {
            tetriminoList[square6] = Tetrimino()
        } else {
            square6 = 0
        }
        if (tetriminoList[square7].name != "") {
            tetriminoList[square7] = Tetrimino()
        }else {
            square7 = 0
        }
        if (tetriminoList[square8].name != "") {
            tetriminoList[square8] = Tetrimino()
        }else {
            square8 = 0
        }
    }

    // Sprawdź czy Tetrimino osiągnęło dół planszy
    // (Czy jego indeksy nie wyjdą poza Listę jeżeli się obniży)
    fun detectBottom(tetriminoList: ArrayList<Tetrimino>) : Boolean{
        // True == można zejść niżej
        return try {
            tetriminoList[this.mid + 10]
            tetriminoList[this.square1 + 10]
            tetriminoList[this.square2 + 10]
            tetriminoList[this.square3 + 10]
            tetriminoList[this.square4 + 10]
            tetriminoList[this.square5 + 10]
            tetriminoList[this.square6 + 10]
            tetriminoList[this.square7 + 10]
            tetriminoList[this.square8 + 10]
            true
        } catch (exception: IndexOutOfBoundsException){
            // False == próba uzyskania dostępu do elementu poza tablicą
            false
        }
    }

}