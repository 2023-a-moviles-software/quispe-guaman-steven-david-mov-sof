package com.example.marca_laptop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi

class FormLaptop : AppCompatActivity() {
    var opcion: String? = ""
    var idLaptopSeleccionada = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_laptop)
        //Parametros intent
        opcion = intent.getStringExtra("opcion")
        idLaptopSeleccionada = intent.getIntExtra("idLaptopSeleccionada", -1)
        //Verificar opción para presentar interfaz
        comprobarOpcion(opcion)
        //Obtener datos del layout
        val botonGuardar = findViewById<Button>(R.id.btn_añadir_laptop)
        botonGuardar.setOnClickListener {
            val modelo = findViewById<EditText>(R.id.txt_modelo).text.toString()
            val precio = findViewById<EditText>(R.id.txt_precio).text.toString()
            val fechaLanzamiento = findViewById<EditText>(R.id.txt_fecha_lanzamiento).text.toString()
            val enProduccion = findViewById<Switch>(R.id.sw_en_produccion).isChecked
            devolverRespuesta(modelo, precio, fechaLanzamiento, enProduccion.toString(), opcion)
        }
    }

    fun devolverRespuesta(modelo: String, precio: String, fechaLanzamiento:String, enProduccion:String, opcion: String?){
        val intent = Intent()
        intent.putExtra("modeloLaptop", modelo)
        intent.putExtra("precioLaptop", precio)
        intent.putExtra("fechaLanzamientoLaptop", fechaLanzamiento)
        intent.putExtra("enProduccionLaptop", enProduccion)
        intent.putExtra("opcion", opcion)
        intent.putExtra("idMarcaSeleccionada", intent.getIntExtra("idMarca", 1))
        intent.putExtra("idLaptopSeleccionada", idLaptopSeleccionada)
        setResult(
            RESULT_OK,
            intent
        )
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun comprobarOpcion(opcion: String?){
        if (opcion == "MODIFICAR"){
            val modeloLaptop = intent.getStringExtra("modeloLaptop")
            val laptopEditar = BaseDeDatos.tablaLaptop?.consultarLaptopPorID(idLaptopSeleccionada)
            findViewById<TextView>(R.id.txt_form_laptop).setText("Editar laptop ${modeloLaptop}")
            findViewById<EditText>(R.id.txt_modelo).setText(laptopEditar?.modelo)
            findViewById<EditText>(R.id.txt_precio).setText(laptopEditar?.precio.toString())
            findViewById<EditText>(R.id.txt_fecha_lanzamiento).setText(laptopEditar?.fechaLanzamiento.toString())
            val sw_enProduccion = laptopEditar?.enProduccion.toString()
            if (sw_enProduccion.toBoolean() == true){
                findViewById<Switch>(R.id.sw_en_produccion).isChecked = true
            }
        }
    }
}