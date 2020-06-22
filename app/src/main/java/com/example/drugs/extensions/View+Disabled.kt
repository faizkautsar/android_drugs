package com.example.drugs.extensions

import android.view.View

fun View.disabled(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}