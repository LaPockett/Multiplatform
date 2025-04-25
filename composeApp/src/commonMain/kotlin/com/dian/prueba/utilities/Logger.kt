package com.dian.prueba.utilities

/**
 * Centralización de logging para no llenar de prints
 * en todas partes del código. EL código se ve más limpio,
 * incluso más legible y fácil de mantener porque no tenemos
 * que estar repitiendo el print en cada parte del código.
 * Si queremos poner más logs, simplemente añadimos una que queramos
 * o invocamos una función que ya tenemos con los parámetros que sean.
 *
 * Básicamente atomizar todo lo que se pueda repetir en el código.
 */
class Logger {

    fun debug(variable:String , tag: String)  {
        println("DIAN DEBUG - [$tag]: $variable")
    }
    fun warn(result: String, tag: String) {
        println("DIAN WARN - [$tag]: $result")
    }
    fun error(e: Exception, tag: String) {
        println("DIAN ERROR - [$tag] : ${e.stackTraceToString()}")
    }
}