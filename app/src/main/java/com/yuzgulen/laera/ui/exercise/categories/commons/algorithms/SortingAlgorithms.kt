package com.yuzgulen.laera.ui.exercise.categories.commons.algorithms

class SortingAlgorithms {
    fun  bubbleSort(array : IntArray) : MutableList<IntArray> {
        var temp:Int
        var stepsBubbleSort : MutableList<IntArray> = arrayListOf()
        for(i in 0 until (array.size-1)){
            for(j in 0 until (array.size-i-1)){
                if(array[j] > array[j+1]) {
                    temp = array[j]
                    array[j] = array[j+1]
                    array[j+1] = temp
                    stepsBubbleSort.add(array.clone())
                }
            }
        }
        return stepsBubbleSort
    }


    fun insertionsort(a:IntArray): MutableList<IntArray>{
        var stepsInsertionSort : MutableList<IntArray> = arrayListOf()
        var ok = false
        var key:Int
        var j:Int
        for (i in 1 until a.size){
            key = a[i]
            j = i-1
            ok = false
            while (j>=0 && a[j] > key){
                a[j+1] = a[j]
                j -=1
                ok = true
            }
            a[j+1] = key
            if(ok)
                stepsInsertionSort.add(a.clone())
        }

        return stepsInsertionSort
    }

    fun selectionSort(array: IntArray) : MutableList<IntArray>{
        var temp:Int
        var stepsSelectionSort : MutableList<IntArray> = arrayListOf()
        for(i in array.size-1 downTo  0){
            var max = i
            for(j in 0 until i){
                if(array[j]>array[max])
                    max=j
            }
            if(i!=max){
                temp = array[i]
                array[i]= array[max]
                array[max]=temp
                stepsSelectionSort.add(array.clone())
            }
        }
        return stepsSelectionSort
    }
}