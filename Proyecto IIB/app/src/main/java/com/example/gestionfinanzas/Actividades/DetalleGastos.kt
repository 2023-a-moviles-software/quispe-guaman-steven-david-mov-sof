package com.example.gestionfinanzas.Actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.GastosAdapter
import com.example.exameniib.viewholders.GastoViewHolder
import com.example.gestionfinanzas.Modelos.Gasto
import com.example.gestionfinanzas.R
import com.google.firebase.firestore.FirebaseFirestore

class DetalleGastos : AppCompatActivity() {
    private lateinit var adapter: GastosAdapter
    private val arreglo: ArrayList<Gasto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_gastos)

        val idCuenta = intent.getStringExtra("idCuenta")

        if (idCuenta != null) {
            consultarGastsPorCuenta(idCuenta)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_lista_gastos)
        adapter = GastosAdapter(arreglo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        GastoViewHolder.setAdapter(adapter)
    }

    fun consultarGastsPorCuenta(idCuenta: String) {
        val db = FirebaseFirestore.getInstance()
        val cuentasRef = db.collection("Gasto")
            .whereEqualTo("idCuenta", idCuenta)

        cuentasRef.get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val categoria = document.getString("categoria")
                    val descripcion = document.getString("descripcion")?: ""
                    val fecha = document.getString("fecha")
                    val monto = document.getDouble("monto")

                    if (categoria != null && descripcion != null && fecha != null && monto != null) {
                        val gasto = Gasto(document.id, monto, fecha, categoria, descripcion)
                        arreglo.add(gasto)
                    } else {
                        Log.i("Gsato", "Datos incompletos")
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.i("Gasto", "Error BDD")
            }
    }
}