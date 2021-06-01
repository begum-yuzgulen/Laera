package com.yuzgulen.laera.utils

import androidx.annotation.ColorRes
import com.yuzgulen.laera.App

object Colors {
    fun get(@ColorRes colorRes: Int): Int {
        return App.instance.resources.getColor(colorRes)
    }
}