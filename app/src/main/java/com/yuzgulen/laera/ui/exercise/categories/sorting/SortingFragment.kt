package com.yuzgulen.laera.ui.exercise.categories.sorting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.yuzgulen.laera.R
import com.yuzgulen.laera.databinding.SortingFragmentBinding
import java.util.*

class SortingFragment : Fragment() {

    private lateinit var viewModel: SortingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<SortingFragmentBinding>(inflater,R.layout.sorting_fragment,container,false)

        val toSort : MutableList<Int> = Exercise().randomToSort()
        val generatedArray : MutableList<Int> = Exercise().randomArray(toSort)
        val args = SortingFragmentArgs.fromBundle(arguments!!)
        val selectedItem = args.selectedItem
        binding.generated.text = toSort.toString()

        for(i in 0..4){
            generatedArray.add(toSort[i])
        }


        var n : Int
        for(i in 1..16){
            when (i) {
                1 -> {
                    n = generatedArray.random()
                    binding.option1.text = n.toString()
                    generatedArray.remove(n)
                }
                2 -> {
                    n = generatedArray.random()
                    binding.option2.text = n.toString()
                    generatedArray.remove(n)
                }
                3 -> {
                    n = generatedArray.random()
                    binding.option3.text = n.toString()
                    generatedArray.remove(n)
                }
                4 -> {
                    n = generatedArray.random()
                    binding.option4.text = n.toString()
                    generatedArray.remove(n)
                }
                5 -> {
                    n = generatedArray.random()
                    binding.option5.text = n.toString()
                    generatedArray.remove(n)
                }
                6 -> {
                    n = generatedArray.random()
                    binding.option6.text = n.toString()
                    generatedArray.remove(n)
                }
                7 -> {
                    n = generatedArray.random()
                    binding.option7.text = n.toString()
                    generatedArray.remove(n)
                }
                8 -> {
                    n = generatedArray.random()
                    binding.option8.text = n.toString()
                    generatedArray.remove(n)
                }
                9 -> {
                    n = generatedArray.random()
                    binding.option9.text = n.toString()
                    generatedArray.remove(n)
                }
                10 -> {
                    n = generatedArray.random()
                    binding.option10.text = n.toString()
                    generatedArray.remove(n)
                }
                11 -> {
                    n = generatedArray.random()
                    binding.option11.text = n.toString()
                    generatedArray.remove(n)
                }
                12 -> {
                    n = generatedArray.random()
                    binding.option12.text = n.toString()
                    generatedArray.remove(n)
                }
                13 -> {
                    n = generatedArray.random()
                    binding.option13.text = n.toString()
                    generatedArray.remove(n)
                }
                14 -> {
                    n = generatedArray.random()
                    binding.option14.text = n.toString()
                    generatedArray.remove(n)
                }
                15 -> {
                    n = generatedArray.random()
                    binding.option15.text = n.toString()
                    generatedArray.remove(n)
                }
            }
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
        binding.refresh.setOnClickListener{
            view!!.findNavController().navigate(
                SortingFragmentDirections.actionExerciseFragmentToExerciseFragment(selectedItem)
            )
        }
        Exercise().buttons(binding, steps, 0)

        return binding.root
    }


}
