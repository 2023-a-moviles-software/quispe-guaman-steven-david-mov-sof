package com.example.deber2_messenger.Modelos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2_messenger.MainActivity
import com.example.deber2_messenger.R

class ChatAdaptador (
    private val contexto: MainActivity,
    private val lista: ArrayList<Chat>,
    private val recyclerView: RecyclerView
        ): RecyclerView.Adapter<ChatAdaptador.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nombreChat: TextView
        val mensajeChat: TextView
        val fotoChat: ImageView
        init {
            nombreChat = view.findViewById(R.id.nombre_chat)
            mensajeChat = view.findViewById(R.id.mensaje_chat)
            fotoChat = view.findViewById(R.id.imagen_chat)
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
        val chatActual = this.lista[position]
        holder.nombreChat.text = chatActual.nombreUsuario
        holder.mensajeChat.text = chatActual.mensaje + chatActual.fecha

        //Cambio de imagen
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(chatActual.nombreImagenPerfil
            , "drawable", holder.itemView.context.packageName)
        holder.fotoChat.setImageResource(drawableResourceId)
        //Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.imagenPinterest)
    }

}