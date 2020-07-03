package com.example.drugs.ui.main.prevention

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugs.R
import com.example.drugs.models.Upaya
import com.example.drugs.viewmodels.UpayaState
import com.example.drugs.viewmodels.UpayaViewModel
import kotlinx.android.synthetic.main.fragment_upaya.view.*

class UpayaFragment : Fragment(R.layout.fragment_upaya){
    private lateinit var upayaViewModel: UpayaViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        upayaViewModel = ViewModelProvider(this).get(UpayaViewModel::class.java)
        upayaViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleState(it) })
        upayaViewModel.listenToUpaya().observe(viewLifecycleOwner, Observer { handleUpaya(it) })
        upayaViewModel.fetchUpaya()
    }

    private fun handleUpaya(x : List<Upaya>){
        view!!.rv_upaya.adapter?.let { a ->
            if(a is UpayaAdapter){
                a.updateList(x)
            }
        }
    }

    private fun setupUI(){
        view!!.rv_upaya.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UpayaAdapter(
                mutableListOf(),
                activity!!
            )
        }
    }

    private fun handleState(i : UpayaState){
        when(i){
            is UpayaState.Loading -> {
                if(i.state){
                    view!!.loading.visibility = View.VISIBLE
                }else{
                    view!!.loading.visibility = View.GONE
                }
            }
            is UpayaState.ShowToast -> Toast.makeText(activity, i.message, Toast.LENGTH_LONG).show()
        }
    }

}