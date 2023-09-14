package com.example.exameniib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.LaptopAdapter
import com.example.exameniib.models.Laptop
import com.example.exameniib.viewholders.LaptopViewHolder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LaptopList : AppCompatActivity() {
    private lateinit var adapter: LaptopAdapter
    private val arreglo: ArrayList<Laptop> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)

        val nombreMarca = intent.getStringExtra("nombre")
        val idMarcaStr = intent.getStringExtra("idAProducto")

        consultarLaptopsPorMarca(idMarcaStr)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_lista_productos)
        adapter = LaptopAdapter(arreglo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        LaptopViewHolder.setAdapter(adapter)

        val tb = findViewById<TextView>(R.id.txttest)
        tb.setText(nombreMarca)

        val btnCrearProducto = findViewById<Button>(R.id.btn_crear_nuevo_producto)


        btnCrearProducto.setOnClickListener {
            val intent = Intent(this, CrearLaptop::class.java)
            intent.putExtra("idMarcaStr", idMarcaStr)
            startActivity(intent)

        }
    }

    private fun consultarLaptopsPorMarca(marcaId: String?) {
        val db = Firebase.firestore
        val laptopsRef = db.collection("laptop")
            .whereEqualTo("idMarca", marcaId)

        laptopsRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val laptopId = document.id
                    val modelo = document.getString("modelo")
                    val idMarca = document.getString("idMarca")
                    val precio = document.getString("precio")
                    val fechaLanzamiento = document.getString("fechaLanzamiento")
                    val enProduccion = document.getString("enProduccion")

                    val nuevaLaptop =
                        Laptop(laptopId, modelo, idMarca, precio, fechaLanzamiento, enProduccion)
                    arreglo.add(nuevaLaptop)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Captura y registra la excepción en el logcat
                Log.e("consultarLaptopsPorMarca", "Error al obtener datos: ${e.message}", e)
            }
    }


}