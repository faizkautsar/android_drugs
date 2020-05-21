package com.example.drugs.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.R
import com.example.drugs.adapters.RehabAdapter
import com.example.drugs.models.Rehab
import com.example.drugs.viewmodels.RehabState
import com.example.drugs.viewmodels.RehabViewModel
import kotlinx.android.synthetic.main.fragment_rehabilitation.view.*

class RehabilitationFragment : Fragment(R.layout.fragment_rehabilitation){
    private lateinit var rehabViewModel: RehabViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        rehabViewModel = ViewModelProvider(this).get(RehabViewModel::class.java)
        rehabViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleState(it) })
        rehabViewModel.listenToRehab().observe(viewLifecycleOwner, Observer { handleRehab(it) })
        rehabViewModel.fetchRehab()
    }

    private fun handleRehab(x : List<Rehab>){
        view!!.rv_rehab.adapter?.let { a ->
            if(a is RehabAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        view!!.rv_rehab.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RehabAdapter(mutableListOf(), activity!!)
        }
    }

    private fun handleState(i : RehabState){
        when(i){
            is RehabState.Loading -> {
                if(i.state){
                    view!!.loading.visibility = View.VISIBLE
                }else{
                    view!!.loading.visibility = View.GONE
                }
            }
            is RehabState.ShowToast -> Toast.makeText(activity, i.message, Toast.LENGTH_LONG).show()
        }
    }

}