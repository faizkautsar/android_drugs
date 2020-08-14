package com.example.drugs.ui.drugs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.models.Adiktif
import com.example.drugs.ui.drug_detail.DrugDetailActivity
import com.example.drugs.webservices.ApiClient
import kotlinx.android.synthetic.main.list_item_drug.view.*

class AddictiveAdapter(private val addictives : MutableList<Adiktif>) : RecyclerView.Adapter<AddictiveAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindAddictive(data: Adiktif) {
            with(itemView) {
                val image = "${ApiClient.ENDPOINT}/public/uploads/narkoba/zat-adiktif/${data.gambar}"
                drug_image.load(image)
                drug_name.text = data.nama
                drug_class.gone()
                drug_type.gone()
                drug_description.setHtml(data.keterangan!!)
                drug_effect.setHtml(data.dampak!!)
                setOnClickListener {
                    context.startActivity(Intent(context, DrugDetailActivity::class.java).apply {
                        putExtra("addictive", data)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_drug, parent, false))

    override fun getItemCount() = addictives.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindAddictive(addictives[position])

    fun updateList(anAddictives: List<Adiktif>){
        addictives.clear()
        addictives.addAll(anAddictives)
        notifyDataSetChanged()
    }
}