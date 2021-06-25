package com.yuzgulen.laera.utils


interface ICallback<in T> {
    fun onCallback(value: T)
}