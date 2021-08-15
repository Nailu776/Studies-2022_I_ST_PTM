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
    open var squere1 = 0
    open var squere2 = 0
    open var squere3 = 0
    open var squere4 = 0
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
        if (tetriminoList[squere1].name != "") {
            tetriminoList[squere1] = Tetrimino()
        }else {
            squere1 = 0
        }
        if (tetriminoList[squere2].name != "") {
            tetriminoList[squere2] = Tetrimino()
        } else {
            squere2 = 0
        }
        if (tetriminoList[squere3].name != "") {
            tetriminoList[squere3] = Tetrimino()
        }else {
            squere3 = 0
        }
        if (tetriminoList[squere4].name != "") {
            tetriminoList[squere4] = Tetrimino()
        }else {
            squere4 = 0
        }
    }

    // Sprawdź czy Tetrimino osiągnęło dół planszy
    // (Czy jego indeksy nie wyjdą poza Listę jeżeli się obniży)
    fun detectBottom(tetriminoList: ArrayList<Tetrimino>) : Boolean{
        // True == można zejść niżej
        return try {
            tetriminoList[this.mid + 10]
            tetriminoList[this.squere1 + 10]
            tetriminoList[this.squere2 + 10]
            tetriminoList[this.squere3 + 10]
            tetriminoList[this.squere4 + 10]
            true
        } catch (exception: IndexOutOfBoundsException){
            // False == próba uzyskania dostępu do elementu poza tablicą
            false
        }
    }

}