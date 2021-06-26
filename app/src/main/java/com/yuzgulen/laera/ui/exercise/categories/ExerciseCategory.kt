package com.yuzgulen.laera.ui.exercise.categories

import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yuzgulen.laera.R
import com.yuzgulen.laera.utils.Strings
import java.util.concurrent.TimeUnit

open class ExerciseCategory : Fragment() {

    var elapsedTime: String = ""
    private lateinit var countdownTimer: CountDownTimer
    val tryAgain = "Try again"
    val exercises = "Exercises"

    fun startTimer(timer: TextView?, retryDirection: NavDirections?, exerciseDirection: NavDirections?) {
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
                buildDialog("Time", Strings.get(R.string.timeExpired), retryDirection, exerciseDirection).show()
            }
        }.start()
    }

    fun buildDialog(title: String, message: String, retryDirection: NavDirections?, exerciseDirection: NavDirections?) : MaterialAlertDialogBuilder  {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
        if (exerciseDirection != null) {
            dialog.setPositiveButton(exercises) { _, _ ->
                view?.findNavController()?.navigate(exerciseDirection)
            }
        }
        if (retryDirection != null) {
            dialog.setNegativeButton(tryAgain) { _, _ ->
                view?.findNavController()?.navigate(retryDirection)
            }
        }
        return dialog
    }

    fun cancelTimer() {
        countdownTimer.cancel()
    }
}