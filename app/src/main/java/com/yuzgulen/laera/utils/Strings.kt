package com.yuzgulen.laera.utils

import androidx.annotation.StringRes
import com.yuzgulen.laera.App

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }
}