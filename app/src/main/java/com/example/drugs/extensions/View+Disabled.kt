package com.example.drugs.extensions

import android.view.View

fun View.disabled(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}
