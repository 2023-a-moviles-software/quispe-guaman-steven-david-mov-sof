package com.example.deber2_messenger.Modelos

class Historia (
    var nombreUsuario: String,
    var nombreImagenPerfil: String,
    var nombreImagenHistoria: String,
        ) {
    companion object {
        val arregloHistorias = arrayListOf<Historia>()
        init {
            arregloHistorias
                .add(
                    Historia("Maria Perez",  "chat1", "historia1")
                )
            arregloHistorias
                .add(
                    Historia("Laura Díaz",  "chat5", "historia2")
                )
            arregloHistorias
                .add(
                    Historia("Pedro Paez",  "chat2", "historia3")
                )
            arregloHistorias
                .add(
                    Historia("Marco Rojas",  "chat4", "historia4")
                )

        }
    }

}