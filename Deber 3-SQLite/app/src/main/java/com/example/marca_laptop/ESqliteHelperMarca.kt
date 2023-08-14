package com.example.marca_laptop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class ESqliteHelperMarca (
    contexto: Context?, // this
): SQLiteOpenHelper(
    contexto,
    "sqlite", // nombre bdd
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaMarca =
            """
                CREATE TABLE IF NOT EXISTS MARCA(
                    idMarca INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    fechaCreacion VARCHAR(10),
                    servicioTecnico VARCHAR(5),
                    contacto VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaMarca)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}

    fun crearMarca(
        nombre: String,
        fechaCreacion: String,
        servicioTecnico: String,
        contacto: String
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaCreacion", fechaCreacion)
        valoresAGuardar.put("servicioTecnico", servicioTecnico)
        valoresAGuardar.put("contacto", contacto)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "MARCA", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarMarca(idMarca:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( idMarca.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "MARCA", // Nombre tabla
                "idMarca=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarMarca(
        nombre: String,
        fechaCreacion: String,
        servicioTecnico: String,
        contacto: String,
        idMarca:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaCreacion", fechaCreacion)
        valoresAActualizar.put("servicioTecnico", servicioTecnico)
        valoresAActualizar.put("contacto", contacto)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( idMarca.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "MARCA", // Nombre tabla
                valoresAActualizar, // Valores
                "idMarca=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarMarcaPorID(idMarca: Int): BMarca {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM MARCA WHERE idMarca = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idMarca.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeMarca = resultadoConsultaLectura.moveToFirst()
        val MarcaEncontrada = BMarca(0,"--", LocalDate.parse("2023-12-12"), false,
            "----")
        val arreglo = arrayListOf<BMarca>()
        if(existeMarca){
            do{
                val idMarca = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaCreacion = resultadoConsultaLectura.getString(2)
                val servicioTecnico = resultadoConsultaLectura.getString(3)
                val contacto = resultadoConsultaLectura.getString(4)
                if(idMarca != null){
                    // llenar el arreglo con una nuva marca
                    MarcaEncontrada.idMarca = idMarca
                    MarcaEncontrada.nombre = nombre
                    MarcaEncontrada.fechaCreacion = LocalDate.parse(fechaCreacion)
                    MarcaEncontrada.servicioTecnico = servicioTecnico.toBoolean()
                    MarcaEncontrada.contacto = contacto
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return MarcaEncontrada
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarNombreMarcaPorID(idMarca: Int): String{
        var nombre = ""
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT nombre FROM MARCA WHERE idMarca = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idMarca.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeMarca = resultadoConsultaLectura.moveToFirst()
        if(existeMarca){
            do{
                nombre = resultadoConsultaLectura.getString(0)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return nombre
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerTodasLasMarcas(): List<BMarca> {
        val registros = mutableListOf<BMarca>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM MARCA 
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null, // Parametros
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do{
                val idMarca = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaCreacion = resultadoConsultaLectura.getString(2)
                val servicioTecnico = resultadoConsultaLectura.getString(3)
                val contacto = resultadoConsultaLectura.getString(4)
                registros.add(BMarca(idMarca, nombre, LocalDate.parse(fechaCreacion), servicioTecnico.toBoolean(), contacto))
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return registros
    }

}