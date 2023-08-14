package com.example.marca_laptop

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.time.LocalDate

class MarcaListView : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var arreglo = BaseDeDatos.tablaMarca?.obtenerTodasLasMarcas()?: emptyList()
    var idItemSeleccionado = -1

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
                    val nombre = data?.getStringExtra("nombreMarca")
                    val fechaCreacion = data?.getStringExtra("fechaCreacionMarca")
                    val servicioTecnico = data?.getStringExtra("servicioTecnicoMarca")
                    val contacto = data?.getStringExtra("contactoMarca")
                    val opcion = data?.getStringExtra("opcion")

                    if(nombre != null && fechaCreacion != null && servicioTecnico != null && contacto !=null){
                        if(opcion != "MODIFICAR"){
                            BaseDeDatos.tablaMarca?.crearMarca(nombre, fechaCreacion, servicioTecnico , contacto)
                        }else {
                            BaseDeDatos.tablaMarca?.actualizarMarca(nombre, fechaCreacion, servicioTecnico, contacto, idItemSeleccionado)
                        }
                        //Actualización de la lista en pantalla
                        val listViewMarca = findViewById<ListView>(R.id.lv_list_marca)
                        val adaptador = listViewMarca.adapter as ArrayAdapter<BMarca>
                        adaptador.clear()
                        val marcasActualizadas = BaseDeDatos.tablaMarca?.obtenerTodasLasMarcas()
                        if (marcasActualizadas != null){
                            adaptador.addAll(marcasActualizadas)
                        }
                        adaptador.notifyDataSetChanged()
                    }
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marca_list_view)
        //Crear primeras marcas
        if (arreglo.isEmpty()){
            BaseDeDatos.tablaMarca?.crearMarca("Asus", "1989-12-05", "true", "asus@contacto.com")
            BaseDeDatos.tablaMarca?.crearMarca("Msi", "1995-08-11", "true", "msi@contacto.com")
        }
        //Adaptador
        val listView = findViewById<ListView>(R.id.lv_list_marca)
        val adaptador = ArrayAdapter(
            this,   //Contexto
            android.R.layout.simple_list_item_1,
            BaseDeDatos.tablaMarca?.obtenerTodasLasMarcas()?: emptyList()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        //Referencia al botón para añadir
        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_marca)
        botonAnadirListView.setOnClickListener {
            anadirMarca(FormMarca::class.java)
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
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id =  info.position
        idItemSeleccionado = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar->{
                editarMarca(FormMarca::class.java)
                return true
            }
            R.id.mi_eliminar->{
                abrirDialogo()
                return true
            }
            R.id.mi_ver_laptops->{
                listarLaptops(LaptopListView::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
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
                BaseDeDatos.tablaMarca?.eliminarMarca(idItemSeleccionado)
                //Actualización de la lista en pantalla
                val adaptador = findViewById<ListView>(R.id.lv_list_marca).adapter as ArrayAdapter<BMarca>
                adaptador.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    //Función para añadir elementos al arreglo y mostrarlos en pantalla
    @RequiresApi(Build.VERSION_CODES.O)
    fun anadirMarca(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("opcion","CREAR")  //Operacion CRUD a realizar
        //Enviar parametros
        callbackContenidoIntentExlicito.launch(intentExplicito)
    }

    //Función para editar elementos al arreglo y mostrarlos en pantalla
    @RequiresApi(Build.VERSION_CODES.O)
    fun editarMarca(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("opcion","MODIFICAR")  //Operacion CRUD a realizar
        intentExplicito.putExtra("nombreMarca",BaseDeDatos.tablaMarca?.consultarMarcaPorID(idItemSeleccionado)?.nombre) //Nombre Marca a editar
        intentExplicito.putExtra("idItemSeleccionado",idItemSeleccionado.toInt()) //Id Marca a editar
        //Enviar parametros
        callbackContenidoIntentExlicito.launch(intentExplicito)
    }

    //Función para mostrar la lista de laptops por marca
    @RequiresApi(Build.VERSION_CODES.O)
    fun listarLaptops(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        //Enviar parametros
        intent.putExtra("idMarca", BaseDeDatos.tablaMarca?.consultarMarcaPorID(idItemSeleccionado)?.idMarca)
        startActivity(intent)
    }
}