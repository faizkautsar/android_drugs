package com.example.drugs.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.drugs.AdiktifActivity
import com.example.drugs.NarcoticaActivity
import com.example.drugs.PsicotropicaActivity
import com.example.drugs.R
import kotlinx.android.synthetic.main.fragment_drug.*


class DrugFragment : Fragment(R.layout.fragment_drug){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_narkotika.setOnClickListener { startActivity(Intent(requireActivity(), NarcoticaActivity::class.java)) }
        btn_psicotropika.setOnClickListener { startActivity(Intent(requireActivity(), PsicotropicaActivity::class.java)) }
        btn_zat_adiktif.setOnClickListener { startActivity(Intent(requireActivity(), AdiktifActivity::class.java)) }
    }

}
