package com.yuzgulen.laera.utils

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

object Drawables {
    fun get(@DrawableRes drawableRes: Int): Drawable? {
        return App.instance.getDrawable(drawableRes)
    }

    fun getIdentifier(resourceName: String?): Int {
        return App.instance.resources.getIdentifier(resourceName, "drawable", App.instance.packageName)
    }
}