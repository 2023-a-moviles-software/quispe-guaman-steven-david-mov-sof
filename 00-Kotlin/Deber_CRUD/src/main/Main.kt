import org.gradle.internal.component.external.model.ComponentVariant.File
import org.gradle.internal.impldep.org.joda.time.LocalDate

fun main(args: Array<String>){
    //Variables
    val marcasArchivo = File("MarcasRegistradas.txt")
    val laptopsArchivo = File("LaptopsRegistradas.txt")

    var marcas: MutableList<Marca> = mutableListOf<Marca>()
    var laptops: MutableList<Laptop> = mutableListOf<Laptop>()

    //Creacion de archivos
    if(!marcasArchivo.exists()){
        marcasArchivo.createNewFile()
    }
    if(!laptopsArchivo.exists()){
        laptopsArchivo.createNewFile()
    }

    //Cargar datos desde archivos
    if(marcasArchivo.exists()){
        marcas = mutableListOf<Marca>()
        marcasArchivo.readLines().forEach{line->
            val datos = line.split{","}
            var marca = Marca(datos[0].toInt[], datos[1], LocalDate.parse(datos[2]), datos[3].toBoolean(), datos[4])
            marcas.add(marca)
        }
    }

    if(laptopsArchivo.exists()){
        laptops = mutableListOf<Laptop>()
        laptopsArchivo.readLines().forEach{line->
            val datos = line.split{","}
            var laptop = Laptop(datos[0].toInt[], datos[1], datos[2], datos[3].toDouble(),LocalDate.parse(datos[4]),
                datos[5].toBoolean())
            laptops.add(laptop)
        }
    }

    //Actualziar registros
    fun actualizarMarcas(){
        marcasArchivo.delete()
        marcasArchivo.createNewFile()
        marcas.forEach{marca: Marca ->
            marca.almacenarDatosMarca(marcasArchivo)
        }
    }

    fun actualizarLaptops(){
        laptopsArchivo.delete()
        laptopsArchivo.createNewFile()
        laptops.forEach{laptop: Laptop ->
            laptop.almacenarDatosLaptop(laptopsArchivo)
        }
    }

    //CREATE MARCA
    fun crearMarca(){
        println("\n******* Formulario de registro de marca *******")
        print("1. Nombre de la marca: ")
        var nombre = readLine()
        print("2. Fecha de creación (aaaa-mm-dd): ")
        var fechaC = readLine()
        var fechaCreacion = LocalDate.parse(fechaC)
        print("3. Ofrece servicio técnico (Si o No): ")
        var servicio = readLine()
        var ofreceServicioTecnico: Boolean = false
        if(servicio == "Si"){ofreceServicioTecnico = true}
        print("4. Contacto: ")
        var contacto = readLine()

        if(nombre!= null && fechaCreacion != null && ofreceServicioTecnico != null && contacto != null){
            var marcaCreada = Marca(marcas.size, nombre, fechaCreacion, ofreceServicioTecnico, contacto)
            marcas.add(marcaCreada)
            //Almacenamiento de datos
            marcaCreada.almacenarDatosMarca(marcasArchivo)
            println("Marca registrada correctamente")
        }else{
            println("Algunos datos ingresados son inválidos")
        }
    }

    fun crearLaptop(){
        println("\n******* Formulario de registro de laptop *******")
        print("1. Modelo: ")
        var modelo = readLine()
        print("2. Marca: ")
        var marca = readLine()
        print("3. En producción (Si o No): ")
        var produccion = readLine()
        var enProduccion: Boolean = false
        if(produccion == "Si"){enProduccion = true}
        print("4. Fecha de lanzamiento (aaaa-mm-dd): ")
        var fechaL = readLine()
        var fechaLanzamiento = LocalDate.parse(fechaL)
        print("5. Precio: ")
        var precio = readLine()?.toDouble()

        if(modelo!= null && fechaLanzamiento != null && enProduccion != null && precio != null && marca != null){
            var laptopCreada = Laptop(laptops.size, modelo, marca, precio, fechaLanzamiento, enProduccion)
            laptops.add(laptopCreada)
            //Almacenamiento de datos
            laptopCreada.almacenarDatosMarca(laptopsArchivo)
            println("Laptop registrada correctamente")
        }else{
            println("Algunos datos ingresados son inválidos")
        }
    }

    //READ MARCA
    fun listarMarca(){
        println("\n******* MARCAS REGISTRADAS *******")
        //Comprobación de existencia de marcas registradas
        if(marcas.isNotEmpty()){
            marcas[0].listarMarcasRegistradas(marcasArchivo)
        }else{
            println("No existen marcas registradas")
        }
    }

    //READ LAPTOP
    fun listarLaptop(){
        println("\n******* LAPTOPS REGISTRADAS *******")
        //Comprobación de existencia de laptops registradas
        if(laptops.isNotEmpty()){
            laptops[0].listarLaptopsRegistradas(laptopsArchivo)
        }else{
            println("No existen laptops registradas")
        }
    }

    //UPDATE MARCA
    fun actualizarMarca(){
        println("\n******* Formulario de actualización de datos Marca *******")
        print("Ingrese el nombre de la marca: ")
        var nombre = readLine()
        var marcaAsociada = marcas.find {it.nombre == nombre}
        if(nombre != null && marcaAsociada != null){
            print("1. Nuevo nombre de la marca: ")
            var nombre = readLine()
            print("2. Nueva fecha de creación (aaaa-mm-dd): ")
            var fechaC = readLine()
            var fechaCreacion = LocalDate.parse(fechaC)
            print("3. Ofrece servicio técnico (Si o No): ")
            var servicio = readLine()
            var ofreceServicioTecnico: Boolean = false
            if(servicio == "Si"){ofreceServicioTecnico = true}
            print("4. Nuevo contacto: ")
            var contacto = readLine()
            //Actualizacion
            if(nombre!= null && fechaCreacion != null && ofreceServicioTecnico != null && contacto != null){
                marcaAsociada.actualizarDatosMarca(nombre, fechaCreacion, ofreceServicioTecnico, contacto)
                //Almacenar datos
                actualizarMarcas()
                println("Datos actualizados correctamente")
            }else{
                println("Error en los datos ingresados")
            }
        }
    }

    //UPDATE LAPTOP
    fun actualizarLaptop(){
        println("\n******* Formulario de actualización de datos Laptop *******")
        print("Ingrese la marca: ")
        var nombreMarca = readLine()
        print("Ingrese el id de la laptop: ")
        var idLaptop = readLine()?.toInt()
        var marcaAsociada = marcas.find {it.nombre == nombre}
        if(nombreMarca != null && marcaAsociada != null){
            var laptopAsociada = laptops.find {it.idLaptop == idLaptop}
            if(laptopAsociada != null){
                print("1. En producción (Si o No): ")
                var produccion = readLine()
                var enProduccion: Boolean = false
                if(produccion == "Si"){enProduccion = true}
                print("2. Nuevo precio: ")
                var precio = readLine()?.toDouble()

                if(enProduccion != null && precio != null){
                    laptopAsociada.actualziarDatosLaptop(precio, enProduccion)
                    //ALMACENAR DATOS
                    actualizarLaptops()
                    println("Datos actualziados correctamente")
                }else{
                    println("La marca ingresada no está registrada")
                }
            }else{
                println("La laptop ingresada no existe")
            }
        }
    }

    //DELET MARCA
    fun borrarMarca(){
        println("\n******* Formulario de eliminación de Marca *******")
        print("Ingrese la marca: ")
        var nombreMarca = readLine()
        print("Ingrese el id de la laptop: ")
        var idLaptop = readLine()?.toInt()
        var marcaAsociada = marcas.find {it.nombre == nombre}
        if(nombreMarca != null && marcaAsociada != null){

            marcas.remove(marcaAsociada)
            //Actualizar datos
            actualizarMarcas()
            println("Marca eliminada correctamente")
        }else{
            println("No existen registros de la marca ingresada")
        }
    }

    //DELET LAPTOP
    fun borrarLaptop(){
        println("\n******* Formulario de eliminación de Laptop *******")
        print("Ingrese la marca: ")
        var nombreMarca = readLine()
        var marcaAsociada = marcas.find {it.nombre == nombre}
        if(nombreMarca != null && marcaAsociada != null){
            var laptopAsociada = laptops.find {it.idLaptop == idLaptop}
            if(laptopAsociada != null){
                laptops.remove(laptopAsociada)
                //Actualizar datos
                actualizarLaptops()
                println("Laptop eliminada correctamente")
            }else{
                println("No existen registros de la laptop ingresada")
            }
        }else{
            println("No existen registros de la marca ingresada")
        }
    }

    //Menu
    var opcion = 0
    do{
        println("\n------------- MARCAS Y LAPTOPS -----------------")
        println("1. Registrar una marca")
        println("2. Registrar una laptop")
        println("3. Listar marcas registrada")
        println("4. Listar laptops registrada")
        println("5. Actualizar datos de una marca")
        println("6. Actualizar datos de una laptop")
        println("7. Eliminar una marca")
        println("8. Eliminar una laptop")
        println("9. Salir")
        println("Ingrese la opción a realizar: ")
        opcion = readLine()?.toInt()!!

        when(opcion){
            1-> crearMarca()
            2-> crearLaptop()
            3-> listarMarca()
            4-> listarLaptop()
            5-> actualizarMarcas()
            6-> actualizarLaptops()
            7-> borrarMarca()
            8-> borrarLaptop()
            9-> exitProcess(0)
            else -> println("Opción inválida")
        }
    }while (opcion != 9)



}