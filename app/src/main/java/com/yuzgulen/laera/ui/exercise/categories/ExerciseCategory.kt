package com.yuzgulen.laera.ui.exercise.categories

import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.ui.exercise.categories.treetraversal.TreeTraversalFragmentDirections
import java.util.concurrent.TimeUnit

open class ExerciseCategory : Fragment() {

    var elapsedTime: String = ""
    private lateinit var countdownTimer: CountDownTimer
    val tryAgain = "Try again"
    val exercises = "Exercises"

    fun startTimer(timer: TextView?) {
        countdownTimer = object : CountDownTimer(300000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if(timer != null)
                    timer.text = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)%60)

                elapsedTime = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(300000-millisUntilFinished)%60,
                    TimeUnit.MILLISECONDS.toSeconds(300000-millisUntilFinished)%60)

            }

            override fun onFinish() {

            }
        }.start()
    }

    fun showDialog(title: String, message: String, direction: NavDirections) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(tryAgain) { dialog, which ->
                view?.findNavController()?.navigate(direction)
            }
            .setPositiveButton(exercises) { dialog, which ->
                view?.findNavController()?.navigate(
                    TreeTraversalFragmentDirections.actionNavTraverseToExercise()
                )
            }
            .show()
    }

    fun cancelTimer() {
        countdownTimer.cancel()
    }
}