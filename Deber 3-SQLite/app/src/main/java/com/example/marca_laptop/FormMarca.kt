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

class FormMarca : AppCompatActivity() {
    //Parametros intent
    var opcion: String? = ""
    var idItemSeleccionado = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_marca)

        opcion = intent.getStringExtra("opcion")
        idItemSeleccionado = intent.getIntExtra("idItemSeleccionado", -1)

        //Verificar opción para presentar interfaz
        comprobarOpcion(opcion)

        //Obtener datos del layout
        val botonGuardar = findViewById<Button>(R.id.btn_añadir_marca)
        botonGuardar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.txt_nombre_marca).text.toString()
            val fechaCreacion = findViewById<EditText>(R.id.txt_fecha_creacion).text.toString()
            val servicioTecnico = findViewById<Switch>(R.id.sw_servicioTecnico).isChecked()
            val contacto = findViewById<EditText>(R.id.txt_contacto).text.toString()
            devolverRespuesta(nombre, fechaCreacion, servicioTecnico.toString(), contacto, opcion, idItemSeleccionado)
        }

    }

    fun devolverRespuesta(nombre: String, fechaCreacion: String, servicioTecnico:String, contacto:String, opcion:String?,
                          idItem: Int?){
        val intent = Intent()
        intent.putExtra("nombreMarca", nombre)
        intent.putExtra("fechaCreacionMarca", fechaCreacion)
        intent.putExtra("servicioTecnicoMarca", servicioTecnico)
        intent.putExtra("contactoMarca", contacto)
        intent.putExtra("opcion", opcion)
        intent.putExtra("idItemSeleccionado", idItem)
        setResult(
            RESULT_OK,
            intent
        )
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun comprobarOpcion(opcion: String?){
        if (opcion == "MODIFICAR"){
            val nombreMarca = intent.getStringExtra("nombreMarca")
            val marcaEditar = BaseDeDatos.tablaMarca?.consultarMarcaPorID(idItemSeleccionado)
            findViewById<TextView>(R.id.txt_form_marca).setText("Editar marca ${nombreMarca}")
            findViewById<EditText>(R.id.txt_nombre_marca).setText(marcaEditar?.nombre)
            findViewById<EditText>(R.id.txt_fecha_creacion).setText(marcaEditar?.fechaCreacion.toString())
            val sw_servicioTecnico = marcaEditar?.servicioTecnico.toString()
            if (sw_servicioTecnico.toBoolean() == true){
                findViewById<Switch>(R.id.sw_servicioTecnico).isChecked = true
            }
            findViewById<EditText>(R.id.txt_contacto).setText(marcaEditar?.contacto)
        }
    }
}