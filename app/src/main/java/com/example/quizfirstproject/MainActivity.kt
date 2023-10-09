package com.example.quizfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button;
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.quizfirstproject.Question
import com.bignerdranch.android.quizfirstproject.QuizViewModel


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView
    var colCorrect: Int = 0
    private val quizViewModel: QuizViewModel by
    lazy { ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE

        if (quizViewModel.checkEnd(1))
            nextButton.visibility = View.GONE

        if (quizViewModel.checkEnd(2))
            nextButton.visibility = View.VISIBLE

        if (quizViewModel.currentIndex == 0)
            prevButton.visibility = View.GONE

        if (quizViewModel.currentIndex == 1)
            prevButton.visibility = View.VISIBLE
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer)
            colCorrect++
        Toast.makeText(this, messageResId,
            Toast.LENGTH_SHORT)
            .show()
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
        if (quizViewModel.checkEnd(1)) {
            Toast.makeText(this, "Правильно : $colCorrect",
                Toast.LENGTH_SHORT)
                .show()
        }
    }


}