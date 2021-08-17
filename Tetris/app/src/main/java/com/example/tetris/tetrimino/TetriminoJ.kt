package com.example.tetris.tetrimino

class TetriminoJ : Tetrimino {

    // Tetrimino w kształcie litery J
    override var name: String = "J"

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
        // Kostka przesunięta o 1 w prawo i w dół
        this.square3 = mid + 11
        // Wstaw powyższe kostki na listę kostek
        tetriminoList[mid] = TetriminoJ(mid)
        tetriminoList[mid - 1] = TetriminoJ(mid - 1)
        tetriminoList[mid + 1] = TetriminoJ(mid + 1)
        tetriminoList[mid + 11] = TetriminoJ(mid + 11)
    }




    // Funkcja dokonująca rotacji Tetrimino
    override fun rotation(tetriminoList: ArrayList<Tetrimino>) : Boolean{

        // Sprawdź czy jakaś ściana nie blokuje obrotu
        if (!detectWall(tetriminoList)){
            return false
        }
        // Kostki Tetrimino J w przypadku obrotu z 0 s1 mid s2 s3
        // W przypadku obrotu z 1 rotacji s5 mid s8 s6
        // 2 razy obrócony: s2 mid s1 s4
        // 3 razy obrócony: s8 mid s5 s7
        this.square1 = mid - 1
        this.square2 = mid + 1
        this.square3 = mid + 11
        this.square4 = mid - 11
        this.square5 = mid - 10
        this.square6 = mid + 9
        this.square7 = mid - 9
        this.square8 = mid + 10

        // Sprawdzenie czy położone kwadraty nie stanowią blokady obrotu
        when(this.rotation){
            0 -> {
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square5].name == "" &&
                        tetriminoList[square6].name == "" &&
                        tetriminoList[square8].name == "" ) {
                    // Wyczyść poprzednie kwadraciki J
                    tetriminoList[square1] = Tetrimino()
                    tetriminoList[square2] = Tetrimino()
                    tetriminoList[square3] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square5] = TetriminoJ(square5)
                    tetriminoList[square6] = TetriminoJ(square6)
                    tetriminoList[square8] = TetriminoJ(square8)
                    // Teraz J jest obrócone o 90 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    // Dlatego wyzeruj "chwycone" kwadraciki (poza mid,s1,s2,s3)
                    this.square4 = 0
                    this.square5 = 0
                    this.square6 = 0
                    this.square7 = 0
                    this.square8 = 0
                    return false
                }
            }
            1 -> {
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square1].name == "" &&
                        tetriminoList[square2].name == "" &&
                        tetriminoList[square4].name == "" ) {
                    // Wyczyść poprzednie kwadraciki I
                    tetriminoList[square5] = Tetrimino()
                    tetriminoList[square6] = Tetrimino()
                    tetriminoList[square8] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square1] = TetriminoJ(square1)
                    tetriminoList[square2] = TetriminoJ(square2)
                    tetriminoList[square4] = TetriminoJ(square4)
                    // Teraz J jest obrócone o 180 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.square1 = 0
                    this.square2 = 0
                    this.square3 = 0
                    this.square4 = 0
                    this.square7 = 0
                    return false
                }
            }
            2-> {
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square5].name == "" &&
                        tetriminoList[square7].name == "" &&
                        tetriminoList[square8].name == "" ) {
                    // Wyczyść poprzednie kwadraciki I
                    tetriminoList[square1] = Tetrimino()
                    tetriminoList[square2] = Tetrimino()
                    tetriminoList[square4] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square5] = TetriminoJ(square5)
                    tetriminoList[square7] = TetriminoJ(square7)
                    tetriminoList[square8] = TetriminoJ(square8)
                    // Teraz J jest obrócone o 270 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.square3 = 0
                    this.square5 = 0
                    this.square6 = 0
                    this.square7 = 0
                    this.square8 = 0
                    return false
                }

            }
            3-> {
                // Jeżeli obrócone pozycje są puste
                if (tetriminoList[square1].name == "" &&
                        tetriminoList[square2].name == "" &&
                        tetriminoList[square3].name == "" ) {
                    // Wyczyść poprzednie kwadraciki I
                    tetriminoList[square5] = Tetrimino()
                    tetriminoList[square7] = Tetrimino()
                    tetriminoList[square8] = Tetrimino()
                    // Utwórz obrócone kwadraciki
                    tetriminoList[square1] = TetriminoJ(square1)
                    tetriminoList[square2] = TetriminoJ(square2)
                    tetriminoList[square3] = TetriminoJ(square3)
                    // Teraz J jest w początkowym stanie
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.square1 = 0
                    this.square2 = 0
                    this.square3 = 0
                    this.square4 = 0
                    this.square6 = 0
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
            0 -> {
                // Sprawdź czy górna ściana nie blokuje obrotu
                if (this.mid < 10){
                    return false
                }
            }
            1->{
                // Sprawdź prawą ścianę
                for (x in 9..199 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
            }
            2->{
                // Sprawdź podłogę
                if (this.mid > 190){
                    return false
                }
            }
            3->{
                // I sprawdź ostatnią czyli lewą ścianę
                for (x in 0..190 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W przeciwnym wypadku można obrócić Tetrimino J
        return true
    }

    // Sprawdź czy jest z prawej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation) {
            0->{
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    if (this.square2 == x){
                        return false
                    }
                }
                // Sprawdź prawe  2 kwadraciki ( J jest poziomo więc z prawej strony 2 kwadraciki sąsiadują)
                if (tetriminoList[this.square2 + 1].name == "" &&
                        tetriminoList[this.square3 + 1].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź prawe 3 kwadraciki
                if (tetriminoList[this.mid + 1].name == "" &&
                        tetriminoList[this.square5 + 1].name == "" &&
                        tetriminoList[this.square8 + 1].name == "")
                {
                    return true
                }
            }
            2->{
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    if (this.square2 == x){
                        return false
                    }
                }
                // Sprawdź prawe 2 kwadraciki (analogicznie jak w 0 tylko dalej od siebie są te kwadraty)
                if (tetriminoList[this.square4 + 1].name == "" &&
                        tetriminoList[this.square2 + 1].name == "")
                {
                    return true
                }

            }
            3->{
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    if (this.square7 == x){
                        return false
                    }
                }
                // Sprawdź 3 kostki
                if (tetriminoList[this.mid + 1].name == "" &&
                        tetriminoList[this.square7 + 1].name == "" &&
                        tetriminoList[this.square8 + 1].name == "")
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
            0->{
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    if (this.square1 == x){
                        return false
                    }
                }
                // Sprawdź 2 kostki
                if (tetriminoList[this.square1 - 1].name == "" &&
                        tetriminoList[this.square3 - 1].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    if (this.square6 == x){
                        return false
                    }
                }
                // Sprawdź 3 kostki
                if (tetriminoList[this.mid - 1].name == "" &&
                        tetriminoList[this.square5 - 1].name == "" &&
                        tetriminoList[this.square6 - 1].name == "")
                {
                    return true
                }
            }
            2->{
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    if (this.square1 == x){
                        return false
                    }
                }
                // Sprawdź 2 kostki
                if (tetriminoList[this.square1 - 1].name == "" &&
                        tetriminoList[this.square4 - 1].name == "")
                {
                    return true
                }
            }
            3->{
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź 3 kostki  //TODO CHECK!
                if (tetriminoList[this.square5 - 1].name == "" &&
                        tetriminoList[this.square8 - 1].name == "" &&
                        tetriminoList[this.mid - 1].name == "")
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
            0->{
                // Sprawdź 3 kwadraciki z dołu
                if (tetriminoList[this.mid + 10].name == "" &&
                        tetriminoList[this.square1 + 10].name == "" &&
                        tetriminoList[this.square3 + 10].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź 2 kwadraciki z dołu
                if (tetriminoList[this.square6 + 10].name == "" &&
                        tetriminoList[this.square8 + 10].name == "")
                {
                    return true
                }
            }
            2->{
                // Sprawdź 3 kwadraciki z dołu
                if (tetriminoList[this.mid + 10].name == "" &&
                        tetriminoList[this.square1 + 10].name == "" &&
                        tetriminoList[this.square2 + 10].name == "")
                {
                    return true
                }
            }
            3->{
                // Sprawdź 2 kwadraciki z dołu
                if (tetriminoList[this.square7 + 10].name == "" &&
                        tetriminoList[this.square8 + 10].name == "")
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
        // Tworzymy nowe 4 kwadraciki litery J przesunięte w prawo
        // W zależności od rotacji s1 może być puste albo pełne! to samo tyczy się reszty kwadracików
        tetriminoList[this.mid] = TetriminoJ(this.mid)
        if (this.square1 != 0) {
            this.square1 += 1
            tetriminoList[square1] = TetriminoJ(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 1
            tetriminoList[square2] = TetriminoJ(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 1
            tetriminoList[square3] = TetriminoJ(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 1
            tetriminoList[square4] = TetriminoJ(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 1
            tetriminoList[square5] = TetriminoJ(square5)
        }
        if (this.square6 != 0) {
            this.square6 += 1
            tetriminoList[square6] = TetriminoJ(square6)
        }
        if (this.square7 != 0) {
            this.square7 += 1
            tetriminoList[square7] = TetriminoJ(square7)
        }
        if (this.square8 != 0) {
            this.square8 += 1
            tetriminoList[square8] = TetriminoJ(square8)
        }
    }

    // Przesuń w lewo
    override fun moveLeft(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w lewo
        this.mid -= 1
        // Tworzymy nowe 4 kwadraciki litery J przesunięte w lewo
        tetriminoList[this.mid] = TetriminoJ(this.mid)
        if (this.square1 != 0) {
            this.square1 -= 1
            tetriminoList[square1] = TetriminoJ(square1)
        }
        if (this.square2 != 0) {
            this.square2 -= 1
            tetriminoList[square2] = TetriminoJ(square2)
        }
        if (this.square3 != 0) {
            this.square3 -= 1
            tetriminoList[square3] = TetriminoJ(square3)
        }
        if (this.square4 != 0) {
            this.square4 -= 1
            tetriminoList[square4] = TetriminoJ(square4)
        }
        if (this.square5 != 0) {
            this.square5 -= 1
            tetriminoList[square5] = TetriminoJ(square5)
        }
        if (this.square6 != 0) {
            this.square6 -= 1
            tetriminoList[square6] = TetriminoJ(square6)
        }
        if (this.square7 != 0) {
            this.square7 -= 1
            tetriminoList[square7] = TetriminoJ(square7)
        }
        if (this.square8 != 0) {
            this.square8 -= 1
            tetriminoList[square8] = TetriminoJ(square8)
        }
    }

    // Przesuń w dół
    override fun moveDown(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w dół
        this.mid += 10
        // Tworzymy nowe 4 kwadraciki litery J przesunięte w dół
        tetriminoList[this.mid] = TetriminoJ(this.mid)
        if (this.square1 != 0) {
            this.square1 += 10
            tetriminoList[square1] = TetriminoJ(square1)
        }
        if (this.square2 != 0) {
            this.square2 += 10
            tetriminoList[square2] = TetriminoJ(square2)
        }
        if (this.square3 != 0) {
            this.square3 += 10
            tetriminoList[square3] = TetriminoJ(square3)
        }
        if (this.square4 != 0) {
            this.square4 += 10
            tetriminoList[square4] = TetriminoJ(square4)
        }
        if (this.square5 != 0) {
            this.square5 += 10
            tetriminoList[square5] = TetriminoJ(square5)
        }
        if (this.square6 != 0) {
            this.square6 += 10
            tetriminoList[square6] = TetriminoJ(square6)
        }
        if (this.square7 != 0) {
            this.square7 += 10
            tetriminoList[square7] = TetriminoJ(square7)
        }
        if (this.square8 != 0) {
            this.square8 += 10
            tetriminoList[square8] = TetriminoJ(square8)
        }
    }
}