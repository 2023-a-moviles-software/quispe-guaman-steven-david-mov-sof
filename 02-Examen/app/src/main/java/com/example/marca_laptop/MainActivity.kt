package com.example.marca_laptop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Boton ver lista de marcas
        val botonVerMarcas = findViewById<Button>(R.id.btn_ver_marca)
        botonVerMarcas
            .setOnClickListener{
                irActividad(MarcaListView::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }


}