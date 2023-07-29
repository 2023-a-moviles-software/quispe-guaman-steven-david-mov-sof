package com.example.deber2_messenger.Modelos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2_messenger.Historias
import com.example.deber2_messenger.R

class HistoriaAdaptador (
    private val contexto: Historias,
    private val lista: ArrayList<Historia>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<HistoriaAdaptador.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nombreUsuario: TextView
        val nombreImagenPerfil: ImageView
        val nombreImagenHistoria: ImageView
        init {
            nombreUsuario = view.findViewById(R.id.usuario_historia)
            nombreImagenPerfil = view.findViewById(R.id.foto_perfil_historia)
            nombreImagenHistoria = view.findViewById(R.id.imagen_historia)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_chats,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historiaActual = this.lista[position]
        holder.nombreUsuario.text =  historiaActual.nombreUsuario
        //Cambio de imagen perfil
        val drawableResourceIdPerfil = holder.itemView.context.resources.getIdentifier(historiaActual.nombreImagenPerfil
            , "drawable", holder.itemView.context.packageName)
        holder.nombreImagenPerfil.setImageResource(drawableResourceIdPerfil)

        //Cambio de imagen perfil
        val drawableResourceIdHistoria = holder.itemView.context.resources.getIdentifier(historiaActual.nombreImagenHistoria
            , "drawable", holder.itemView.context.packageName)
        holder.nombreImagenPerfil.setImageResource(drawableResourceIdHistoria)



    }

}