package com.example.drugs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.adapters.AdiktifAdapter
import com.example.drugs.models.Adiktif
import com.example.drugs.viewmodels.AdiktifState
import com.example.drugs.viewmodels.AdiktifViewModel
import kotlinx.android.synthetic.main.activity_adiktif.*

class AdiktifActivity : AppCompatActivity() {

    private lateinit var adiktifViewModel: AdiktifViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView(R.layout.activity_adiktif)
        setupUI()
        adiktifViewModel = ViewModelProvider(this).get(AdiktifViewModel::class.java)
        adiktifViewModel.listenToState().observer(this, Observer { handleState(it) })
        adiktifViewModel.listenToAdiktif().observe(this, Observer { handleAdiktif(it) })
        adiktifViewModel.fetchAdiktif()
    }

    private fun handleAdiktif(x : List<Adiktif>){
        rv_zat_adiktif.adapter?.let { a ->
            if(a is AdiktifAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        rv_zat_adiktif.apply {
            layoutManager = LinearLayoutManager(this@AdiktifActivity)
            adapter = AdiktifAdapter(mutableListOf(), this@AdiktifActivity)
        }
    }

    private fun handleState(i : AdiktifState){
        when(i){
            is AdiktifState.Loading -> {
                if(i.state){
                    loading.visibility = View.VISIBLE
                }else{
                    loading.visibility = View.GONE
                }
            }
            is AdiktifState.ShowToast -> Toast.makeText(this@AdiktifActivity, i.message, Toast.LENGTH_LONG).show()
        }
    }

}
