package org.example

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    var continuar = true

    while (continuar) {
        println("Menú:")
        println("1. Introducir un nuevo sudoku")
        println("2. Salir")
        print("Elige una opción: ")

        when (scanner.nextLine()) {
            "1" -> {
                val board = arrayOf(
                    intArrayOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
                    intArrayOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
                    intArrayOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
                    intArrayOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
                    intArrayOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
                    intArrayOf(7, 0, 0, 0, 2, 0, 0, 3, 6),
                    intArrayOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
                    intArrayOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
                    intArrayOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
                )

                val sudoku = Sudoku()
                // Convertir Array<IntArray> a List<List<Int>>
                val boardList = board.map { it.toList() }
                sudoku.buscarSoluciones(boardList)
                val numSoluciones = sudoku.obtenerNumeroDeSoluciones()
                if (numSoluciones > 0) {
                    println("Se han encontrado $numSoluciones soluciones:")
                    for (indice in 0 until numSoluciones) {
                        println("Solución ${indice + 1}:")
                        sudoku.mostrarSolucion(indice)?.forEach { fila ->
                            println(fila.joinToString(" "))
                        }
                        println()
                    }
                } else {
                    println("No se ha encontrado ninguna solución.")
                }
            }
            "2" -> {
                println("ADIOS")
                continuar = false
            }
            else -> println("Opción no válida")
        }
    }
}

/**
 * La clase Sudoku representa un rompecabezas de Sudoku.
 * Proporciona métodos para resolver el rompecabezas y recuperar las soluciones.
 */
class Sudoku {
    private var tablero = Array(9) { IntArray(9) }
    private var soluciones = mutableListOf<Array<IntArray>>()

    /**
     * Este método inicia el proceso de encontrar soluciones para el rompecabezas de Sudoku.
     * @param puzzle El rompecabezas de Sudoku representado como una lista de listas de enteros.
     */
    fun buscarSoluciones(puzzle: List<List<Int>>) {
        tablero = Array(9) { IntArray(9) }
        for (fila in 0..8) {
            for (columna in 0..8) {
                tablero[fila][columna] = puzzle[fila][columna]
            }
        }
        resolver(0, 0)
    }

    /**
     * Este método utiliza un algoritmo de retroceso para encontrar todas las soluciones posibles para el rompecabezas de Sudoku.
     * @param fila El índice de la fila.
     * @param columna El índice de la columna.
     * @return Un booleano que indica si se ha encontrado una solución.
     */
    private fun resolver(fila: Int, columna: Int): Boolean {
        if (fila == 9) {
            soluciones.add(tablero.map { it.clone() }.toTypedArray())
            return false // Permitir múltiples soluciones
        }

        val siguienteFila = if (columna == 8) fila + 1 else fila
        val siguienteColumna = (columna + 1) % 9

        if (tablero[fila][columna] != 0) {
            return resolver(siguienteFila, siguienteColumna)
        }

        for (num in 1..9) {
            if (esValido(num, fila, columna)) {
                tablero[fila][columna] = num
                resolver(siguienteFila, siguienteColumna) // Continuar buscando más soluciones
                tablero[fila][columna] = 0
            }
        }
        return false // Retorna falso si no se encuentra ninguna solución válida desde esta posición
    }

    /**
     * Este método verifica si un número se puede colocar en una posición determinada en el rompecabezas de Sudoku.
     * @param num El número que se va a colocar.
     * @param fila El índice de la fila.
     * @param columna El índice de la columna.
     * @return Un booleano que indica si el número se puede colocar en la posición.
     */
    private fun esValido(num: Int, fila: Int, columna: Int): Boolean {
        for (index in 0..8) {
            if (tablero[fila][index] == num || tablero[index][columna] == num) return false
        }
        val inicioFila = fila / 3 * 3
        val inicioColumna = columna / 3 * 3
        for (i in 0..2) {
            for (j in 0..2) {
                if (tablero[inicioFila + i][inicioColumna + j] == num) return false
            }
        }
        return true
    }

    /**
     * Este método devuelve el número de soluciones encontradas para el rompecabezas de Sudoku.
     * @return El número de soluciones.
     */
    fun obtenerNumeroDeSoluciones(): Int = soluciones.size

    /**
     * Este método devuelve una solución específica para el rompecabezas de Sudoku.
     * @param indice El índice de la solución.
     * @return La solución representada como una matriz de matrices de enteros, o null si el índice es inválido.
     */
    fun mostrarSolucion(indice: Int): Array<IntArray>? = soluciones.getOrNull(indice)
}
