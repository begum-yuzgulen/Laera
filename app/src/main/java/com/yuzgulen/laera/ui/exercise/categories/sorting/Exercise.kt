package com.yuzgulen.laera.ui.exercise.categories.sorting

import android.graphics.Color
import android.view.View
import androidx.navigation.findNavController
import com.yuzgulen.laera.databinding.SortingFragmentBinding
import java.util.*
import kotlin.random.Random

class Exercise{
    fun randomToSort() : MutableList<Int> {
        var array : MutableList<Int> = arrayListOf()
        var n : Int
        var i = 0
        while(i < 5){
            n = Random.nextInt(100)
            if (!array.contains(n)){
                array.add(n)
                i++
            }

        }
        return array
    }
    fun randomArray(toSort: MutableList<Int>) : MutableList<Int> {
        var array : MutableList<Int> = arrayListOf()
        var n : Int
        var i = 0
        while(i<10){
            n = Random.nextInt(100)
            if (!array.contains(n)  && !toSort.contains(n)){
                array.add(n)
                i++
            }
        }
        return array
    }

    fun arraysEqual(a : IntArray, b: IntArray, binding: SortingFragmentBinding) : Boolean {
        for(i in 0..4)
            if(a[i] !=  b[i]){
                when(i){
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
        if(arraysEqual(a, nextIt, binding)){
            binding.generated.text = Arrays.toString(a)
            binding.selected1.visibility = View.INVISIBLE
            binding.selected2.visibility = View.INVISIBLE
            binding.selected3.visibility = View.INVISIBLE
            binding.selected4.visibility = View.INVISIBLE
            binding.selected5.visibility = View.INVISIBLE

            return true
        }
        return false

    }

    fun buttons(binding: SortingFragmentBinding, steps: MutableList<IntArray>, it: Int){
        var iteration = it
        if(iteration == steps.size){
            binding.root.findNavController().navigate(
                SortingFragmentDirections.actionExerciseFragmentToCongratsFragment()
            )
        }
        binding.iteration.text = "Iteration: %d/%d".format(iteration+1, steps.size)

        var pressed = 1
        binding.option1.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option1.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option1.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option1.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option1.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option1.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option2.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option2.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option2.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option2.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option2.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option2.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }

        binding.option3.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option3.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option3.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option3.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option3.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option3.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)
                }
            }
            pressed++
        }

        binding.option4.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option4.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option4.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option4.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option4.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option4.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)
                }
            }
            pressed++
        }
        binding.option5.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option5.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option5.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option5.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option5.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option5.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)
                }
            }
            pressed++
        }
        binding.option6.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option6.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option6.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option6.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option6.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option6.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option7.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option7.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option7.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option7.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option7.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option7.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option8.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option8.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option8.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option8.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option8.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option8.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option9.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option9.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option9.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option9.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option9.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option9.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option10.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option10.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option10.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option10.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option10.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option10.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option11.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option11.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option11.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option11.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option11.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option11.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option12.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option12.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option12.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option12.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option12.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option12.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option13.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option13.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option13.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option13.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option13.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option13.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)
                }
            }
            pressed++
        }
        binding.option14.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option14.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option14.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option14.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option14.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option14.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)

                }
            }
            pressed++
        }
        binding.option15.setOnClickListener{
            when(pressed){
                1->{
                    binding.selected1.text = binding.option15.text
                    binding.selected1.visibility = View.VISIBLE

                }
                2->{
                    binding.selected2.text = binding.option15.text
                    binding.selected2.visibility = View.VISIBLE

                }
                3->{
                    binding.selected3.text = binding.option15.text
                    binding.selected3.visibility = View.VISIBLE

                }
                4->{
                    binding.selected4.text = binding.option15.text
                    binding.selected4.visibility = View.VISIBLE

                }
                5->{
                    binding.selected5.text = binding.option15.text
                    binding.selected5.visibility = View.VISIBLE
                    if(checkArray(binding, steps.get(iteration)))
                        buttons(binding, steps, iteration+1)
                }
            }
            pressed++
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