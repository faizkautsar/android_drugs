package com.example.drugs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.drugs.models.Narcotic
import kotlinx.android.synthetic.main.activity_detail_narcotica.*
import kotlinx.android.synthetic.main.content_detail_narcotica.*

class DetailNarcoticaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_narcotica)
        setSupportActionBar(toolbar)

        getPassedNarcotica()?.let {n->
            supportActionBar?.setTitle(n.nama)
            narkotik_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/narkotika/"+n.gambar)
            narkotik_jenis.text=n.jenis
            narkotik_gol.text = n.golongan
            narkotik_ket.text = n.keterangan
            narkotik_dampak.text = n.dampak
        }
    }
    private fun getPassedNarcotica() : Narcotic? = intent.getParcelableExtra("NARCOTICA")


}

