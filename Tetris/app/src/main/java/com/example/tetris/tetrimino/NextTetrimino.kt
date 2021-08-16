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
                this.square1 = 4
                this.square2 = 5
                this.square3 = 6
                this.square4 = 7
                nameString = "I"
            }
            2 -> {
                this.square1 = 6
                this.square2 = 7
                this.square3 = 8
                this.square4 = 13
                nameString = "J"
            }
            3 -> {
                this.square1 = 6
                this.square2 = 7
                this.square3 = 8
                this.square4 = 11
                nameString = "L"
            }
            4 -> {
                this.square1 = 5
                this.square2 = 6
                this.square3 = 9
                this.square4 = 10
                nameString = "O"
            }
            5 -> {
                this.square1 = 7
                this.square2 = 8
                this.square3 = 11
                this.square4 = 12
                nameString = "S"
            }
            6 -> {
                this.square1 = 6
                this.square2 = 7
                this.square3 = 8
                this.square4 = 12
                nameString = "T"
            }
            7 -> {
                this.square1 = 6
                this.square2 = 7
                this.square3 = 12
                this.square4 = 13
                nameString = "Z"
            }
        }
        // Do wybranych indeksów poszczególnych kwadratów wpisz Tetrimino
        // Indeks oraz nazwę (położenie kwadratu oraz kolor)
        nextTetrimino[square1] = NextTetrimino(square1, nameString)
        nextTetrimino[square2] = NextTetrimino(square2, nameString)
        nextTetrimino[square3] = NextTetrimino(square3, nameString)
        nextTetrimino[square4] = NextTetrimino(square4, nameString)
    }
}