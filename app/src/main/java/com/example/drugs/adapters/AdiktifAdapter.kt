package com.example.drugs.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.DetailAdiktifActivity
import com.example.drugs.R
import com.example.drugs.models.Adiktif
import kotlinx.android.synthetic.main.list_item_adiktif.view.*

class AdiktifAdapter (private var adiktifs : MutableList<Adiktif>,
                         private val context : Context) : RecyclerView.Adapter<AdiktifAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(a: Adiktif, context: Context){
            with(itemView){
                adiktif_nama.text = a.nama
                adiktif_ket.text = a.keterangan
                adiktif_dampak.text = a.dampak
                adiktif_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/bhn_adiktif/"+a.gambar)
                setOnClickListener {
                    context.startActivity(Intent(context, DetailAdiktifActivity::class.java).apply {
                        putExtra("ADIKTIF", a)
                    })
                }
            }
        }
    }

    fun updateList(it: List<Adiktif>){
        adiktifs.clear()
        adiktifs.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate( R.layout.list_item_adiktif, parent, false))
    }

    override fun getItemCount() = adiktifs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(adiktifs[position], context)
}