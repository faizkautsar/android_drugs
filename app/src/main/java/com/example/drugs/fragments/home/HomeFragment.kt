package com.example.drugs.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.drugs.AdiktifActivity
import com.example.drugs.NarcoticaActivity
import com.example.drugs.PsicotropicaActivity
import com.example.drugs.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupRecyclerView()
        card_narkotika.setOnClickListener { startActivity(Intent(requireActivity(), NarcoticaActivity::class.java)) }
        card_psicotropica.setOnClickListener { startActivity(Intent(requireActivity(), PsicotropicaActivity::class.java)) }
        card_zat_adiktif.setOnClickListener { startActivity(Intent(requireActivity(), AdiktifActivity::class.java)) }
    }

//    private fun setupRecyclerView(){
//        with(requireView()){
//            home_recyclerView.apply{
//                layoutManager = GridLayoutManager(requireActivity(), 3)
//                adapter = HomeRecyclerAdapter()
//
//            }
//        }
//
//    }

    private fun createDummyData(){

    }
}