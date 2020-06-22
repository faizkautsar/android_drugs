package com.example.drugs.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.DetailNarcoticaActivity
import com.example.drugs.R
import com.example.drugs.models.Narcotic
import kotlinx.android.synthetic.main.list_item_narkotika.view.*


class NarkotikaAdapter (private var narcotics : MutableList<Narcotic>, private val context : Context) : RecyclerView.Adapter<NarkotikaAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(n: Narcotic, context: Context){
            with(itemView){
                narkotik_nama.text = n.nama
                narkotik_jenis.text = n.jenis
                narkotik_gol.text = n.golongan
                narkotik_ket.setHtml(n.keterangan!!)
                narkotik_dampak.setHtml(n.dampak!!)
                narkotik_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/narkotika/"+n.gambar)
                setOnClickListener {
                    context.startActivity(Intent(context, DetailNarcoticaActivity::class.java).apply {
                        putExtra("NARCOTIC", n)
                    })
                }
            }
        }
    }

    fun updateList(it: List<Narcotic>){
        narcotics.clear()
        narcotics.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_narkotika, parent, false))
    }

    override fun getItemCount() = narcotics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(narcotics[position], context)
}