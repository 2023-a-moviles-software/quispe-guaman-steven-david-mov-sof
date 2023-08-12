package com.example.marca_laptop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.marca_laptop.BbaseDatosMemoria.Companion.arregloBLaptop
import java.time.LocalDate

class LaptopListView : AppCompatActivity() {
    companion object{
        lateinit var adaptador: ArrayAdapter<BLaptop>
    }

    //Variables
    var idItemSeleccionado = -1
    var laptopPorMarca = ArrayList<BLaptop>()
    var idLaptopSeleccionada: Int? = -1
    var idMarcaSeleccionada = -1

    @RequiresApi(Build.VERSION_CODES.O)
    var arreglo = BaseDeDatos.tablaLaptop?.obtenerTodasLasLaptops()?: emptyList()

    @RequiresApi(Build.VERSION_CODES.O)
    val callbackContenidoIntentExlicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if( result.data != null){
                    val data = result.data
                    //Obtencion datos creados o modificados
                    val modelo = data?.getStringExtra("modeloLaptop")
                    val precio = data?.getStringExtra("precioLaptop")
                    val fechaLanzamiento = data?.getStringExtra("fechaLanzamientoLaptop")
                    val enProduccion = data?.getStringExtra("enProduccionLaptop")
                    val opcion = data?.getStringExtra("opcion")
                    val idMarca = data?.getStringExtra("idMarcaSeleccionada")
                    idLaptopSeleccionada = data?.getIntExtra("idLaptopSeleccionada",-1)
                    if(modelo != null && precio != null && fechaLanzamiento != null && enProduccion !=null){
                        if(opcion != "MODIFICAR"){
                                BaseDeDatos.tablaLaptop?.crearLaptop(modelo,idMarcaSeleccionada,precio.toDouble(),
                                            fechaLanzamiento, enProduccion)

                           laptopPorMarca = obtenerLaptopPorMarca(idMarcaSeleccionada) //Actualiza las laptops de esa marca
                        }else {
                            BaseDeDatos.tablaLaptop?.actualizarLaptop(modelo,precio.toDouble(),
                                fechaLanzamiento, enProduccion, idLaptopSeleccionada!!)
                        }
                        //Actualización de la lista en pantalla
                        adaptador = findViewById<ListView>(R.id.lv_list_laptop).adapter as ArrayAdapter<BLaptop>
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laptop_list_view)
        //Parametros del intent
        idMarcaSeleccionada = intent.getIntExtra("idMarca", 1);
        //Crear primeras laptops
        if (arreglo.isEmpty()){
            BaseDeDatos.tablaLaptop?.crearLaptop("VivoBook",1,750.12, "2018-12-01", "true")
            BaseDeDatos.tablaLaptop?.crearLaptop("ROG G15",1,1200.99, "2020-01-01", "true")
            BaseDeDatos.tablaLaptop?.crearLaptop("Thin GF63",2,1100.99, "2021-01-01", "true")
            BaseDeDatos.tablaLaptop?.crearLaptop("Thin GF65",2,1300.99, "2022-01-01", "true")
        }
        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_list_laptop)
        adaptador = ArrayAdapter(
            this,   //Contexto
            android.R.layout.simple_list_item_1,
            obtenerLaptopPorMarca(idMarcaSeleccionada)
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        //Referencia al texto de nombre la marca
        val txtNombreMarca = findViewById<TextView>(R.id.txt_nombre_laptop)
        txtNombreMarca
            .setText(obtenerNombreMarca(idMarcaSeleccionada))
        //Referencia al botón para añadir
        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_laptop)
        botonAnadirListView.setOnClickListener {
            anadirLaptop(FormLaptop::class.java)
        }
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenar opciones de menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_laptop, menu)
        //Obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id =  info.position
        idItemSeleccionado = id
        idLaptopSeleccionada = laptopPorMarca[idItemSeleccionado].idLaptop-1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_laptop->{
                editarLaptop(FormLaptop::class.java)
                return true
            }
            R.id.mi_eliminar_laptop->{
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    //Obtener nombre marca
    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerNombreMarca(idMarca: Int): String{
        return BaseDeDatos.tablaMarca?.consultarNombreMarcaPorID(idMarca)?: ""
    }
    //Clasificar laptop por id
    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerLaptopPorMarca(idMarca: Int): ArrayList<BLaptop> {
        laptopPorMarca.clear()
        laptopPorMarca = BaseDeDatos.tablaLaptop?.consultarLaptopPorIDMarca(idMarca) as ArrayList<BLaptop>
        return laptopPorMarca
    }


    //Dialogo para confirmar eliminacion
    @RequiresApi(Build.VERSION_CODES.O)
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { //Callback
                    dialog, which ->
                //Eliminar marca seleccionada
                BaseDeDatos.tablaLaptop?.eliminarLaptop(idLaptopSeleccionada!!)
                laptopPorMarca.removeAt(idItemSeleccionado)
                //Actualización de la lista en pantalla
                val adaptador = findViewById<ListView>(R.id.lv_list_laptop).adapter as ArrayAdapter<BLaptop>
                adaptador.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    //Función para añadir elementos al arreglo y mostrarlos en pantalla
    @RequiresApi(Build.VERSION_CODES.O)
    fun anadirLaptop(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("opcion","CREAR")  //Operacion CRUD a realizar
        //Enviar parametros
        callbackContenidoIntentExlicito.launch(intentExplicito)
    }

    //Función para editar elementos al arreglo y mostrarlos en pantalla
    @RequiresApi(Build.VERSION_CODES.O)
    fun editarLaptop(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("opcion","MODIFICAR")  //Operacion CRUD a realizar
        intentExplicito.putExtra("modeloLaptop", BaseDeDatos.tablaLaptop?.consultarLaptopPorID(idLaptopSeleccionada!!)?.modelo)
        intentExplicito.putExtra("idLaptopSeleccionada", idLaptopSeleccionada)
        intentExplicito.putExtra("idMarca", intent.getIntExtra("idMarca", 1) )
        //Enviar parametros
        callbackContenidoIntentExlicito.launch(intentExplicito)
    }
}