package com.example.tetris.tetrimino

class TetriminoT : Tetrimino {

    // Tetrimino w kształcie litery T
    override var name: String = "T"

    // Przekazujemy tylko indeks kwadratu, bo nazwę już mamy
    constructor(mid: Int){
        this.mid = mid
    }

    constructor(tetriminoList: ArrayList<Tetrimino>, mid: Int){
        // Indeks środka
        this.mid = mid
        // Kostka jedna w prawo
        this.squere1 = mid + 1
        // Kostka jedna w lewo
        this.squere2 = mid - 1
        // Kostka jedna w dół
        this.squere4 = mid + 10
        // Wstaw powyższe kostki na listę kostek
        tetriminoList[mid] = TetriminoT(mid)
        tetriminoList[mid+1] = TetriminoT(squere1)
        tetriminoList[mid-1] = TetriminoT(squere2)
        tetriminoList[mid+10] = TetriminoT(squere4)
    }

    // Funkcja dokonująca rotacji Tetrimino
    override fun rotation(tetriminoList: ArrayList<Tetrimino>) : Boolean {

        // Sprawdź czy jakaś ściana nie blokuje obrotu
        if (!detectWall(tetriminoList)){
            return false
        }
        // Kostki w 4 strony od środka
        this.squere1 = mid + 1
        this.squere2 = mid - 1
        this.squere3 = mid - 10
        this.squere4 = mid + 10

        // Sprawdzenie czy położone kwadraty nie stanowią blokady obrotu
        when(this.rotation){
            0 -> {
                // Jeżeli powyżej nie ma kwadracika (jego nazwa jest pusta)
                if (tetriminoList[squere3].name == "")
                {
                    // Wyczyść kwadracik prawej części Tetrimino T
                    tetriminoList[squere1] = Tetrimino()
                    // Utwórz kwadracik powyżej środkowego kwadracika Tetrimino T
                    tetriminoList[squere3] = TetriminoT(squere3)
                    // Teraz T jest obrócone o 90stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić bo kwadracik powyżej blokuje
                    // Dzięki poniższej linijce nie zbierzemy dodatkowego kwadracika ze sobą
                    this.squere3 = 0
                    return false
                }
            }
            // Sprawdzenie czy kwadracik z prawej strony nie blokuje
            1 ->{
                // Jeżeli jest pusty
                if (tetriminoList[squere1].name == "")
                {
                    // Analogicznie jak poprzednio wyczyść odpowiedni kwadracik
                    tetriminoList[squere4] = Tetrimino()
                    // W nowe miejsce wstaw nowy kwadracik tworzący T
                    tetriminoList[squere1] = TetriminoT(squere1)
                    // Teraz T jest obrócone o 180 stopni
                    return true
                }
                else {
                    // W przeciwnym razie nie można obrócić
                    this.squere1 = 0
                    return false
                }
            }
            // Sprawdzenie czy kwadracik od dołu nie blokuje
            2 -> {
                // Analogicznie jeżeli nie ma go to obróć
                // a w przeciwnym razie nie bo jest blokowany
                if (tetriminoList[squere4].name == "")
                {
                    tetriminoList[squere2] = Tetrimino()
                    tetriminoList[squere4] = TetriminoT(squere4)
                    // Tutaj T jest obrócone o 270stopni
                    return true
                }
                else {
                    this.squere4 = 0
                    return false
                }
            }
            // Sprawdzenie czy kwadracik od lewej nie blokuje
            3 -> {
                // Analogicznie jeżeli nie ma go to obróć
                // a w przeciwnym razie nie bo jest blokowany
                if (tetriminoList[squere2].name == "")
                {
                    tetriminoList[squere3] = Tetrimino()
                    tetriminoList[squere2] = TetriminoT(squere2)
                    // Tutaj T nie jest obrócone (wracamy do stanu początkowego)
                    return true
                }
                else {
                    this.squere2 = 0
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
                // Sprawdź górną ścianę (obrót na początku gry)
                // Jeżeli indeks środka jest w górnym wierszu
                if (this.mid < 10){
                    // Zwróć brak możliwości obrotu
                    return false
                }
            }
            // Jeżeli był raz obrócony
            1 -> {
                // Sprawdź czy prawa ściana go nie blokuje
                for (x in 9..199 step 10){
                    // Czyli sprawdź czy indeks środka znajduje się
                    // w prawej (ostatniej) kolumnie
                    if (this.mid == x){
                        // Jeżeli tak zwróć brak możliwości obrotu
                        return false
                    }
                }
            }
            // Jeżeli był dwa razy obrócony
            2 -> {
                // Sprawdź podłogę
                if (this.mid > 190){
                    // Jeżeli środek znajduje się w dolnym wierszu nie można obrócić
                    return false
                }
            }
            // Jeżeli był 3 razy obrócony
            3 -> {
                // Sprawdź lewą ścianę
                for (x in 0..190 step 10){
                    // Analogicznie jak prawą tylko pierwsza kolumna
                    if (this.mid == x){
                        // Jeżeli się znajduje w tej kolumnie to nie można obrócić
                        return false
                    }
                }
            }
            // Jeżeli jakiś błąd wystąpił i rotacja nie jest od 0 do 3 włącznie
            else -> return false
        }
        // W przeciwnym wypadku można obrócić TetriminoT
        return true
    }

    // Sprawdź czy jest z prawej strony wolne miejsce na przesunięcie Tetrimino
    override fun checkRight(tetriminoList: ArrayList<Tetrimino>) : Boolean {
        // W zależności od rotacji
        when(this.rotation){
            0 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Czy prawa kostka nie dotyka ściany
                    if (this.squere1 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy prawa i dolna kostka nie dotyka innych kostek na planszy
                if (tetriminoList[this.squere1 + 1].name == "" && tetriminoList[this.squere4 + 1].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Sprawdź czy środkowa kostka dotyka prawej ściany
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy środkowa oraz dolna i górna kostka nie dotyka innej kostki z prawej strony
                if (tetriminoList[this.mid + 1].name == "" && tetriminoList[this.squere3 + 1].name == "" && tetriminoList[this.squere4 + 1].name == "")
                {
                    return true
                }
            }
            2 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Sprawdź czy prawa kostka dotyka prawej ściany
                    if (this.squere1 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy górna oraz prawa kostka nie dotyka innej kostki z prawej strony
                if (tetriminoList[this.squere1 + 1].name == "" && tetriminoList[this.squere3 + 1].name == "")
                {
                    return true
                }
            }
            3 -> {
                // Sprawdź ścianę
                for (x in 9..199 step 10){
                    // Sprawdź czy prawa kostka dotyka prawej ściany
                    if (this.squere1 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy górna i dolna oraz prawa kostka nie dotyka innej kostki z prawej strony
                if (tetriminoList[this.squere1 + 1].name == "" && tetriminoList[this.squere3 + 1].name == "" && tetriminoList[this.squere4 + 1].name == "")
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
            0 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy lewa kostka dotyka lewej ściany
                    if (this.squere2 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy lewa i dolna kostka nie dotyka innej kostki z lewej strony
                if (tetriminoList[this.squere2 - 1].name == "" && tetriminoList[this.squere4 - 1].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy lewa kostka dotyka lewej ściany
                    if (this.squere2 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy lewa i dolna oraz górna kostka nie dotyka innej kostki z lewej strony
                if (tetriminoList[this.squere2 - 1].name == "" && tetriminoList[this.squere3 - 1].name == "" && tetriminoList[this.squere4 - 1].name == "")
                {
                    return true
                }
            }
            2 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy lewa kostka dotyka lewej ściany
                    if (this.squere2 == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy lewa oraz górna kostka nie dotyka innej kostki z lewej strony
                if (tetriminoList[this.squere2 - 1].name == "" && tetriminoList[this.squere3 - 1].name == "")
                {
                    return true
                }
            }
            3 -> {
                // Sprawdź ścianę
                for (x in 0..190 step 10){
                    // Sprawdź czy środkowa kostka dotyka lewej ściany
                    if (this.mid == x){
                        return false
                    }
                }
                // Sprawdź inne kostki
                // Czy środkowa i górna oraz dolna kostka nie dotyka innej kostki z lewej strony
                if (tetriminoList[this.mid - 1].name == "" && tetriminoList[this.squere3 - 1].name == "" && tetriminoList[this.squere4 - 1].name == "")
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
            0 -> {
                // Sprawdź prawy dolny i lewy kwadracik czy może zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.squere1 + 10].name == "" && tetriminoList[this.squere2 + 10].name == "" && tetriminoList[this.squere4 + 10].name == "")
                {
                    return true
                }
            }
            1 -> {
                // Sprawdź prawy i dolny kwadracik czy może zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.squere2 + 10].name == "" && tetriminoList[this.squere4 + 10].name == "")
                {
                    return true
                }
            }
            2 -> {
                // Sprawdź prawy środkowy i lewy kwadracik czy może zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.mid + 10].name == "" && tetriminoList[this.squere1 + 10].name == "" && tetriminoList[this.squere2 + 10].name == "")
                {
                    return true
                }
            }
            3 -> {
                // Sprawdź dolny i lewy kwadracik czy może zejść w dół ( czy niżej jest puste )
                if (tetriminoList[this.squere1 + 10].name == "" && tetriminoList[this.squere4 + 10].name == "")
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
        // Tworzymy nowe 4 kwadraciki litery T przesunięte w prawo
        tetriminoList[this.mid] = TetriminoT(this.mid)
        if (this.squere1 != 0) {
            this.squere1 += 1
            tetriminoList[squere1] = TetriminoT(squere1)
        }
        if (this.squere2 != 0) {
            this.squere2 += 1
            tetriminoList[squere2] = TetriminoT(squere2)
        }
        if (this.squere3 != 0) {
            this.squere3 += 1
            tetriminoList[squere3] = TetriminoT(squere3)
        }
        if (this.squere4 != 0) {
            this.squere4 += 1
            tetriminoList[squere4] = TetriminoT(squere4)
        }
    }

    // Przesuń w lewo
    override fun moveLeft(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w lewo
        this.mid -= 1
        // Tworzymy nowe 4 kwadraciki litery T przesunięte w lewo
        tetriminoList[this.mid] = TetriminoT(this.mid)
        if (this.squere1 != 0) {
            this.squere1 -= 1
            tetriminoList[squere1] = TetriminoT(squere1)
        }
        if (this.squere2 != 0) {
            this.squere2 -= 1
            tetriminoList[squere2] = TetriminoT(squere2)
        }
        if (this.squere3 != 0) {
            this.squere3 -= 1
            tetriminoList[squere3] = TetriminoT(squere3)
        }
        if (this.squere4 != 0) {
            this.squere4 -= 1
            tetriminoList[squere4] = TetriminoT(squere4)
        }
    }

    // Przesuń w dół
    override fun moveDown(tetriminoList: ArrayList<Tetrimino>){
        // Indeks środka jest przesunięty w dół
        this.mid += 10
        // Tworzymy nowe 4 kwadraciki litery T przesunięte w dół
        tetriminoList[this.mid] = TetriminoT(this.mid)
        if (this.squere1 != 0) {
            this.squere1 += 10
            tetriminoList[squere1] = TetriminoT(squere1)
        }
        if (this.squere2 != 0) {
            this.squere2 += 10
            tetriminoList[squere2] = TetriminoT(squere2)
        }
        if (this.squere3 != 0) {
            this.squere3 += 10
            tetriminoList[squere3] = TetriminoT(squere3)
        }
        if (this.squere4 != 0) {
            this.squere4 += 10
            tetriminoList[squere4] = TetriminoT(squere4)
        }
    }
}