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
import com.example.exameniib.ActualizarLaptop
import com.example.exameniib.R
import com.example.exameniib.adapters.LaptopAdapter
import com.example.exameniib.models.Laptop
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LaptopViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    val txtmodeloLaptop = view.findViewById<TextView>(R.id.txt_modelo_laptop)
    val txtPrecioLaptop = view.findViewById<TextView>(R.id.txt_precio_laptop)
    val txtFechaLanzamiento = view.findViewById<TextView>(R.id.txt_fechaLanzamiento)
    val txtEnProduccion = view.findViewById<TextView>(R.id.txt_enProduccion)
    private var currentLaptop: Laptop? = null
    private val arreglo: ArrayList<Laptop> = arrayListOf()
    init {
        view.setOnCreateContextMenuListener(this)
    }

    companion object {
        private lateinit var adapter: LaptopAdapter


        fun setAdapter(laptopAdapter: LaptopAdapter) {
            adapter = laptopAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    fun render(laptop: Laptop) {
        currentLaptop = laptop
        txtmodeloLaptop.text = laptop.modelo
        txtPrecioLaptop.text = "Precio: $ ${laptop.precio}"
        txtFechaLanzamiento.text = laptop.fechaLanzamiento
        txtEnProduccion.text = if (laptop.enProduccion.toBoolean()) "En producción: Si" else "En producción: No"
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = MenuInflater(v?.context)
        inflater.inflate(R.menu.menu_laptop, menu)

        if (menu != null) {
            for (i in 0 until menu.size()) {
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_laptop_tool -> {
                val idAux = currentLaptop?.idLaptop
                val idMarca = currentLaptop?.idMarca
                val intent = Intent(itemView.context, ActualizarLaptop::class.java)
                intent.putExtra("idLaptop", idAux)
                intent.putExtra("idMarca", idMarca)
                itemView.context.startActivity(intent)
                true
            }

            R.id.eliminar_laptop_tool -> {
                val laptopID = currentLaptop?.idLaptop
                consultarLaptopsPorMarca(currentLaptop?.idMarca)
                eliminarLaptopEnFirestore(laptopID)
                adapter.updateData(arreglo)
                true
            }

            else -> false
        }
    }

    private fun eliminarLaptopEnFirestore(laptopID: String?) {
        if (laptopID != null) {
            val db = Firebase.firestore
            db.collection("laptop")
                .document(laptopID)
                .delete()
                .addOnSuccessListener {

                }
                .addOnFailureListener { }
        }
    }

    private fun consultarLaptopsPorMarca(marcaId: String?) {
        val db = Firebase.firestore
        val laptopsRef = db.collection("laptop")
            .whereEqualTo("idMarca", marcaId)

        laptopsRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val laptopId = document.getString("idLaptop")
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