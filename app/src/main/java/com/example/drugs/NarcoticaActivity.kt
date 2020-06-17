package com.example.drugs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.adapters.NarkotikaAdapter
import com.example.drugs.models.Narcotic
import com.example.drugs.viewmodels.NarcoticState
import com.example.drugs.viewmodels.NarcoticViewModel
import kotlinx.android.synthetic.main.activity_narcotica.*

class NarcoticaActivity : AppCompatActivity() {
    private lateinit var narcoticViewModel: NarcoticViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_narcotica)
        setupUI()
        narcoticViewModel = ViewModelProvider(this).get(NarcoticViewModel::class.java)
        narcoticViewModel.listenToState().observer(this, Observer { handleState(it) })
        narcoticViewModel.listenToNarcotic().observe(this, Observer { handleNarcotic(it) })
        narcoticViewModel.fetchNarcotic()
    }

    private fun handleNarcotic(x : List<Narcotic>){
        rv_narkotik.adapter?.let { a ->
            if(a is NarkotikaAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        rv_narkotik.apply {
            layoutManager = LinearLayoutManager(this@NarcoticaActivity)
            adapter = NarkotikaAdapter(mutableListOf(), this@NarcoticaActivity)
        }
    }

    private fun handleState(i : NarcoticState){
        when(i){
            is NarcoticState.Loading -> {
                if(i.state){
                    loading.visibility = View.VISIBLE
                }else{
                    loading.visibility = View.GONE
                }
            }
            is NarcoticState.ShowToast -> Toast.makeText(this@NarcoticaActivity, i.message, Toast.LENGTH_LONG).show()
        }
    }

}
