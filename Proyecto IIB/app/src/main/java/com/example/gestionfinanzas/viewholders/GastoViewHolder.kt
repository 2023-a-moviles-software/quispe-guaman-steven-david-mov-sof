package com.example.exameniib.viewholders

import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.adapters.GastosAdapter
import com.example.gestionfinanzas.Modelos.Gasto
import com.example.gestionfinanzas.R
import com.google.firebase.firestore.FirebaseFirestore

class GastoViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    private var categoria = view.findViewById<TextView>(R.id.txt_categoria_item)
    private var descripcion = view.findViewById<TextView>(R.id.txt_descripcion_item)
    private var fecha = view.findViewById<TextView>(R.id.txt_fecha_item)
    private var monto =  view.findViewById<TextView>(R.id.txt_monto_item)
    private var arreglo: ArrayList<Gasto> = arrayListOf()
    init {
        view.setOnCreateContextMenuListener(this)
    }

    companion object {
        private lateinit var adapter: GastosAdapter


        fun setAdapter(electroAdapter: GastosAdapter) {
            adapter = electroAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    fun render(gasto: Gasto) {
        categoria.text = gasto.categoria
        descripcion.text = "Descripción: ${gasto.descripcion}"
        fecha.text = "Fecha: ${gasto.fecha}"
        monto.text = "$ ${gasto.monto}"
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
                        val gasto = Gasto(idCuenta, monto, fecha, categoria, descripcion)
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