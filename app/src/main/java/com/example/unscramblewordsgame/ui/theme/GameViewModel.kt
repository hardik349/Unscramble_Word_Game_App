package com.example.unscramblewordsgame.ui.theme

import android.provider.UserDictionary.Words
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramblewordsgame.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    //private will make it to access this under this class
    private val _uiState = MutableStateFlow(GameUiState())
    //a callback property for private variable
    var uiState : StateFlow<GameUiState> = _uiState.asStateFlow()

    //create a variable to store the current value of the variable
    private lateinit var currentWord : String
    //create a method to pick a random word from allWords and shuffle it
    private  fun pickRandomWordAndShuffle() : String{
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }
        else{
            usedWords.add(currentWord)
            return shuflleCurrentWord(currentWord)
        }
    }
    //add the property which contains the usedWords
    private var usedWords : MutableSet<String> = mutableSetOf()
    //add a method to shuffle the word
    private fun shuflleCurrentWord(word : String) : String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while(String(tempWord).equals(word)){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    //add the method to enter the user Guess
    fun updateUserGuess(guessWord : String){
        userGuess = guessWord
    }

    //add the mutableState of varialble
    var userGuess by mutableStateOf(" ")
        private set

    //checck the user Guess is wrong or right
    fun checkUserGuess(){

    }

    //add the method to reset the game
    private fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWordAndShuffle())
    }
    //add the init block and reset the game
    init {
        resetGame()
    }
}
