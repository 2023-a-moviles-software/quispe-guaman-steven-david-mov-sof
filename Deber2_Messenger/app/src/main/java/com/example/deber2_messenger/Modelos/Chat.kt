package com.example.deber2_messenger.Modelos

class Chat (
    var nombreUsuario: String,
    var mensaje: String,
    var fecha: String,
    var nombreImagenPerfil: String,
        ){

    companion object {
        val arregloChats = arrayListOf<Chat>()
        init {
            arregloChats
                .add(
                    Chat("Maria Perez", "¿Cómo estás?", "22 jul", "chat1")
                )
            arregloChats
                .add(
                    Chat("Pedro Paez", "Listo, sin problema", "12 jul", "chat2")
                )
            arregloChats
                .add(
                    Chat("Hernán Reyes", "Hasta luego", "10 abr", "chat3")
                )
            arregloChats
                .add(
                    Chat("Marco Rojas", "Te envío la proforma", "15 mar", "chat4")
                )
            arregloChats
                .add(
                    Chat("Laura Díaz", "Hola :D", "14 mar", "chat5")
                )
            arregloChats
                .add(
                    Chat("Jhon Narvaez", "Aquí tienes la tarea", "9 mar", "chat6")
                )
        }
    }

}