package com.example.exameniib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.exameniib.models.Laptop
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearLaptop : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_laptop)

        val marcaID = intent.getStringExtra("idMarcaStr")

        val txtModelo = findViewById<EditText>(R.id.txt_modelo)
        val txtPrecio = findViewById<EditText>(R.id.txt_precio)
        val txtFechaLanzamiento = findViewById<EditText>(R.id.txt_fecha_lanzamiento)
        val txtEnProduccion = findViewById<Switch>(R.id.sw_en_produccion)
        val btnCrearLaptop = findViewById<Button>(R.id.btn_crear_laptop)

        btnCrearLaptop.setOnClickListener {

            val modelo = txtModelo.text.toString()
            val precio = txtPrecio.text.toString()
            val fechaLanzamiento = txtFechaLanzamiento.text.toString()
            var enProduccion = "false"
            if (txtEnProduccion.isChecked){
                enProduccion = "true"
            }

            crearLaptop(modelo, precio, fechaLanzamiento, enProduccion, marcaID)

            val intent = Intent(this, LaptopList::class.java)
            intent.putExtra("idAProducto", marcaID)
            startActivity(intent)
        }
    }

    private fun crearLaptop(
        modelo: String?,
        precio: String?,
        fechaLanzamiento: String?,
        enProduccion: String?,
        marcAID: String?
    ) {
        val nuevaLaptop = Laptop(
            idLaptop = null,
            modelo = modelo,
            idMarca = marcAID,
            precio = precio,
            fechaLanzamiento = fechaLanzamiento,
            enProduccion = enProduccion
        )

        val db = Firebase.firestore

        db.collection("laptop")
            .add(nuevaLaptop)
            .addOnSuccessListener { }
            .addOnFailureListener { }

    }
}