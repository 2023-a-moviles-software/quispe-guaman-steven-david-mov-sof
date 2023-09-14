package com.example.exameniib.viewholders

import android.annotation.SuppressLint
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exameniib.ActualizarMarca
import com.example.exameniib.LaptopList
import com.example.exameniib.R
import com.example.exameniib.adapters.MarcaAdapter
import com.example.exameniib.models.Marca
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MarcaViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

    private val nombreMarca = view.findViewById<TextView>(R.id.txt_name)
    private val fechaCreacion = view.findViewById<TextView>(R.id.txt_fechaCreacion)
    private val servicioTecnico = view.findViewById<TextView>(R.id.txt_servicioTecnico)
    private val contacto = view.findViewById<TextView>(R.id.txt_contactoMarca)
    private var currentMarca: Marca? = null
    private val arreglo: ArrayList<Marca> = arrayListOf()


    init {
        view.setOnCreateContextMenuListener(this)
    }

    companion object {
        private lateinit var adapter: MarcaAdapter


        fun setAdapter(marcaAdapter: MarcaAdapter) {
            adapter = marcaAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    fun render(marca: Marca) {
        currentMarca = marca
        nombreMarca.text = marca.nombre
        fechaCreacion.text = marca.fechaCreacion
        servicioTecnico.text = if (marca.servicioTecnico.toBoolean()) "Servicio Técnico: Si" else "Servicio Técnico: No"
        contacto.text = marca.contacto
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = MenuInflater(v?.context)
        inflater.inflate(R.menu.menu_tools, menu)

        if (menu != null) {
            for (i in 0 until menu.size()) {
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }
    }


    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ver_laptops -> {
                 val idAux = currentMarca?.idMarca ?:""

                 val nombreMarca = nombreMarca.text
                 val intent = Intent(itemView.context, LaptopList::class.java)
                 intent.putExtra("idAProducto", idAux)
                 intent.putExtra("nombre", nombreMarca)
                 itemView.context.startActivity(intent)
                true
            }

            R.id.editar_marca -> {
                val idAux = currentMarca?.idMarca ?:""
                  val intent = Intent(itemView.context, ActualizarMarca::class.java)
                intent.putExtra("idAProducto", idAux)
                itemView.context.startActivity(intent)
                true
            }

            R.id.eliminar_marca -> {
                currentMarca?.let { marca ->
                    val marcaID = marca.idMarca
                    eliminarMarcaEnFirestore(marcaID)
                    adapter.clearData()
                    consultarMarcas()
                    adapter.updateData(arreglo)
                }
                true
            }

            else -> false
        }
    }

    private fun eliminarMarcaEnFirestore(marcaID: String?) {
        if (marcaID != null) {
            val db = Firebase.firestore
            db.collection("marca")
                .document(marcaID)
                .delete()
                .addOnSuccessListener {

                }
                .addOnFailureListener { }
        }
    }

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

                    val nuevoMarca = Marca(marcaId, nombre, fechaCreacion, servicioTecnico, contacto )
                    arreglo.add(nuevoMarca)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }
}

