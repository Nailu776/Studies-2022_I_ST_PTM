package com.example.tetris.tetrimino

class TetriminoO  : Tetrimino {

    // Tetrimino w kształcie litery O
    override var name = "O"

    // Przekazujemy tylko indeks kwadratu, bo nazwę już mamy
    constructor(mid: Int){
        this.mid = mid
    }

    constructor(tetriminoList: ArrayList<Tetrimino>, mid: Int){
        // Indeks środka
        this.mid = mid
        // Kostka jedna w prawo
        this.square1 = mid + 1
        // Jedna kostka w dół
        this.square2 = mid + 10
        // Kostka o 1 w dół i prawo
        this.square3 = mid + 11
        // Wstaw powyższe kostki na listę kostek
        tetriminoList[mid] = TetriminoO(mid)
        tetriminoList[mid+1] = TetriminoO(mid + 1)
        tetriminoList[mid+10] = TetriminoO(mid + 10)
        tetriminoList[mid+11] = TetriminoO(mid + 11)
    }

    // Uproszczenie!
    // Funkcja dokonująca rotacji Tetrimino
    // Oraz
    // Funkcja sprawdzająca ściany czy można dokonać rotacji
    // nie będą potrzebne (detectWall nie będzie potrzebna bo rotation nie jest potrzebna)
    // Kwadrat po rotacji będzie takim samym kwadratem
    // -> Obracając "O" nadal będzie O :)

    // Sprawdź czy jest z prawej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // Nie ma znaczenia rotacja (sprawdź uproszczenie powyżej)
        // Sprawdź prawą ścianę (czy prawa kostka tetrimino O nie jest czasem w prawej kolumnie)
        for (x in 9..199 step 10){
            if (this.square1 == x){
                return false
            }
        }
        // Sprawdź dwie kostki z prawej strony
        if (tetriminoList[this.square1 + 1].name == "" &&
                tetriminoList[this.square3 + 1].name == "")
        {
            // Jeżeli są puste to przesunąć można
            return true
        }
        // W przeciwnym razie nie można bo coś w nich jest
        return false
    }

    // Sprawdź czy jest z lewej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkLeft(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // Sprawdź ścianę lewą (pierwszą kolumnę)
        for (x in 0..190 step 10){
            if (this.mid == x){
                return false
            }
        }
        // Sprawdź dwie kostki z lewej strony analogicznie jak checkRight
        if (tetriminoList[this.mid - 1].name == "" &&
                tetriminoList[this.square2 - 1].name == "")
        {
            return true
        }
        return false
    }

    // Sprawdź czy jest z dołu wolne miejsce na przesunięcie Tetrimino
    override fun checkDown(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // Sprawdź dwie kostki z dołu
        if (tetriminoList[this.square2 + 10].name == "" &&
                tetriminoList[this.square3 + 10].name == "")
        {
            return true
        }
        return false
    }

    // Przesuń w prawo
    override fun moveRight(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w prawo
        this.mid += 1
        // Tworzymy nowe 4 kwadraciki litery O przesunięte w prawo
        tetriminoList[this.mid] = TetriminoO(this.mid)
        if (this.square1 != 0) {
            this.square1 += 1
            tetriminoList[square1] = TetriminoO(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 1
            tetriminoList[square2] = TetriminoO(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 1
            tetriminoList[square3] = TetriminoO(square3)
        }
    }

    // Przesuń w lewo
    override fun moveLeft(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w lewo
        this.mid -= 1
        // Tworzymy nowe 4 kwadraciki litery O przesunięte w lewo
        tetriminoList[this.mid] = TetriminoO(this.mid)
        if (this.square1 != 0) {
            this.square1 -= 1
            tetriminoList[square1] = TetriminoO(square1)
        }
        if (this.square2 != 0) {
            this.square2 -= 1
            tetriminoList[square2] = TetriminoO(square2)
        }
        if (this.square3 != 0) {
            this.square3 -= 1
            tetriminoList[square3] = TetriminoO(square3)
        }
    }

    // Przesuń w dół
    override fun moveDown(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w dół
        this.mid += 10
        // Tworzymy nowe 4 kwadraciki litery O przesunięte w dół
        tetriminoList[this.mid] = TetriminoO(this.mid)
        if (this.square1 != 0) {
            this.square1 += 10
            tetriminoList[square1] = TetriminoO(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 10
            tetriminoList[square2] = TetriminoO(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 10
            tetriminoList[square3] = TetriminoO(square3)
        }
    }

}