package com.example.tetris.tetrimino

import kotlin.contracts.ReturnsNotNull

class TetriminoI: Tetrimino {

    // Tetrimino w kształcie litery I
    override var name: String = "I"

    // Przekazujemy tylko indeks kwadratu, bo nazwę już mamy
    constructor(mid: Int){
        this.mid = mid
    }

    constructor(tetriminoList: ArrayList<Tetrimino>, mid: Int){
        // Indeks środka
        this.mid = mid
        // Kostka jedna w lewo
        this.square1 = mid - 1
        // Kostka jedna w prawo
        this.square2 = mid + 1
        // Kostka druga w prawo
        this.square3 = mid + 2
        // Wstaw powyższe kostki na listę kostek
        tetriminoList[mid] = TetriminoI(mid)
        tetriminoList[mid-1] = TetriminoI(square1)
        tetriminoList[mid+1] = TetriminoI(square2)
        tetriminoList[mid+2] = TetriminoI(square3)
    }

    // Funkcja dokonująca rotacji Tetrimino
    override fun rotation(tetriminoList: ArrayList<Tetrimino>) : Boolean {

        // Sprawdź czy jakaś ściana nie blokuje obrotu
        if (!detectWall(tetriminoList)){
            return false
        }
        // Kostki w przypadku obrotu z 1 lub 3 rotacji
        this.square1 = mid - 1
        this.square2 = mid + 1
        this.square3 = mid + 2
        // Kostki w przypadku obrotu z 0 lub 2 rotacji
        this.square4 = mid - 10
        this.square5 = mid + 10
        this.square6 = mid + 20
        // Sprawdzenie czy położone kwadraty nie stanowią blokady obrotu
        when(this.rotation){
            0, 2-> {
                // Jeżeli obrócone pozycje są puste
                if (    tetriminoList[square4].name == "" &&
                        tetriminoList[square5].name == "" &&
                        tetriminoList[square6].name == "" )
                {
                    // Wyczyść poprzednie kwadraciki I
                    tetriminoList[square1] = Tetrimino()
                    tetriminoList[square2] = Tetrimino()
                    tetriminoList[square3] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square4] = TetriminoI(square4)
                    tetriminoList[square5] = TetriminoI(square5)
                    tetriminoList[square6] = TetriminoI(square6)
                    // Teraz I jest obrócone o 90 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić bo kwadracik powyżej blokuje
                    // Dzięki poniższym linijkom nie zbierzemy dodatkowych kwadracików ze sobą
                    this.square4 = 0
                    this.square5 = 0
                    this.square6 = 0
                    return false
                }
            }
            // Sprawdzenie czy kwadracik z prawej strony nie blokuje
            1, 3->{
                // Jeżeli jest pusty
                if (tetriminoList[square1].name == "" &&
                        tetriminoList[square2].name == "" &&
                        tetriminoList[square3].name == "" ) {

                    // Analogicznie jak poprzednio wyczyść odpowiednie kwadraciki
                    tetriminoList[square4] = Tetrimino()
                    tetriminoList[square5] = Tetrimino()
                    tetriminoList[square6] = Tetrimino()
                    // W nowe miejsce wstaw nowe kwadraciki
                    tetriminoList[square1] = TetriminoI(square1)
                    tetriminoList[square2] = TetriminoI(square2)
                    tetriminoList[square3] = TetriminoI(square3)
                    // Tutaj I jest w początkowym stanie
                    return true
                }
                else {
                    this.square1 = 0
                    this.square2 = 0
                    this.square3 = 0
                    return false
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
    }

    // Jeżeli można obrócić zwróci True, jeżeli znajdzie ścianę blokującą zwróci Fałsz
    override fun detectWall(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // Aktualny obrót kostki
        when(this.rotation){
            // Jeżeli brak obrotu
            0, 2 -> {
                // Sprawdź górną i dolną ścianę
                // Jeżeli indeks środka jest w drugim od góry wierszu
                // Lub w dolnym to nie można obrócić
                if (this.mid < 10 || this.mid > 180){
                    return false
                }
            }
            // W pionie
            1, 3 -> {
                // Sprawdzenie Prawej ściany
                for (x in 9..199 step 10){
                    // Czyli sprawdź czy indeks środka znajduje się
                    // w prawej (ostatniej) kolumnie lub w kolumnie obok
                    if (this.mid == x || this.mid == x-1){
                        return false
                    }
                }
                // Sprawdzenie lewej ściany
                for (x in 0..190 step 10){
                    // Czyli sprawdź czy indeks środka znajduje się
                    // w lwej (pierwszej) kolumnie
                    if (this.mid == x){
                        return false
                    }
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W przeciwnym wypadku można obrócić TetriminoI
        return true
    }

    // Sprawdź czy jest z prawej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation){
            0, 2 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Czy kostka nie dotyka ściany
                    if (this.square3 == x){
                        return false
                    }
                }
                // Sprawdź prawy kwadrat
                if (tetriminoList[this.square3 + 1].name == "")
                {
                    return true
                }
            }
            1, 3-> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Sprawdź czy środkowa kostka dotyka prawej ściany
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź inne kostki wszystkie 4 kostki
                if (tetriminoList[this.mid + 1].name == "" &&
                        tetriminoList[this.square4 + 1].name == "" &&
                        tetriminoList[this.square5 + 1].name == "" &&
                        tetriminoList[this.square6 + 1].name == "")
                {
                    return true
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W tym momencie wiemy, że o ile ściana nie blokuje Tetrimino to inne kwadraty już blokują
        return false
    }

    // Sprawdź czy jest z lewej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkLeft(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation){
            0, 2 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy lewa kostka dotyka lewej ściany
                    if (this.square1 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy lewa kostka nie dotyka innej kostki z lewej strony
                if (tetriminoList[this.square1 - 1].name == "")
                {
                    return true
                }
            }
            1, 3-> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy środkowa kostka dotyka lewej ściany
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź inne kostki wszystkie 4 czy nie dotykają innych kwadratów
                if (tetriminoList[this.mid - 1].name == "" &&
                        tetriminoList[this.square4 - 1].name == "" &&
                        tetriminoList[this.square5 - 1].name == "" &&
                        tetriminoList[this.square6 - 1].name == "")
                {
                    return true
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W tym momencie wiemy, że o ile ściana nie blokuje Tetrimino to inne kwadraty już blokują
        return false
    }

    // Sprawdź czy jest z dołu wolne miejsce na przesunięcie Tetrimino
    override fun checkDown(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation){
            0, 2 -> {
                // Sprawdź wszystkie 4 kwadraciki czy mogą zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.mid + 10].name == "" &&
                        tetriminoList[this.square1 + 10].name == "" &&
                        tetriminoList[this.square2 + 10].name == "" &&
                        tetriminoList[this.square3 + 10].name == "")
                {
                    return true
                }
            }
            1, 3 -> {
                // Sprawdź dolny kwadracik czy może zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.square6 + 10].name == "")
                {
                    return true
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W tym momencie wiemy, że jakieś kwadraciki blokują
        return false
    }

    // Przesuń w prawo
    override fun moveRight(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w prawo
        this.mid += 1
        // Tworzymy nowe 4 kwadraciki litery I przesunięte w prawo
        tetriminoList[this.mid] = TetriminoI(this.mid)
        if (this.square1 != 0) {
            this.square1 += 1
            tetriminoList[square1] = TetriminoI(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 1
            tetriminoList[square2] = TetriminoI(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 1
            tetriminoList[square3] = TetriminoI(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 1
            tetriminoList[square4] = TetriminoI(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 1
            tetriminoList[square5] = TetriminoI(square5)
        }
        if (this.square6 != 0) {
            this.square6 += 1
            tetriminoList[square6] = TetriminoI(square6)
        }
    }

    // Przesuń w lewo
    override fun moveLeft(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w lewo
        this.mid -= 1
        // Tworzymy nowe 4 kwadraciki litery I przesunięte w lewo
        tetriminoList[this.mid] = TetriminoI(this.mid)
        if (this.square1 != 0) {
            this.square1 -= 1
            tetriminoList[square1] = TetriminoI(square1)
        }
        if (this.square2 != 0) {
            this.square2 -= 1
            tetriminoList[square2] = TetriminoI(square2)
        }
        if (this.square3 != 0) {
            this.square3 -= 1
            tetriminoList[square3] = TetriminoI(square3)
        }
        if (this.square4 != 0) {
            this.square4 -= 1
            tetriminoList[square4] = TetriminoI(square4)
        }
        if (this.square5 != 0) {
            this.square5 -= 1
            tetriminoList[square5] = TetriminoI(square5)
        }
        if (this.square6 != 0) {
            this.square6 -= 1
            tetriminoList[square6] = TetriminoI(square6)
        }
    }

    // Przesuń w dół
    override fun moveDown(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w dół
        this.mid += 10
        // Tworzymy nowe 4 kwadraciki litery I przesunięte w dół
        tetriminoList[this.mid] = TetriminoI(this.mid)
        if (this.square1 != 0) {
            this.square1 += 10
            tetriminoList[square1] = TetriminoI(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 10
            tetriminoList[square2] = TetriminoI(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 10
            tetriminoList[square3] = TetriminoI(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 10
            tetriminoList[square4] = TetriminoI(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 10
            tetriminoList[square5] = TetriminoI(square5)
        }
        if (this.square6 != 0) {
            this.square6 += 10
            tetriminoList[square6] = TetriminoI(square6)
        }
    }
}