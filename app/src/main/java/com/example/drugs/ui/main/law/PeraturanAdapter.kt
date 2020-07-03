package com.example.drugs.ui.main.law

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugs.R
import com.example.drugs.models.Hukum
import kotlinx.android.synthetic.main.list_item_peraturan.view.*

class PeraturanAdapter(private var hukums: MutableList<Hukum>, private val context: Context) :
    RecyclerView.Adapter<PeraturanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(h: Hukum, context: Context) {
            with(itemView) {
                hukum_ket.text = h.keterangan
                hukum_isi.setHtml(h.isi!!)
            }
        }
    }

    fun updateList(it: List<Hukum>) {
        hukums.clear()
        hukums.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_peraturan, parent, false)
        )
    }

    override fun getItemCount() = hukums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(hukums[position], context)

}