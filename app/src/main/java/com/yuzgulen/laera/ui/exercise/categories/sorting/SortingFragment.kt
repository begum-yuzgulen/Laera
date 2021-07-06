package com.yuzgulen.laera.ui.exercise.categories.sorting

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.yuzgulen.laera.R
import com.yuzgulen.laera.algorithms.SortingAlgorithms
import com.yuzgulen.laera.databinding.SortingFragmentBinding
import com.yuzgulen.laera.ui.exercise.categories.ExerciseCategory
import com.yuzgulen.laera.utils.Strings
import kotlinx.android.synthetic.main.sorting_fragment.*
import java.util.*

class SortingFragment : ExerciseCategory() {

    private lateinit var viewModel: SortingViewModel
    private lateinit var selectedItem: String
    private lateinit var retryDirection: NavDirections
    private lateinit var exerciseDirection: NavDirections

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<SortingFragmentBinding>(inflater,R.layout.sorting_fragment,container,false)
        viewModel = ViewModelProvider(this).get(SortingViewModel::class.java)
        val toSort : MutableList<Int> = viewModel.randomToSort()
        val generatedArray : MutableList<Int> = viewModel.randomArray(toSort)
        val args = SortingFragmentArgs.fromBundle(requireArguments())
        selectedItem = args.selectedItem
        (activity as AppCompatActivity).supportActionBar?.title = selectedItem
        binding.generated.text = toSort.toString()

        for(i in 0..4) {
            generatedArray.add(toSort[i])
        }

        val optionButtons = listOf(binding.option1, binding.option2, binding.option3, binding.option4,
            binding.option5, binding.option6, binding.option7,
            binding.option8, binding.option9, binding.option10,
            binding.option11, binding.option12, binding.option13,
            binding.option14, binding.option15)

        var n : Int
        for(i in 0 until 15) {
            n = generatedArray.random()
            optionButtons[i].text = n.toString()
            generatedArray.remove(n)
        }
        var steps : MutableList<IntArray> = arrayListOf()
        when(selectedItem){
            "Bubble Sort" -> {
                steps = SortingAlgorithms().bubbleSort(toSort.toIntArray())
            }
            "Insertion Sort" -> {
                steps = SortingAlgorithms().insertionsort(toSort.toIntArray())
            }
            "Selection Sort" -> {
                steps = SortingAlgorithms().selectionSort(toSort.toIntArray())
            }
        }

        var s = ""
        for(step in steps){
            s += Arrays.toString(step)
        }
        binding.selectedAlg.text = selectedItem

        buttons(binding, steps, 0)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryDirection = SortingFragmentDirections.actionSortingFragmentToSortingFragment(selectedItem)
        exerciseDirection = SortingFragmentDirections.actionSortingFragmentToExerciseFragment()
        refresh.setOnClickListener {
            requireView().findNavController().navigate(retryDirection)
        }
        info.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Instructions")
                .setMessage(Strings.get(R.string.loremIpsum))
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }
        startTimer(timer, retryDirection, exerciseDirection)
    }

    fun arraysEqual(a : IntArray, b: IntArray, binding: SortingFragmentBinding) : Boolean {
        for(i in 0..4)
            if(a[i] !=  b[i]) {
                when(i) {
                    0 -> binding.selected1.setBackgroundColor(Color.RED)
                    1 -> binding.selected2.setBackgroundColor(Color.RED)
                    2 -> binding.selected3.setBackgroundColor(Color.RED)
                    3 -> binding.selected4.setBackgroundColor(Color.RED)
                    4 -> binding.selected5.setBackgroundColor(Color.RED)
                }
                return false
            }
        return true
    }
    fun checkArray(binding : SortingFragmentBinding, nextIt: IntArray):Boolean{
        var a : IntArray = intArrayOf(0,0,0,0,0)
        a[0] = Integer.parseInt(binding.selected1.text.toString())
        a[1] = Integer.parseInt(binding.selected2.text.toString())
        a[2] = Integer.parseInt(binding.selected3.text.toString())
        a[3] = Integer.parseInt(binding.selected4.text.toString())
        a[4] = Integer.parseInt(binding.selected5.text.toString())
        if(arraysEqual(a, nextIt, binding)) {
            binding.generated.text = Arrays.toString(a)
            binding.selected1.visibility = View.INVISIBLE
            binding.selected2.visibility = View.INVISIBLE
            binding.selected3.visibility = View.INVISIBLE
            binding.selected4.visibility = View.INVISIBLE
            binding.selected5.visibility = View.INVISIBLE

            return true
        }
        viewModel.updateScores(elapsedTime, false)
        cancelTimer()
        return false

    }

    fun buttons(binding: SortingFragmentBinding, steps: MutableList<IntArray>, it: Int){
        var iteration = it
        if(iteration == steps.size) {
            buildDialog("Correct!",
                "You have successfully sorted the array! Congratulations!",
                retryDirection, exerciseDirection)
            viewModel.updateScores(elapsedTime, true)
        }
        binding.iteration.text = "Iteration: %d/%d".format(iteration+1, steps.size)

        var pressed = 1
        val selectedButtons = listOf(binding.selected1, binding.selected2, binding.selected3,
            binding.selected4, binding.selected5)
        val optionButtons = listOf(binding.option1, binding.option2, binding.option3, binding.option4,
            binding.option5, binding.option6, binding.option7,
            binding.option8, binding.option9, binding.option10,
            binding.option11, binding.option12, binding.option13,
            binding.option14, binding.option15)
        optionButtons.forEach { b ->
            b.setOnClickListener {
                selectedButtons[pressed - 1].text = b.text
                selectedButtons[pressed - 1].visibility = View.VISIBLE
                if (pressed == 5 && checkArray(binding, steps.get(iteration))) {
                    buttons(binding, steps, iteration+1)
                }
                pressed++
            }
        }
        binding.delete.setOnClickListener{
            when(pressed){
                2->{
                    binding.selected1.visibility = View.INVISIBLE

                }
                3->{
                    binding.selected2.visibility = View.INVISIBLE

                }
                4->{
                    binding.selected3.visibility = View.INVISIBLE

                }
                5->{
                    binding.selected4.visibility = View.INVISIBLE

                }
            }
            pressed--
        }
    }
}
