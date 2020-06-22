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

            getPassedAdiktif()?.let {
                supportActionBar?.setTitle(it.nama)
                adiktif_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/bhn_adiktif/"+it.gambar)
                adiktif_ket.setHtml(it.keterangan!!)
                adiktif_dampak.setHtml( it.dampak!!)
            }
        }

        private fun getPassedAdiktif() : Adiktif? = intent.getParcelableExtra("ADIKTIF")

}
