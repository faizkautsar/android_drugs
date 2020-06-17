package com.example.drugs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.adapters.PsicotropicAdapter
import com.example.drugs.models.Psicotropica
import com.example.drugs.viewmodels.PsicotropicState
import com.example.drugs.viewmodels.PsicotropicaViewModel
import kotlinx.android.synthetic.main.activity_psicotropica.*

class PsicotropicaActivity : AppCompatActivity() {

    private lateinit var psicotropicaViewModel: PsicotropicaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psicotropica)
        setupUI()
        psicotropicaViewModel = ViewModelProvider(this).get(PsicotropicaViewModel::class.java)
        psicotropicaViewModel.listenToState().observer(this, Observer { handleState(it) })
        psicotropicaViewModel.listenToPsicotropica().observe(this, Observer { handlePsicotropica(it) })
        psicotropicaViewModel.fetchPsicotropica()

    }


    private fun handlePsicotropica(x : List<Psicotropica>){
        rv_psicotropica.adapter?.let { a ->
            if(a is PsicotropicAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        rv_psicotropica.apply {
            layoutManager = LinearLayoutManager(this@PsicotropicaActivity)
            adapter = PsicotropicAdapter(mutableListOf(), this@PsicotropicaActivity)
        }
    }

    private fun handleState(i : PsicotropicState){
        when(i){
            is PsicotropicState.Loading -> {
                if(i.state){
                    loading.visibility = View.VISIBLE
                }else{
                    loading.visibility = View.GONE
                }
            }
            is PsicotropicState.ShowToast -> Toast.makeText(this@PsicotropicaActivity, i.message, Toast.LENGTH_LONG).show()
        }
    }
}
