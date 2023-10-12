package com.bignerdranch.android.quizfirstproject

import androidx.lifecycle.ViewModel
import com.example.quizfirstproject.R


private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val currentQuestionAnswered: Boolean
        get() = questionBank[currentIndex].answered
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun checkEnd(Col:Int): Boolean {
        return (currentIndex + Col) == questionBank.size
    }

    fun questionAnswered(){
        questionBank[currentIndex].answered = true
    }

}