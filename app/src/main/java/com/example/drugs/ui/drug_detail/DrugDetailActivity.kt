package com.example.drugs.ui.drug_detail

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.models.Adiktif
import com.example.drugs.models.Narcotic
import com.example.drugs.models.Psicotropica
import kotlinx.android.synthetic.main.activity_drug_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*

class DrugDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_detail)
        setSupportActionBar(toolbar)
        fill()
    }

    private fun fill(){
        getAddictive()?.let {
            detail_drug_name.text = it.nama
            detail_drug_class.gone()
            detail_drug_type.gone()
            it.keterangan?.let { it1 -> detail_drug_description.setHtml(it1) }
            it.dampak?.let { it1 -> detail_drug_effect.setHtml(it1) }
        }
        getPsychotropic()?.let {
            detail_drug_name.text = it.nama
            detail_drug_class.text = it.golongan
            detail_drug_type.gone()
            it.keterangan?.let { it1 -> detail_drug_description.setHtml(it1) }
            it.dampak?.let { it1 -> detail_drug_effect.setHtml(it1) }
        }
        getNarcotic()?.let {
            detail_drug_name.text = it.nama
            detail_drug_class.text = it.golongan
            detail_drug_type.text = it.jenis
            it.keterangan?.let { it1 -> detail_drug_description.setHtml(it1) }
            it.dampak?.let { it1 -> detail_drug_effect.setHtml(it1) }
        }
    }

    private fun getAddictive() = intent.getParcelableExtra<Adiktif>("addictive")
    private fun getPsychotropic() = intent.getParcelableExtra<Psicotropica>("psychotropic")
    private fun getNarcotic() = intent.getParcelableExtra<Narcotic>("narcotic")
}