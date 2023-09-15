package com.example.exameniib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.viewholders.IngresoViewHolder
import com.example.gestionfinanzas.Modelos.Ingreso
import com.example.gestionfinanzas.R


class IngresosAdapter(
    private var ingresosList: MutableList<Ingreso>,
) : RecyclerView.Adapter<IngresoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngresoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return IngresoViewHolder(layoutInflater.inflate(R.layout.item_movimiento, parent, false))
    }

    override fun getItemCount(): Int = ingresosList.size

    override fun onBindViewHolder(holder: IngresoViewHolder, position: Int) {
        val item = ingresosList[position]
        holder.render(item)
    }

    fun updateData(newData: MutableList<Ingreso>) {
        ingresosList = newData
        notifyDataSetChanged()
    }

    fun clearData() {
        ingresosList.clear()
        notifyDataSetChanged()
    }

}