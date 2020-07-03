package com.example.drugs.ui.drugs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.models.Psicotropica
import com.example.drugs.ui.drug_detail.DrugDetailActivity
import kotlinx.android.synthetic.main.list_item_drug.view.*

class PsychotropicAdapter (private val psychos : MutableList<Psicotropica>) : RecyclerView.Adapter<PsychotropicAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPsycho(data: Psicotropica) {
            with(itemView) {
                drug_image.load(data.gambar)
                drug_name.text = data.nama
                drug_class.text = data.golongan
                drug_type.gone()
                drug_description.setHtml(data.keterangan!!)
                drug_effect.setHtml(data.dampak!!)
                setOnClickListener {
                    context.startActivity(Intent(context, DrugDetailActivity::class.java).apply {
                        putExtra("psychotropic", data)
                    })
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_drug, parent, false))

    override fun getItemCount() = psychos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindPsycho(psychos[position])

    fun updateList(newList: List<Psicotropica>){
        psychos.clear()
        psychos.addAll(newList)
        notifyDataSetChanged()
    }
}