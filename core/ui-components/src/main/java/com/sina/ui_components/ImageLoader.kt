package com.sina.ui_components

import android.widget.ImageView
import coil.load
import com.bumptech.glide.Glide

fun ImageView.loadGlide(imageSrc: String?) {
    Glide.with(this).load(imageSrc).into(this)
}

fun ImageView.loadCoil(imageSrc: String) {
    this.load(imageSrc)
}