package com.example.drugs.ui.main.law

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.R
import com.example.drugs.models.Hukum
import com.example.drugs.viewmodels.HukumState
import com.example.drugs.viewmodels.HukumViewModel
import kotlinx.android.synthetic.main.fragment_peraturan.view.*

class PeraturanFragment : Fragment(R.layout.fragment_peraturan) {
    private lateinit var hukumViewModel: HukumViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        hukumViewModel = ViewModelProvider(this).get(HukumViewModel::class.java)
        hukumViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleState(it) })
        hukumViewModel.listenToHukum().observe(viewLifecycleOwner, Observer { handleHukum(it) })
        hukumViewModel.fetchHukum()
    }

    private fun handleHukum(x : List<Hukum>){
        view!!.rv_hukum.adapter?.let { a ->
            if(a is PeraturanAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        view!!.rv_hukum.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PeraturanAdapter(
                mutableListOf(),
                activity!!
            )
        }
    }

    private fun handleState(i : HukumState){
        when(i){
            is HukumState.Loading -> {
                if(i.state){
                    view!!.loading.visibility = View.VISIBLE
                }else{
                    view!!.loading.visibility = View.GONE
                }
            }
            is HukumState.ShowToast -> Toast.makeText(activity, i.message, Toast.LENGTH_LONG).show()
        }
    }

}
