package com.example.marca_laptop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class ESqliteHelperLaptop (
    contexto: Context?, // this
): SQLiteOpenHelper(
    contexto,
    "sqlite", // nombre bdd
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaLaptop =
            """
                CREATE TABLE LAPTOP(
                    idLaptop INTEGER PRIMARY KEY AUTOINCREMENT,
                    modelo VARCHAR(50),
                    idMarca INTEGER,
                    precio REAL, 
                    fechaLanzamiento VARCHAR(10),
                    enProduccion VARCHAR(5)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaLaptop)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}

    fun crearLaptop(
        modelo: String,
        idMarca: Int,
        precio: Double,
        fechaLanzamiento: String,
        enProduccion: String
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("modelo", modelo)
        valoresAGuardar.put("idMarca", idMarca)
        valoresAGuardar.put("precio", precio)
        valoresAGuardar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAGuardar.put("enProduccion", enProduccion)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "LAPTOP", // nombre tabla
                null,
                valoresAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarLaptop(idLaptop:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( idLaptop.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "LAPTOP", // Nombre tabla
                "idLaptop=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarLaptop(
        modelo: String,
        precio: Double,
        fechaLanzamiento: String,
        enProduccion: String,
        idLaptop:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("modelo", modelo)
        valoresAActualizar.put("precio", precio)
        valoresAActualizar.put("fechaLanzamiento", fechaLanzamiento)
        valoresAActualizar.put("enProduccion", enProduccion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( idLaptop.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "LAPTOP", // Nombre tabla
                valoresAActualizar, // Valores
                "idLaptop=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarLaptopPorIDMarca(idMarca: Int): List<BLaptop> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LAPTOP WHERE idMarca = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idMarca.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeLaptop = resultadoConsultaLectura.moveToFirst()
        val LaptopEncontrada =   BLaptop(0,"---",0,0.0,
            LocalDate.parse("2023-12-12"), false)
        val arreglo = mutableListOf<BLaptop>()
        if(existeLaptop){
            do{
                val idLaptop = resultadoConsultaLectura.getInt(0) // Indice 0
                val modelo = resultadoConsultaLectura.getString(1)
                val idMarca = resultadoConsultaLectura.getInt(2)
                val precio = resultadoConsultaLectura.getString(3)
                val fechaLanzamiento = resultadoConsultaLectura.getString(4)
                val enProduccion = resultadoConsultaLectura.getString(5)
                if(idLaptop != null){
                    // llenar el arreglo con una nuva marca
                    LaptopEncontrada.idLaptop = idLaptop
                    LaptopEncontrada.modelo = modelo
                    LaptopEncontrada.idMarca = idMarca
                    LaptopEncontrada.precio = precio.toDouble()
                    LaptopEncontrada.fechaLanzamiento = LocalDate.parse(fechaLanzamiento)
                    LaptopEncontrada.enProduccion = enProduccion.toBoolean()
                    arreglo.add(LaptopEncontrada)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarLaptopPorID(idLaptop: Int): BLaptop? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LAPTOP WHERE idLaptop = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idLaptop.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        var  laptopEncontrada: BLaptop? = null
        val existeLaptop = resultadoConsultaLectura.moveToFirst()
        if(existeLaptop){
            do{
                val idLaptop = resultadoConsultaLectura.getInt(0) // Indice 0
                val modelo = resultadoConsultaLectura.getString(1)
                val idMarca = resultadoConsultaLectura.getInt(2)
                val precio = resultadoConsultaLectura.getString(3)
                val fechaLanzamiento = resultadoConsultaLectura.getString(4)
                val enProduccion = resultadoConsultaLectura.getString(5)
                if(idLaptop != null){
                    laptopEncontrada = BLaptop(idLaptop, modelo, idMarca, precio.toDouble(), LocalDate.parse(fechaLanzamiento), enProduccion.toBoolean() )
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return laptopEncontrada
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerTodasLasLaptops(): List<BLaptop> {
        val registros = mutableListOf<BLaptop>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LAPTOP
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null, // Parametros
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do{
                val idLaptop = resultadoConsultaLectura.getInt(0) // Indice 0
                val modelo = resultadoConsultaLectura.getString(1)
                val idMarca = resultadoConsultaLectura.getInt(2)
                val precio = resultadoConsultaLectura.getString(3)
                val fechaLanzamiento = resultadoConsultaLectura.getString(4)
                val enProduccion = resultadoConsultaLectura.getString(5)
                registros.add(BLaptop(2,"ROG G15",1,1200.99,
                    LocalDate.parse("2020-01-01"), true))
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return registros
    }


}