package com.example.tetris

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tetris.adapter.SquareAdapter
import com.example.tetris.player.PlayerViewModel
import com.example.tetris.tetrimino.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

open class VersusActivityExample : AppCompatActivity(), View.OnClickListener{

    // Listy kwadratów
    private val tetriminoSquaresList = ArrayList<Tetrimino>()
    private val nextTetrimino = ArrayList<Tetrimino>()

    // Ustawienia timera
    private var timeLeftInMs = Constants.START_TIME_IN_MS
    private var countDownTimer: CountDownTimer? = null
    private var countDownInterval = Constants.COUNT_DOWN_INITIAL_INTERVAL
    private var timerRunning: Boolean = false
    // Flagi stanów
    private var bottom: Boolean = false
    private var playing: Boolean = false
    private var paused: Boolean = false
    // Wynik aktualny, poziom oraz zmienne pomocnicze
    private var score: Int = 0
    private var currentLevel : Int = 0
    private var fullRows: Int = 0
    private var next: Int = 0
    private var nextView: GridView? = null

    // Później inicjalizowane zmienne
    // Główne Tetrimino
    private lateinit var mainTetrimino: Tetrimino
    // Napis końca gry
    private lateinit var gameOver : TextView
    // Całkowita liczba uzyskanych puntków
    private lateinit var totalScore : TextView
    // Widok umożliwiający wyświetlanie graczowi aktualnego poziomu
    private lateinit var level : TextView
    // Nawigacja spadająctym Tetrimino
    private lateinit var leftMoveView : ImageView
    private lateinit var rightMoveView : ImageView
    private lateinit var downMoveView : ImageView
    private lateinit var rotateMoveView : ImageView
    // Siatka gry
    private lateinit var gameGridView : GridView
    // Wstrzymanie i wznowienie gry
    private lateinit var pauseClickView : ImageView
    private lateinit var resumeClickView : ImageView
    // Start gry
    private lateinit var start : Button
    // Siatki następnego Tetrimino
    private lateinit var nextTetriminoCols4 : GridView
    private lateinit var nextTetriminoCols5 : GridView
    // Tekst powodzenia
    //private lateinit var goodLuckView: TextView
    private var highScore = "0"
    private lateinit var highScoreView : TextView
    private lateinit var playerNickName : String

    private lateinit var mPlayerViewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ukryj UI żeby przypadkiem nie kliknąć cofnij podczas gry ;)
        hideSystemUI()
        // Ustaw kontent
        setContentView(R.layout.activity_versus_working_copy)
        // Znajdź wszystkie widoki
        findAllViewsById()
        // Ustaw wszystkie nasłuchiwacze kliknięć
        setAllOnClickListeners()
        // Inicjalizacja planszy
        initializeBoard()
        // Wyłącz przyciski nawigacji
        setButtonsClickability(false)
        // Ustaw pseudonim gracza
        playerNickName = intent.getStringExtra("playerNickName").toString()
        // Ustaw poziom
        changeLevel(
            intent.getIntExtra("startLevel",0)
                .toString().toInt())
        // Zdobądź PlayerViewModel
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        if(playerNickName != ""){
            // Zdobądź najwyższy wynik z bazy danych
            GlobalScope.launch(Dispatchers.IO){
                val hscore = mPlayerViewModel.getHighScore(playerNickName)
                highScore = hscore.toString()
                highScoreView.text = highScore
            }
        }
    }

    fun receive(){
        if(ConnectActivity.socket != null){
            val scanner = Scanner(ConnectActivity.socket!!.getInputStream())
            while(scanner.hasNextLine()){
                val read = scanner.nextLine()
                findViewById<TextView>(R.id.textView).text = read
                junkRows(read.toInt())
            }
        }
        else{
            Toast.makeText(this, "Socket is null", Toast.LENGTH_SHORT).show()
        }

    }

    private fun junkRows( number: Int) {
      for(i in 1..number){

      }


    }

    fun send(message: Int){
        ConnectActivity.socket!!.getOutputStream().write(message)
    }

    // Źródło : https://developer.android.com/training/system-ui/immersive
    // Funkcja ukrywająca UI systemu
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Funkcja pokazująca UI systemu
    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onClick(v: View?) {
        when(v){
            leftMoveView -> {
                moveLeft()
            }
            downMoveView -> {
                downClicked()
            }
            rightMoveView -> {
                moveRight()
            }
            rotateMoveView ->  {
                rotateClicked()
            }
            pauseClickView -> {
                if (playing){
                    pauseGame()
                }
            }
            resumeClickView -> {
                resumeClicked()
            }
            start -> {
                hideSystemUI()
                startClicked()
                pauseClickView.visibility = View.VISIBLE
                setButtonsClickability(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Jeżeli gra trwała wstrzymaj ją!
        if (playing){
            pauseGame()
        }
    }

    // Funkcja obsługująca kliknięcie resume
    private fun resumeClicked(){
        resumeGame()
        pauseClickView.visibility = View.VISIBLE
        resumeClickView.visibility = View.GONE
        setButtonsClickability(true)
    }

    // Funkcja obsługująca kliknięcie rotacji
    private fun rotateClicked(){
        // Sprawdź czy można obrócić
        if (mainTetrimino.rotation(tetriminoSquaresList)){
            // Obróć
            if (mainTetrimino.rotation == 3) {
                mainTetrimino.rotation = 0
            } else {
                mainTetrimino.rotation += 1
            }
            // Wyświetl nową planszę
            gameGridViewAdapt()
        }
        else {
            return
        }
    }

    // Funkcja działająca w przypadku wciśnięcia w dół
    private fun downClicked(){
        moveDown()
    }

    // Funkcja działająca w przypadku wciśnięcia Start gry
    private fun startClicked(){
        initializeBoard()
        score = 0
        playing = true
        start.visibility = View.GONE
        gameOver.visibility = View.GONE
        startGame()
    }

    // Funkcja znajdująca widoki za pomocą Id
    private fun findAllViewsById(){
        start = findViewById(R.id.start)
        gameGridView = findViewById(R.id.game_grid)
        leftMoveView = findViewById(R.id.left)
        downMoveView = findViewById(R.id.down)
        rightMoveView = findViewById(R.id.right)
        rotateMoveView = findViewById(R.id.rotate)
        pauseClickView = findViewById(R.id.pause)
        resumeClickView = findViewById(R.id.resume)
        gameOver = findViewById(R.id.gameOver)
        level = findViewById(R.id.level)
        totalScore = findViewById(R.id.totalScore)
        nextTetriminoCols4 = findViewById(R.id.nextBlock_4)
        nextTetriminoCols5 = findViewById(R.id.nextBlock_5)
        highScoreView = findViewById(R.id.high_score)
    }

    // Funkcja przesuwająca tetrimino w dół na ile może on long click
    private fun moveDownLongClick(){
        // Jeżeli nie natrafiło na spód planszy
        if (mainTetrimino.detectBottom(tetriminoSquaresList)) {
            // Sprawdź możliwość ruchu w dół
            if (mainTetrimino.checkDown(tetriminoSquaresList)) {
                // Usuń Tetrimino z listy
                mainTetrimino.removeTetrimino(tetriminoSquaresList)
                // Wstaw przesunięte w dół
                mainTetrimino.moveDown(tetriminoSquaresList)
                // Wyświetl nową plansze
                gameGridViewAdapt()
            }
        }
    }

    // Funkcja ustawiająca OnClickListenery do widoków
    private fun setAllOnClickListeners(){
        start.setOnClickListener(this)
        leftMoveView.setOnClickListener(this)
        leftMoveView.setOnLongClickListener{
            // Spróbuj przesunąć 10 razy (od prawej do lewej maksymalnie)
            for(i in 0..9){
                moveLeft()
            }
            // Wydarzenie zostało obsłużone == true
            true
        }
        downMoveView.setOnClickListener(this)
        downMoveView.setOnLongClickListener {
            // Spróbuj przesunąć 20 razy (od góry do dołu maksymalnie)
            for(i in 0..19){
                moveDownLongClick()
            }
            // Wydarzenie zostało obsłużone == true
            true
        }
        rightMoveView.setOnClickListener(this)
        rightMoveView.setOnLongClickListener{
            // Spróbuj przesunąć 10 razy (od lewej do prawej maksymalnie)
            for(i in 0..9){
                moveRight()
            }
            // Wydarzenie zostało obsłużone == true
            true
        }
        rotateMoveView.setOnClickListener(this)
        pauseClickView.setOnClickListener(this)
        resumeClickView.setOnClickListener(this)
    }

    // Funkcja Startująca grę
    private fun startGame() {
        playing = true
        sendNewTetrimino()
    }

    // Funkcja wybierająca wylosowane Tetrimino
    private fun selectTetrimino() : Tetrimino {
        return when (next) {
            1 -> TetriminoI(tetriminoSquaresList, 4)
            2 -> TetriminoJ(tetriminoSquaresList, 4)
            3 -> TetriminoL(tetriminoSquaresList, 4)
            4 -> TetriminoO(tetriminoSquaresList, 4)
            5 -> TetriminoS(tetriminoSquaresList, 4)
            6 -> TetriminoT(tetriminoSquaresList, 4)
            7 -> TetriminoZ(tetriminoSquaresList, 4)
            else -> Tetrimino()
        }
    }

    // Funkcja inicjalizująca planszę
    private fun initializeBoard(){
        // Usuń wszystkie kwadratowe bloki z planszy
        tetriminoSquaresList.clear()
        // Wstaw do listy nowe puste kwadraty
        for (x in 0 until 200) {
            tetriminoSquaresList.add(Tetrimino())
        }
        // Stwórz nowe puste Tetrimino
        mainTetrimino = Tetrimino()
        // Wyświetl pustą plansze
        gameGridViewAdapt()
    }

    // Funkcja uruchamiająca timer
    private fun timer() {
        countDownTimer = object : CountDownTimer(timeLeftInMs, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMs = millisUntilFinished
                moveDown()
            }
            override fun onFinish() {
                timerRunning = false
            }
        }.start()
        timerRunning = true
    }

    // Funkcja przesuwająca lecące Tetrimino w dół
    private fun moveDown() {
        // Jeżeli nie natrafiło na spód planszy
        if (mainTetrimino.detectBottom(tetriminoSquaresList)) {
            // Sprawdź możliwość ruchu w dół
            if (mainTetrimino.checkDown(tetriminoSquaresList)) {
                // Usuń Tetrimino z listy
                mainTetrimino.removeTetrimino(tetriminoSquaresList)
                // Wstaw przesunięte w dół
                mainTetrimino.moveDown(tetriminoSquaresList)
                // Wyświetl nową plansze
                gameGridViewAdapt()
                // Wyjdź z funkcji
                return
            }
        }
        // Jeżeli wcześniej nie wyszedł to oznacza
        // że Tetrimino zostało położone
        locatedTetrimino()
    }

    // Funkcja uruchamiająca się, gdy Tetrimino już nie ruszy w dół
    private fun locatedTetrimino(){
        // Jeżeli nie można ruszyć w dół
        bottom = true
        // Sprawdź czy to jest koniec gry
        if (tetriminoSquaresList[4].mid == mainTetrimino.mid){
            playing = false
        }
        // Anuluj Timer
        cancelTimer()
        // Spróbuj wysłać nowe Tetrimino
        sendNewTetrimino()
    }

    // Funkcja wysyłająca nowe Tetrimino
    private fun sendNewTetrimino() {
        // Jeżeli gra trwa
        if (playing){
            randNewTetrimino()
        } else {
            // Wyświetl przycisk startu
            start.visibility = View.VISIBLE
            // Jeżeli jest zatrzymana
            if(paused){
                // Wyświetl napis Wznowienie
                start.text = getString(R.string.resume_txt)
            }
            else {
                // Wyświetl napis końca gry
                gameOver.visibility = View.VISIBLE
                // Animacją
                gameOver.animate().translationY(0f).duration = 1000
                // Przycisk startu gry z napisem: Retry
                start.text = getString(R.string.retry_txt)
                // Pokaż ui systemowe
                showSystemUI()
                // Wyłącz możliwość klikania ruchów
                setButtonsClickability(false)
            }
        }
    }
    // Funkcja losująca nowe Tetrimino
    private fun randNewTetrimino(){
        // Sprawdź ułożone wiersze
        checkingFullRows()
        // Wylosuj następne Tetrimino (od 1 do 7)
        if (next == 0){
            next = (1..7).random()
        }
        // Wybierz wylosowane Tetrimino
        mainTetrimino = selectTetrimino()
        // Wyświetl wybrane Tetrimino na ekranie
        gameGridViewAdapt()
        // Włącz timer opadania Tetrimino w dół
        timer()
        // Wylosuj następne Tetrimino
        next = (1..7).random()
        // Wyświetl następne Tetrimino
        displayNext(next)
    }

    // Funkcja wstrzymująca grę
    private fun pauseGame(){
        // Ukryj widok pause i pokaż widok resume
        pauseClickView.visibility = View.GONE
        resumeClickView.visibility = View.VISIBLE
        // Zablokój możliwość klikania w przyciski
        setButtonsClickability(false)
        playing = false
        paused = true
        // wstrzymaj timer
        pauseTimer()
        // Przywróć UI podczas pauzy by można było się cofnąć
        showSystemUI()
    }

    // Funkcja wznawiająca gre
    private fun resumeGame(){
        playing = true
        paused = false
        // wznów timer
        resumeTimer()
        // Ukryj UI
        hideSystemUI()
    }

    // Funkcja wyświetlająca następne Tetrimino
    private fun displayNext(next: Int){
        // Wyczyść następne tetrimino
        nextTetrimino.clear()
        // Wstaw 20 pustych kawałków Tetrimino
        for (x in 0 until 20) {
            nextTetrimino.add(Tetrimino())
        }
        // Stwórz nowe Tetrimino
        NextTetrimino(nextTetrimino, next)
        // Ustaw widok następnego Tetrimino
        setNextTetriminoGridView()
        // Wyświetl następne tetrimino
        nextTetriminoGridViewAdapt()
    }

    // Funkcja ustawiająca widok następnego Tetrimino
    // w zależności od ilości kolumn ilu potrzebuje
    private fun setNextTetriminoGridView(){
        // Jeżeli jest O lub I to wykorzystaj 4 kolumny
        if (next == 1 || next == 4){
            nextView = nextTetriminoCols4
            nextTetriminoCols4.visibility = View.VISIBLE
            nextTetriminoCols5.visibility = View.GONE
        } else {
            nextView = nextTetriminoCols5
            nextTetriminoCols4.visibility = View.GONE
            nextTetriminoCols5.visibility = View.VISIBLE
        }
    }

    // Funkcja wyświetlająca następne tetrimino
    private fun nextTetriminoGridViewAdapt(){
        // Wyświetl kolejne Tetrimino za pomocą adaptera
        nextView!!.adapter = SquareAdapter(this, nextTetrimino, Constants.TEMP_SQUARE)
    }

    // Funkcja odpowiedzialna za przesunięcie Tetrimino w prawo
    private fun moveRight(){
        // Sprawdź czy można przesunąć w prawo
        if (mainTetrimino.checkRight(tetriminoSquaresList)) {
            // Jeżeli można to usuń dane Tetrimino z listy
            mainTetrimino.removeTetrimino(tetriminoSquaresList)
            // Wstaw to Tetrimino przesunięte w prawo
            mainTetrimino.moveRight(tetriminoSquaresList)
            // Oraz wyświetl nową siatkę gry
            gameGridViewAdapt()
        }
    }

    // Funkcja odpowiedzialna za przesunięcie Tetrimino w lewo
    private fun moveLeft(){
        // Sprawdź czy można przesunąć w lewo
        if (mainTetrimino.checkLeft(tetriminoSquaresList)) {
            // Jeżeli można to usuń dane Tetrimino z listy
            mainTetrimino.removeTetrimino(tetriminoSquaresList)
            // Wstaw to Tetrimino przesuniętew lewo
            mainTetrimino.moveLeft(tetriminoSquaresList)
            // Oraz wyświetl nową siatkę gry
            gameGridViewAdapt()
        }
    }

    // Funkcja wstrzymująca działanie timera
    private fun pauseTimer(){
        countDownTimer!!.cancel()
        timerRunning = false
    }

    // Funkcja wznawiająca działanie timera
    private fun resumeTimer(){
        countDownTimer!!.start()
        timerRunning = true
    }

    // Funkcja resetująca timer i wyłączająca go
    private fun cancelTimer() {
        timeLeftInMs = Constants.START_TIME_IN_MS
        countDownTimer!!.onFinish()
        countDownTimer!!.cancel()
    }

    // Funkcja sprawdzająca kolumny w danym wierszu
    private fun checkingColsInRow(rowStart: Int, rowEnd: Int) {
        // Kolumny sprawdzane od lewej do prawej po danych indeksach początku
        // i końca wiersza
        for (i in rowStart until rowEnd) {
            // Jeżeli jakiekolwiek pole w wierszu jest puste to wyjdź z pętli
            // (oznacza w tym przypadku wyjście także z funkcji)
            if (tetriminoSquaresList[i].name == "") {
                break
            } else {
                // W przeciwnym wypadku sprawdź czy rozważasz ostatnią kolumnę
                if (i == rowEnd - 1) {
                    scoreRow(rowStart,rowEnd)
                }
            }
        }
        send(fullRows)
    }

    // Funkcja usuwająca wiersz
    private fun scoreRow(rowStart: Int, rowEnd: Int){
        resetRow(rowStart,rowEnd)
        moveTetriminoSquaresList(rowStart)
        gameGridViewAdapt()
        // zwiększ liczbę ułożonych pełnych wierszy
        fullRows += 1
    }

    // Funkcja resetująca wiersz
    private fun resetRow(rowStart: Int, rowEnd: Int){
        // Skasuj cały wiersz (wypełnij pustymi Kwadratami)
        for (j in rowStart until rowEnd) {
            tetriminoSquaresList[j] = Tetrimino()
        }
    }

    // Funkcja przesuwająca klocki w dół w wyniku usunięcia wiersza
    private fun moveTetriminoSquaresList(rowStart: Int){
        // Od dołu do góry przesuwaj klocki w dół
        for (z in rowStart downTo 0) {
            // Jeżeli badany klocek jest niepusty
            if (tetriminoSquaresList[z].name != "") {
                // klockowi poniżej przypisz jego parametry
                tetriminoSquaresList[z + 10].mid = tetriminoSquaresList[z].mid + 10
                tetriminoSquaresList[z + 10].name = tetriminoSquaresList[z].name
                // a jego parametry potem wyzeruj
                tetriminoSquaresList[z].mid = 0
                tetriminoSquaresList[z].name = ""
            }
        }
    }

    // Funkcja wyświetlająca siatkę gry
    private fun gameGridViewAdapt(){
        // Wyświetl nową planszę za pomocą adaptera
        gameGridView.adapter = SquareAdapter(this, tetriminoSquaresList, Constants.TEMP_SQUARE)
    }

    // Funkcja sprawdzająca czy zostały, a jeżeli tak to ile,
    // ułożone pełne wiersze, a także zmienia wynik/poziom w przypadku ułożenia
    private fun checkingFullRows(){
        countingFullRows()
        if(fullRows != 0){
            changePointsAndIncLevel()
        }
    }

    // Funkcja zliczająca ułożone wiersze
    private fun countingFullRows(){
        fullRows = 0
        // Sprawdzenie każdego wiersza czy został uzupełniony:
        // Od góry do dołu
        for (rowStart in 0 until 200 step 10) {
            // Kolumny sprawdzamy od lewej do prawej
            val rowEnd = rowStart + 10
            // Funkcja sprawdzająca kolumny w danym wierszu
            checkingColsInRow(rowStart, rowEnd)
        }
    }

    // Funkcja zwiększająca liczbę punktów i zwiększająca poziom
    private fun changePointsAndIncLevel(){
        // Sprawdzenie czy usunięte zostały 4 wiersze... tzw. Tetris
        // (bonusowe punkty) punkty = 2x 100 * ilość wierszy == 800
        // i wyliczenie uzyskanej liczby punktów
        val point: Int = if (fullRows == 4){
            800
        } else {
            // w przeciwnym wypadku daj tylko (100 * ilość wierszy) punktów
            100 * fullRows
        }
        // Dodaj punkty do wyniku gracza
        addPoints(point)
        // Sprawdź czy możesz zwiększyć poziom
        if(countDownInterval > 100){
            // Jeżeli możesz to zwiększ poziom na nowy
            changeLevel(score/100)
        }
    }

    // Funkcja dodająca punkty przekazywane w argumencie
    private fun addPoints(point : Int){
        // Zwiększ wynik o zdobyte punkty
        score += point
        // Wyświetl graczowi nowy wynik
        totalScore.text = score.toString()
        // Sprawdź wynik
        checkHighScore(score)
    }

    // Funkcja sprawdzająca najwyższy wynik i zapisująca go w bazie danych
    private fun checkHighScore(score: Int){
        if (score > highScore!!.toInt()){
            highScore = score.toString()
            highScoreView.text = highScore
            if (playerNickName != ""){
                mPlayerViewModel.updateHighScore(highScore.toInt(),playerNickName)
            }
        }
    }

    // Funkcja ustawiająca możliwość (lub jej brak) klikania
    // w przyciski nawigacji spadającym tetrimino
    private fun setButtonsClickability(bool: Boolean){
        // Ustaw poszczególne przyciski: lewo, dół, prawo, rotacja
        leftMoveView.isEnabled = bool
        downMoveView.isEnabled = bool
        rightMoveView.isEnabled = bool
        rotateMoveView.isEnabled = bool
    }

    // Funkcja zmieniająca aktyualny poziom gry
    // na poziom podany w argumencie
    private fun changeLevel(newLevel : Int){
        if(newLevel > currentLevel){
            // Zmień poziom gry
            currentLevel = newLevel
            // Adekwatnie zmień przerwę pomiędzy tickami timera
            countDownInterval = Constants.COUNT_DOWN_INITIAL_INTERVAL - currentLevel * 50L
            // Wyświetl graczowi nowy poziom
            level.text = currentLevel.toString()
        }
    }
}
