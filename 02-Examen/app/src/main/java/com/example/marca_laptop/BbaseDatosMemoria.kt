package com.example.marca_laptop

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class BbaseDatosMemoria {
    @RequiresApi(Build.VERSION_CODES.O)
    companion object{
        val arregloBMarca = arrayListOf<BMarca>()
        val arregloBLaptop = arrayListOf<BLaptop>()

        init {
            //Datos iniciales Marcas
            arregloBMarca
                .add(
                    BMarca(1,"Asus", LocalDate.parse("1989-12-05"), true,
                    "asus@contacto.com")
                )
            arregloBMarca
                .add(
                    BMarca(2,"Msi", LocalDate.parse("1995-08-11"), true,
                        "msi@contacto.com")
                )
            //Datos iniciales Laptop
            arregloBLaptop
                .add(
                    BLaptop(1,"VivoBook",1,750.12,
                        LocalDate.parse("2018-12-01"), true)
                )
            arregloBLaptop
                .add(
                    BLaptop(2,"ROG G15",1,1200.99,
                        LocalDate.parse("2020-01-01"), true)
                )
            arregloBLaptop
                .add(
                    BLaptop(3,"Thin GF63",2,1100.99,
                        LocalDate.parse("2021-01-01"), true)
                )
            arregloBLaptop
                .add(
                    BLaptop(4,"Thin GF65",2,1300.99,
                        LocalDate.parse("2022-01-01"), true)
                )
        }

    }
}