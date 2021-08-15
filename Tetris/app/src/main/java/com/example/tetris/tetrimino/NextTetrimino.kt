package com.example.tetris.tetrimino

import java.util.ArrayList

class NextTetrimino : Tetrimino {

    // Podanie 1 kwadracika z całego Tetrimino o nazwie name
    constructor(mid: Int, name: String){
        this.mid = mid
        this.name = name
    }

    // Podanie całego Tetrimino
    constructor(nextTetrimino: ArrayList<Tetrimino>, name: Int){
        var nameString = ""
        // W zależności od nazwy następnego Tetrimino wyznacz 4 indeksy
        // i przypisz nazwę
        // Obraz 5x5 (lub 4 kolumny dla I oraz O)
        // Indeksy kwadratów (dla 5x5):
        // od 0 do 4 - pierwszy wiersz
        // od 5 do 9 - drugi wiersz
        // od 10 do 14 - trzeci wiersz
        // od 15 do 19 - czwarty wiersz
        // od 20 do 24 - piąty wiersz
        // Dla 4 kolumn:
        // Od 0 do 3 - pierwszy wiersz (pusty)
        // od 4 do 7 - drugi wiersz (w przypadku I cały wypełniony)
        // od 8 do 11 - trzeci wiersz
        when (name) {
            1 -> {
                this.squere1 = 4
                this.squere2 = 5
                this.squere3 = 6
                this.squere4 = 7
                nameString = "I"
            }
            2 -> {
                this.squere1 = 6
                this.squere2 = 7
                this.squere3 = 8
                this.squere4 = 13
                nameString = "J"
            }
            3 -> {
                this.squere1 = 6
                this.squere2 = 7
                this.squere3 = 8
                this.squere4 = 11
                nameString = "L"
            }
            4 -> {
                this.squere1 = 5
                this.squere2 = 6
                this.squere3 = 9
                this.squere4 = 10
                nameString = "O"
            }
            5 -> {
                this.squere1 = 7
                this.squere2 = 8
                this.squere3 = 11
                this.squere4 = 12
                nameString = "S"
            }
            6 -> {
                this.squere1 = 6
                this.squere2 = 7
                this.squere3 = 8
                this.squere4 = 12
                nameString = "T"
            }
            7 -> {
                this.squere1 = 6
                this.squere2 = 7
                this.squere3 = 12
                this.squere4 = 13
                nameString = "Z"
            }
        }
        // Do wybranych indeksów poszczególnych kwadratów wpisz Tetrimino
        // Indeks oraz nazwę (położenie kwadratu oraz kolor)
        nextTetrimino[squere1] = NextTetrimino(squere1, nameString)
        nextTetrimino[squere2] = NextTetrimino(squere2, nameString)
        nextTetrimino[squere3] = NextTetrimino(squere3, nameString)
        nextTetrimino[squere4] = NextTetrimino(squere4, nameString)
    }
}