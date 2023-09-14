package com.example.exameniib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.R
import com.example.exameniib.models.Laptop
import com.example.exameniib.viewholders.LaptopViewHolder

class LaptopAdapter(
    private var LaptopList: MutableList<Laptop>,
) : RecyclerView.Adapter<LaptopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaptopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LaptopViewHolder(
            layoutInflater.inflate(
                R.layout.item_laptop, parent, false
            ))
    }

    override fun getItemCount(): Int = LaptopList.size

    override fun onBindViewHolder(holder: LaptopViewHolder, position: Int) {
        val item = LaptopList[position]
        holder.render(item)
    }

    fun updateData(newData: MutableList<Laptop>) {
        LaptopList = newData
        notifyDataSetChanged()
    }

    fun clearData() {
        LaptopList.clear()
        notifyDataSetChanged()
    }

}