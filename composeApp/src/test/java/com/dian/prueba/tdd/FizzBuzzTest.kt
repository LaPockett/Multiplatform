package com.dian.prueba.tdd

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Para practicar el TDD
 *
 * Escribe un programa que muestre por consola (con un print) los
 * números de 1 a 100 (ambos incluidos y con un salto de línea entre
 * cada impresión), sustituyendo los siguientes:
 * - Múltiplos de 3 por la palabra "fizz".
 * - Múltiplos de 5 por la palabra "buzz".
 * - Múltiplos de 3 y de 5 a la vez por la palabra "fizzbuzz".
 *
 * Proceso:
 * 1. Crear clase FizzBuzz
 * 2. Crear metodo fizzbuzz sin implementar
 * 3. Crear el primer test
 * 4. Cuando salga en rojo, implementar esa parte del test en el método
 * 5. Así sucesivamente: test, run test e implementar.
 *
 */
class FizzBuzzTest {
    val fizzBuzz = FizzBuzz()

    @Test
    fun `return 1 if number is 1`() {
        val result = fizzBuzz.fizzbuzz(1)
        assertEquals("1", result)
    }

    @Test
    fun `return 2 if number is 2`() {
        val result = fizzBuzz.fizzbuzz(2)
        assertEquals("2", result)
    }

    @Test
    fun `return 'fizz' if number is 3`() {
        val result = fizzBuzz.fizzbuzz(3)
        assertEquals("fizz", result)
    }

    @Test
    fun `return 'buzz' if number is 5`() {
        val result = fizzBuzz.fizzbuzz(5)
        assertEquals("buzz", result)
    }

    @Test
    fun `return 'fizzbuzz' if number is 15`() {
        val result = fizzBuzz.fizzbuzz(15)
        assertEquals("fizzbuzz", result)
    }

    @Test
    fun `return fizz if number is multiple of 3`() {
        val result = fizzBuzz.fizzbuzz(3)
        assertEquals("fizz", result)
    }

    @Test
    fun `return buzz if number is multiple of 5`() {
        val result = fizzBuzz.fizzbuzz(5)
        assertEquals("buzz", result)
    }

    @Test
    fun `return fizzbuzz if number is multiple of 3 and 5`() {
        val result = fizzBuzz.fizzbuzz(15)
        assertEquals("fizzbuzz", result)
        val result2 = fizzBuzz.fizzbuzz(30)
        assertEquals("fizzbuzz", result2)
        val result3 = fizzBuzz.fizzbuzz(45)
        assertEquals("fizzbuzz", result3)
    }
    @Test
    fun `to print all numbers from 1 to 100` (){
        fizzBuzz.allNumbers()
    }

}