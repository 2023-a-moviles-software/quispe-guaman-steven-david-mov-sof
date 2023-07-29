package com.example.deber2_messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2_messenger.Modelos.Chat
import com.example.deber2_messenger.Modelos.ChatAdaptador

class MainActivity : AppCompatActivity() {
    val chats = Chat.arregloChats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarRecyclerView()

        //Referencias al botón de historias del menú de navegación
        val ver_historias = findViewById<ImageView>(R.id.ver_historias)
        ver_historias.setOnClickListener{
            val intent = Intent(this, Historias::class.java)
            startActivity(intent)
        }
    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(
            R.id.rv_chats
        )
        val adaptador = ChatAdaptador(
            this,
            chats,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }






}