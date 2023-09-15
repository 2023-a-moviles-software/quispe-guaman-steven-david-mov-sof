package com.example.gestionfinanzas.Actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.IngresosAdapter
import com.example.exameniib.viewholders.IngresoViewHolder
import com.example.gestionfinanzas.Modelos.Ingreso
import com.example.gestionfinanzas.R
import com.google.firebase.firestore.FirebaseFirestore

class DetalleIngresos : AppCompatActivity() {
    private lateinit var adapter: IngresosAdapter
    private val arreglo: ArrayList<Ingreso> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_ingresos)

        val idCuenta = intent.getStringExtra("idCuenta")

        if (idCuenta != null) {
            consultarIngresosPorCuenta(idCuenta)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_lista_ingresos)
        adapter = IngresosAdapter(arreglo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        IngresoViewHolder.setAdapter(adapter)
    }

    fun consultarIngresosPorCuenta(idCuenta: String) {
        val db = FirebaseFirestore.getInstance()
        val cuentasRef = db.collection("Ingreso")
            .whereEqualTo("idCuenta", idCuenta)

        cuentasRef.get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val categoria = document.getString("categoria")
                    val descripcion = document.getString("descripcion")?: ""
                    val fecha = document.getString("fecha")
                    val monto = document.getDouble("monto")

                    if (categoria != null && descripcion != null && fecha != null && monto != null) {
                        val ingreso = Ingreso(idCuenta, monto, fecha, categoria, descripcion)
                        arreglo.add(ingreso)
                    } else {
                        Log.i("Ingreso", "Datos incompletos")
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.i("Ingreso", "Error BDD")
            }
    }
}