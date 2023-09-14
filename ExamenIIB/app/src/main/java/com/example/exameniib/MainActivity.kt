package com.example.exameniib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.MarcaAdapter
import com.example.exameniib.models.Marca
import com.example.exameniib.viewholders.MarcaViewHolder
import com.google.android.play.core.integrity.e
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MarcaAdapter
    private val arreglo: ArrayList<Marca> = arrayListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adapter.clearData()

        consultarMarcas()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_marcas)
        adapter = MarcaAdapter(arreglo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        MarcaViewHolder.setAdapter(adapter)



        val botonCrearMarca = findViewById<Button>(R.id.btn_agregar_marca)

        botonCrearMarca.setOnClickListener {
            val intent = Intent(this, CrearMarca::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    private fun consultarMarcas() {
        val db = Firebase.firestore
        val almacenRef = db.collection("marca")

        almacenRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val marcaId = document.id
                    val nombre = document.getString("nombre")
                    val fechaCreacion = document.getString("fechaCreacion")
                    val servicioTecnico = document.getString("servicioTecnico")
                    val contacto = document.getString("contacto")

                    val nuevaMarca =
                        Marca(marcaId, nombre, fechaCreacion, servicioTecnico, contacto)
                    arreglo.add(nuevaMarca)
                    nuevaMarca.nombre?.let { Log.i("Test", it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error al obtener datos: " );
            }
    }
}