package com.example.deber2_messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2_messenger.Modelos.Chat
import com.example.deber2_messenger.Modelos.ChatAdaptador
import com.example.deber2_messenger.Modelos.Historia
import com.example.deber2_messenger.Modelos.HistoriaAdaptador

class Historias : AppCompatActivity() {
    val historias = Historia.arregloHistorias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historias)

        inicializarRecyclerView()

        //Referencias al botón de historias del menú de navegación
        val ver_chats = findViewById<ImageView>(R.id.ver_Chats)
        ver_chats.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(
            R.id.rv_historias
        )
        val adaptador = HistoriaAdaptador(
            this,
            historias,
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