package com.example.drugs.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.DetailPsicotropicaActivity
import com.example.drugs.R
import com.example.drugs.models.Psicotropica
import kotlinx.android.synthetic.main.list_item_psikotropika.view.*

class PsicotropicAdapter(private var psicotropicas : MutableList<Psicotropica>,
                         private val context : Context) : RecyclerView.Adapter<PsicotropicAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(r: Psicotropica, context: Context){
            with(itemView){
                psiko_nama.text = r.nama
                psiko_gol.text = r.golongan
                psiko_ket.text = r.keterangan
                psiko_dampak.text = r.dampak
                psiko_gambar.load("https://no-drugs.herokuapp.com/uploads/narkoba/psikotropika/"+r.gambar)
                setOnClickListener {
                    context.startActivity(Intent(context, DetailPsicotropicaActivity::class.java).apply {
                        putExtra("PSICO", r)
                    })
                }
            }
        }
    }

    fun updateList(it: List<Psicotropica>){
        psicotropicas.clear()
        psicotropicas.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_psikotropika, parent, false))
    }

    override fun getItemCount() = psicotropicas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(psicotropicas[position], context)
}