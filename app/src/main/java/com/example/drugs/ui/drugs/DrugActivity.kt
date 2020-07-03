package com.example.drugs.ui.drugs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.extensions.toast
import com.example.drugs.extensions.visible
import com.example.drugs.models.Adiktif
import com.example.drugs.models.Narcotic
import com.example.drugs.models.Psicotropica
import kotlinx.android.synthetic.main.activity_drug.*
import kotlinx.android.synthetic.main.content_drug.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrugActivity : AppCompatActivity() {
    private val drugViewModel: DrugViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug)
        setSupportActionBar(toolbar)
        setupToolbar()
        setupRecyclerView()
        observe()
        fetch()
    }

    private fun fetch(){
        when(getPassedDrugType()){
            1 -> drugViewModel.fetchAddictives()
            2 -> drugViewModel.fetchPsychotropic()
            else -> drugViewModel.fetchNarcotic()
        }
    }

    private fun observe(){
        observeState()
        observePsychotropics()
        observeNarcotics()
        observeAddictives()
    }

    private fun observeState() = drugViewModel.getState().observer(this, Observer { handleState(it) })
    private fun observePsychotropics() = drugViewModel.getPsychotropics().observe(this, Observer { handlePsycho(it) })
    private fun observeAddictives() = drugViewModel.getAddictives().observe(this, Observer { handleAddictives(it) })
    private fun observeNarcotics() = drugViewModel.getNarcotics().observe(this, Observer { handleNarcotics(it) })

    private fun handleState(it: DrugState){
        when(it){
            is DrugState.Loading -> isLoading(it.state)
            is DrugState.ShowToast -> toast(it.message)
        }
    }

    private fun isLoading(b: Boolean) = if(b) loading.visible() else loading.gone()

    private fun handlePsycho(it: List<Psicotropica>){
        rv_drugs.adapter?.let { adapter ->
            if(adapter is PsychotropicAdapter){
                adapter.updateList(it)
            }
        }
    }

    private fun handleAddictives(adds: List<Adiktif>){
        rv_drugs.adapter?.let { adapter ->
            if(adapter is AddictiveAdapter){
                adapter.updateList(adds)
            }
        }
    }

    private fun handleNarcotics(nars: List<Narcotic>){
        rv_drugs.adapter?.let { adapter ->
            if(adapter is NarcoticAdapter){
                adapter.updateList(nars)
            }
        }
    }

    private fun setupRecyclerView(){
        rv_drugs.apply {
            adapter = when {
                getPassedDrugType() == 1 -> AddictiveAdapter(mutableListOf())
                getPassedDrugType() == 2 -> PsychotropicAdapter(mutableListOf())
                else -> NarcoticAdapter(mutableListOf())
            }
            layoutManager = LinearLayoutManager(this@DrugActivity)
        }
    }


    private fun getPassedDrugType() = intent.getIntExtra("type", 1)

    private fun setupToolbar(){
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { finish() }
    }
}