package com.example.exameniib.viewholders

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.IngresosAdapter
import com.example.gestionfinanzas.Modelos.Cuenta
import com.example.gestionfinanzas.Modelos.Ingreso
import com.example.gestionfinanzas.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IngresoViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    private var categoria = view.findViewById<TextView>(R.id.txt_categoria_item)
    private var descripcion = view.findViewById<TextView>(R.id.txt_descripcion_item)
    private var fecha = view.findViewById<TextView>(R.id.txt_fecha_item)
    private var monto =  view.findViewById<TextView>(R.id.txt_monto_item)
    private var arreglo: ArrayList<Ingreso> = arrayListOf()

    init {
        view.setOnCreateContextMenuListener(this)
    }

    companion object {
        private lateinit var adapter: IngresosAdapter


        fun setAdapter(almacenAdapter: IngresosAdapter) {
            adapter = almacenAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    fun render(ingreso: Ingreso) {
        categoria.text = ingreso.categoria
        descripcion.text = "Descripción: ${ingreso.descripcion}"
        fecha.text = "Fecha: ${ingreso.fecha}"
        monto.text = "$ ${ingreso.monto}"

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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}

