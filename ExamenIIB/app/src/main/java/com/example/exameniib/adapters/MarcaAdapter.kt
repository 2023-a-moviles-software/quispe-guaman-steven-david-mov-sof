package com.example.exameniib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.R
import com.example.exameniib.models.Marca
import com.example.exameniib.viewholders.MarcaViewHolder

class MarcaAdapter(
    private var marcaList: MutableList<Marca>,
) : RecyclerView.Adapter<MarcaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarcaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return MarcaViewHolder(layoutInflater.inflate(R.layout.item_marca, parent, false))
    }

    override fun getItemCount(): Int = marcaList.size

    override fun onBindViewHolder(holder: MarcaViewHolder, position: Int) {
        val item = marcaList[position]
        holder.render(item)
    }

    fun updateData(newData: MutableList<Marca>) {
        marcaList = newData
        notifyDataSetChanged()
    }

    fun clearData() {
        marcaList.clear()
        notifyDataSetChanged()
    }

}