package com.example.drugs

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.drugs.models.Psicotropica
import kotlinx.android.synthetic.main.activity_detail_psicotropica.*
import kotlinx.android.synthetic.main.content_detail_psicotropica.*

class DetailPsicotropicaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_psicotropica)
        setSupportActionBar(toolbar)

        getPassedPsico()?.let {
            supportActionBar?.setTitle(it.nama)
            psiko_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/psikotropika/"+it.gambar)
            psiko_gol.text = it.golongan
            psiko_ket.text = it.keterangan
            psiko_dampak.text = it.dampak
        }

    }


    private fun getPassedPsico() : Psicotropica? = intent.getParcelableExtra("PSICO")
}
