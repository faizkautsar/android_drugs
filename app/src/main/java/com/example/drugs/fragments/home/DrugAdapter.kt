package com.example.drugs.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.R
import kotlinx.android.synthetic.main.list_item_drug.view.*

data class Drug(var name : String? = null, var imageUrl : String? = null)

class DrugAdapter (private val drugs: MutableList<Drug>, private val context: Context) : RecyclerView.Adapter<DrugAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(drug: Drug){
            with(itemView){
                drug_name.text = drug.name
                drug_image.load(drug.imageUrl)
                setOnClickListener {
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_drug, parent, false))

    override fun getItemCount() = drugs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(drugs[position])
}