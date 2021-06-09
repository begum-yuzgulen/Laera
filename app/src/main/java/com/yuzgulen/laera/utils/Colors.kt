package com.yuzgulen.laera.utils

import androidx.annotation.ColorRes

object Colors {
    fun get(@ColorRes colorRes: Int): Int {
        return App.instance.resources.getColor(colorRes)
    }
}