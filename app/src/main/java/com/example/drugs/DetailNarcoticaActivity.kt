package com.example.drugs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.drugs.models.Narcotic
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_detail_narcotica.*
import kotlinx.android.synthetic.main.content_detail_narcotica.*

class DetailNarcoticaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_narcotica)
        setSupportActionBar(toolbar)

        getPassedNarcotica()?.let {
            supportActionBar?.setTitle(it.nama)
            narkotik_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/narkotika/"+it.gambar)
            narkotik_jenis.text = it.jenis
            narkotik_gol.text = it.golongan
            narkotik_ket.setHtml(it.keterangan!!)
            narkotik_dampak.setHtml(it.dampak!!)
        }

        fab_aa.setOnClickListener {
            if (isLoggedIn()){
                toast("anda sudah login")
            }else{
                popup("anda belum login, silahkan login")
            }
        }
    }

    private fun popup(message : String){
        AlertDialog.Builder(this).apply {
            setMessage(message)
            setPositiveButton("Login"){d, _->
                startActivity(Intent(this@DetailNarcoticaActivity, LoginActivity::class.java))
                d.dismiss()
            }
        }.show()
    }

    private fun isLoggedIn() = !Constants.getToken(this@DetailNarcoticaActivity).equals("UNDEFINED")
    private fun getPassedNarcotica() : Narcotic? = intent.getParcelableExtra("NARCOTIC")

    private fun toast(m :String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()
}

