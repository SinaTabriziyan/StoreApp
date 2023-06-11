package com.sina.common.utils

import android.widget.ImageView
import coil.load
import com.bumptech.glide.Glide

fun ImageView.load(imageSrc: String) {
    Glide.with(this).load(imageSrc).into(this)
}

fun ImageView.loadCoil(imageSrc: String) {
    this.load(imageSrc)
}