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
    val sueldo: Double =1.2;
    val estadoCivil: Char = 'C';
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    //------ CONDICIONALES --------------
    //No existe Switch, se usa when
    val estadoCivilWhen = "C";
    when(estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        ("S")->{
            println("Soltero")
        }
        else ->{
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