package com.example.drugs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugs.R
import com.example.drugs.models.Rehab
import kotlinx.android.synthetic.main.list_item_rehabilitasi.view.*

class RehabAdapter (private var rehabs : MutableList<Rehab>, private val context : Context) : RecyclerView.Adapter<RehabAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(r: Rehab, context: Context){
            with(itemView){
                pasien_umur.text = r.umur.toString() + " tahun"
                pasien_rujukan.text = r.rujukan
                setOnClickListener {
                    println()
                }
            }
        }
    }

    fun updateList(it: List<Rehab>){
        rehabs.clear()
        rehabs.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_rehabilitasi, parent, false))
    }

    override fun getItemCount() = rehabs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rehabs[position], context)
}