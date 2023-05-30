import java.util.*;

fun main(args: Array<String>) {

    //--------- VARIABLES ---------------
    //Inmutable (No se reasignan)
    val inmutable: String = "Steven";
    //inmutable = "David";

    //Mutables (Se pueden reasignar)
    var mutable: String = "David";
    mutable = "Steven";

    //---------- DUCK TYPING -----------
    var ejemploVariable = " Steven Quispe ";
    val edadEjemplo: Int = 12;
    ejemploVariable.trim()

    //------- VARIABLES PRIMITIVAS ---------
    val nombreProfesor: String = "Steven";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'C';
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    //------ CONDICIONALES --------------
    //No existe Switch, se usa when
    val estadoCivilWhen = "C";
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"


    //------- USO DE FUNCIONES --------
    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    //Parámetros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //-------------------- ARREGLOS ---------------------
    //1. Arreglos estáticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //2. Arreglo Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8)
    println(arregloEstatico)


    //----------------- OPERADORES ---------------
    //FOR EACH -> INT
    val respuestaForEach: Unit = arregloDinamico
        .forEach{ valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    arregloDinamico.forEach{ println(it) }  //it (en ingles eso) significa el elemento iterado

    arregloEstatico
        .forEachIndexed{indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //Map -> Mutar el arreglo (cambia)
    //1)Enviamos el nuevo vaor de la iteracion
    //2) Nos devuelve un NUEVO ARREGLO con valores modificados
    val respuestMap: List<Double> = arregloDinamico
        .map{valorActual: Int ->
            return@map valorActual.toDouble() +100.00
        }

    println(respuestMap)
    val respuestaMapDos = arregloDinamico.map { it+15 }

    //Filter -> Filtrar el arreglo
    //1) Devolver una expresion (True o False)
    //2) Nuevo arreglo filtrado
    val respuestaFilter: List <Int> = arregloDinamico
        .filter {valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5   //Condicion
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //Operadores lógicos
    /*
    Or -- And
    Or -> Any (Alguno cumple?)
    And -> All (Todos cumplen?)
     */
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual>5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll) //false

    //REDUCE -> Valor acumulado
    //Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    /*
    Ejemplo: [1,2,3,4,5] -> Sumar todos los valores del arreglo
        valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion1
        valorIteracion2 = valorIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
        valorIteracion3 = valorIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
        valorIteracion4 = valorIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
        valorIteracion5 = valorIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5
        Aplicacion: Carrito compras
     */
    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)  //Logica de negocio
        }
    println(respuestaReduce) //36



}
// -------- CLASES ---------
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( //Constructor primario
    //EJEMPLO:
    //uno: Int (Parametro sin modificador de acceso)
    //private var uno: Int, (Propiedad publica Clase numeros.uno
    // var uno: Int (Propiedad de la clase por defecto PUBLICO)
    //
    protected val numeroUno: Int,
    protected val numeroDos: Int,
){
    init{
        this.numeroUno; this.numeroDos;
        numeroUno; numeroDos;
        println("Inicializando");
    }
}

class Suma( //Constructor Primario Suma
    unoParametro: Int,  //Parametro
    dosParametro: Int,  //Parametro
): Numeros(unoParametro, dosParametro) {     //Extendiendo y mandando los parametros (super)
    init{
        this.numeroUno
        this.numeroDos
    }
    //multiples constructores
    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )

    constructor(
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos
    )

    constructor(
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    //métodos
    public fun sumar(): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)   //this.agregarHistorial(total)
        return total
    }

    companion object{
        //Atributos y metodos "Compartidos" Singletons o Static de esta clase
        //Todas las instancias de esta clase comparten estos atributos y metodos
        //dentro del companion Object
        val pi = 3.14

        fun elevarAlCuadrado(num: Int): Int{
            return num * num
        }

        val historialSumas = ArrayList<Int>()

        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }

    }

}




//------- FUNCIONES --------
//void -> Unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre: ${nombre}") // template string
}

fun calcularSueldo(
    sueldo: Double,     //Requerido
    tasa: Double = 12.00,    //Opcional(defecto)
    bonoEspecial: Double? = null,   //Opcion null -> nullable
): Double{
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}