package com.kickhead.bharatscanner_intent_demo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.kunalapk.smartrecyclerview.extensions.loadImage

object BaseBindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        view.loadImage(url)
    }
}