package com.example.exameniib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.viewholders.GastoViewHolder
import com.example.gestionfinanzas.Modelos.Gasto
import com.example.gestionfinanzas.R

class GastosAdapter(
    private var electrodomesticoList: MutableList<Gasto>,
) : RecyclerView.Adapter<GastoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GastoViewHolder(
            layoutInflater.inflate(
                R.layout.item_movimiento, parent, false
            ))
    }

    override fun getItemCount(): Int = electrodomesticoList.size

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val item = electrodomesticoList[position]
        holder.render(item)
    }

    fun updateData(newData: MutableList<Gasto>) {
        electrodomesticoList = newData
        notifyDataSetChanged()
    }

}