package com.example.tetris.tetrimino

class TetriminoZ : Tetrimino {

    // C&P TetriminoS z drobnymi zmianami

    // Tetrimino w kształcie litery Z
    override var name: String = "Z"

    // Przekazujemy tylko indeks kwadratu, bo nazwę już mamy
    constructor(mid: Int){
        this.mid = mid
    }

    constructor(tetriminoList: ArrayList<Tetrimino>, mid: Int){
        // Indeks środka
        this.mid = mid
        // Kostka w lewo
        this.square1 = mid - 1
        // Kostka 1 niżej
        this.square2 = mid + 10
        // Kostka jeden w prawo i w dół
        this.square3 = mid + 11
        // Wstaw powyższe kostki na listę kostek
        tetriminoList[mid] = TetriminoZ(mid)
        tetriminoList[mid - 1] = TetriminoZ(square1)
        tetriminoList[mid + 10] = TetriminoZ(square2)
        tetriminoList[mid + 11] = TetriminoZ(square3)
    }


    // Funkcja dokonująca rotacji Tetrimino
    override fun rotation(tetriminoList: ArrayList<Tetrimino>) : Boolean {

        // Sprawdź czy jakaś ściana nie blokuje obrotu
        if (!detectWall(tetriminoList)){
            return false
        }
        this.square1 = mid - 1
        this.square2 = mid + 10
        this.square3 = mid + 11
        this.square4 = mid + 1
        this.square5 = mid - 9
        // W zależności od rotacji
        when(this.rotation) {
            0, 2->{
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square4].name == "" &&
                        tetriminoList[square5].name == "" ) {
                    // Wyczyść poprzednie kwadraciki Z
                    tetriminoList[square1] = Tetrimino()
                    tetriminoList[square3] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square4] = TetriminoZ(square4)
                    tetriminoList[square5] = TetriminoZ(square5)
                    // Teraz Z jest obrócone o 90 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.square4 = 0
                    this.square5 = 0
                    return false
                }
            }
            1, 3 -> {
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square1].name == "" &&
                        tetriminoList[square3].name == "" ) {
                    // Wyczyść poprzednie kwadraciki S
                    tetriminoList[square4] = Tetrimino()
                    tetriminoList[square5] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square1] = TetriminoZ(square1)
                    tetriminoList[square3] = TetriminoZ(square3)
                    // Teraz Z jest w swojej początkowej pozycji
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.square1 = 0
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
        // W zależności od rotacji
        when(this.rotation) {
            0, 2->{
                // Sprawdź czy górna ściana nie blokuje
                if (this.mid < 10){
                    return false
                }
            }
            1, 3 -> {
                // Sprawdź lewą ścianę czy nie blokuje
                // ( czy środek tetrimino nie jest w ostatniej kolumnie)
                for (x in 0..190 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W przeciwnym wypadku można obrócić Tetrimino Z
        return true
    }

    // Sprawdź czy jest z prawej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation) {
            0, 2->{
                // Sprawdź prawą ścianę, czy blokuje ruch w prawo
                for (x in 9..199 step 10){

                    if (this.square3 == x){
                        return false
                    }
                }
                // Sprawdź klocki po prawej stronie
                if (tetriminoList[this.mid + 1].name == "" &&
                        tetriminoList[this.square3 + 1].name == "")
                {
                    return true
                }
            }
            1, 3 -> {
                // Sprawdź prawą ścianę czy nie blokuje ruchu w prawo
                for (x in 9..199 step 10){

                    if (this.square4 == x){
                        return false
                    }
                }
                // Sprawdź 3 klocki z prawej strony czy jakiś z nich nie blokuje przesunięcia
                if (tetriminoList[this.square4 + 1].name == "" &&
                        tetriminoList[this.square2 + 1].name == "" &&
                        tetriminoList[this.square5 + 1].name == "")
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
        when(this.rotation) {
            0, 2->{
                // Sprawdź lewą ścianę analogicznie do poprzednich tetrimino
                for (x in 0..190 step 10){
                    if (this.square1 == x){
                        return false
                    }
                }
                // Sprawdź 2 kwadraciki z lewej strony
                if (tetriminoList[this.square1 - 1].name == "" &&
                        tetriminoList[this.square2 - 1].name == "")
                {
                    return true
                }
            }
            1, 3 -> {
                // Sprawdź lewą ścianę
                for (x in 0..190 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź 3 kwadraciki z lewej strony
                if (tetriminoList[this.square2 - 1].name == "" &&
                        tetriminoList[this.mid - 1].name == "" &&
                        tetriminoList[this.square5 - 1].name == "")
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
        when(this.rotation) {
            0, 2->{
                // Sprawdź 3 kwadraty od dołu
                if (tetriminoList[this.square1 + 10].name == "" &&
                        tetriminoList[this.square2 + 10].name == "" &&
                        tetriminoList[this.square3 + 10].name == "")
                {
                    return true
                }
            }
            1, 3 -> {
                // Sprawdź 2 kwadraty od dołu
                if (tetriminoList[this.square4 + 10].name == "" &&
                        tetriminoList[this.square2 + 10].name == "")
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

    // Przesuń w prawo
    override fun moveRight(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w prawo
        this.mid += 1
        // Tworzymy nowe 4 kwadraciki litery Z przesunięte w prawo
        tetriminoList[this.mid] = TetriminoZ(this.mid)
        if (this.square1 != 0) {
            this.square1 += 1
            tetriminoList[square1] = TetriminoZ(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 1
            tetriminoList[square2] = TetriminoZ(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 1
            tetriminoList[square3] = TetriminoZ(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 1
            tetriminoList[square4] = TetriminoZ(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 1
            tetriminoList[square5] = TetriminoZ(square5)
        }
    }

    // Przesuń w lewo
    override fun moveLeft(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w lewo
        this.mid -= 1
        // Tworzymy nowe 4 kwadraciki litery Z przesunięte w lewo
        tetriminoList[this.mid] = TetriminoZ(this.mid)
        if (this.square1 != 0) {
            this.square1 -= 1
            tetriminoList[square1] = TetriminoZ(square1)
        }
        if (this.square2 != 0) {
            this.square2 -= 1
            tetriminoList[square2] = TetriminoZ(square2)
        }
        if (this.square3 != 0) {
            this.square3 -= 1
            tetriminoList[square3] = TetriminoZ(square3)
        }
        if (this.square4 != 0) {
            this.square4 -= 1
            tetriminoList[square4] = TetriminoZ(square4)
        }
        if (this.square5 != 0) {
            this.square5 -= 1
            tetriminoList[square5] = TetriminoZ(square5)
        }
    }

    // Przesuń w dół
    override fun moveDown(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w dół
        this.mid += 10
        // Tworzymy nowe 4 kwadraciki litery Z przesunięte w dół
        tetriminoList[this.mid] = TetriminoZ(this.mid)
        if (this.square1 != 0) {
            this.square1 += 10
            tetriminoList[square1] = TetriminoZ(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 10
            tetriminoList[square2] = TetriminoZ(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 10
            tetriminoList[square3] = TetriminoZ(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 10
            tetriminoList[square4] = TetriminoZ(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 10
            tetriminoList[square5] = TetriminoZ(square5)
        }
    }
}