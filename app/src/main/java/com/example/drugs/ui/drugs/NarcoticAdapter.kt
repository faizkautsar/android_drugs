package com.example.drugs.ui.drugs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.R
import com.example.drugs.models.Narcotic
import com.example.drugs.ui.drug_detail.DrugDetailActivity
import com.example.drugs.webservices.ApiClient
import kotlinx.android.synthetic.main.list_item_drug.view.*

class NarcoticAdapter (private val narcotis : MutableList<Narcotic>) : RecyclerView.Adapter<NarcoticAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindNarcotic(data: Narcotic) {
            with(itemView) {
                val image = "${ApiClient.ENDPOINT}/public/uploads/narkoba/narkotika/${data.gambar}"
                drug_image.load(image)
                drug_name.text = data.nama
                drug_class.text = data.golongan
                drug_type.text = data.jenis
                drug_description.setHtml(data.keterangan!!)
                drug_effect.setHtml(data.dampak!!)
                setOnClickListener {
                    context.startActivity(Intent(context, DrugDetailActivity::class.java).apply {
                        putExtra("narcotic", data)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_drug, parent, false))

    override fun getItemCount() = narcotis.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindNarcotic(narcotis[position])

    fun updateList(newList: List<Narcotic>){
        narcotis.clear()
        narcotis.addAll(newList)
        notifyDataSetChanged()
    }
}