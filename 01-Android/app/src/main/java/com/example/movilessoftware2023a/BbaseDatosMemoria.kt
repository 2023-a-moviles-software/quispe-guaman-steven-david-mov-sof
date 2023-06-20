package com.example.movilessoftware2023a

class BbaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Steven", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "David", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Carolina", "c@c.com")
                )
        }
    }
}