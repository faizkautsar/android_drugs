package com.example.drugs.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val title = MutableLiveData<String>("")

    fun setTitle(t: String){
        title.value = t
    }

    fun getTitle() = title
}