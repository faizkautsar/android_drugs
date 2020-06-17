package com.example.drugs

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.drugs.models.Adiktif
import kotlinx.android.synthetic.main.activity_detail_adiktif.*
import kotlinx.android.synthetic.main.activity_detail_adiktif.adiktif_gambar
import kotlinx.android.synthetic.main.list_item_adiktif.*

class DetailAdiktifActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_adiktif)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

            getPassedAdiktif()?.let {a->
                supportActionBar?.setTitle(a.nama)
                adiktif_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/bhn_adiktif/"+a.gambar)
                adiktif_ket.text = a.keterangan
                adiktif_dampak.text = a.dampak
            }

        }


        private fun getPassedAdiktif() : Adiktif? = intent.getParcelableExtra("ADIKTIF")

}
