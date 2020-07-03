package com.example.drugs.ui.main.prevention

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drugs.R
import com.example.drugs.models.Upaya
import kotlinx.android.synthetic.main.list_item_upaya.view.*

class UpayaAdapter (private var upayas: MutableList<Upaya>, private val context: Context) :
    RecyclerView.Adapter<UpayaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(u: Upaya, context: Context) {
            with(itemView) {
                upaya_aspek.text = u.aspek
                upaya_ket.setHtml(u.keterangan!!)
            }
        }
    }

    fun updateList(it: List<Upaya>) {
        upayas.clear()
        upayas.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_upaya, parent, false)
        )
    }

    override fun getItemCount() = upayas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(upayas[position], context)

}