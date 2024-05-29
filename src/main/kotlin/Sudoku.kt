package org.example

class SudokuSolver(private val board: Array<IntArray>) {
    private val size = 9
    private var solutionCount = 0
    private var foundMultipleSolutions = false
    private var firstSolution: Array<IntArray>? = null

    // Método para buscar todas las soluciones del Sudoku
    fun cerca_solucions() {
        solve(0, 0)
    }

    // Método recursivo para resolver el Sudoku
    private fun solve(row: Int, col: Int): Boolean {
        var currentRow = row
        var currentCol = col

        // Si llegamos al final de la fila, pasamos a la siguiente columna
        if (currentRow == size) {
            currentRow = 0
            currentCol++
            // Si llegamos al final del tablero, encontramos una solución
            if (currentCol == size) {
                solutionCount++
                if (solutionCount == 1) {
                    firstSolution = board.map { it.copyOf() }.toTypedArray()
                }
                if (solutionCount > 1) {
                    foundMultipleSolutions = true
                    return true // Stop search as we have found more than one solution
                }
                return false // Continue to find all solutions
            }
        }

        // Si la celda ya está llena, pasamos a la siguiente
        if (board[currentRow][currentCol] != 0) {
            return solve(currentRow + 1, currentCol)
        }

        // Intentamos colocar los números del 1 al 9 en la celda vacía
        for (num in 1..9) {
            if (isSafe(currentRow, currentCol, num)) {
                board[currentRow][currentCol] = num
                if (solve(currentRow + 1, currentCol)) {
                    return true
                }
                // Si no encontramos solución, revertimos el cambio
                board[currentRow][currentCol] = 0
            }
        }

        // Si no se encuentra ninguna solución, devolvemos falso
        return false
    }

    // Método para comprobar si es seguro colocar un número en una celda
    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        // Verificar la fila y la columna
        for (i in 0 until size) {
            if (board[row][i] == num || board[i][col] == num) {
                return false
            }
        }

        // Verificar el subcuadro de 3x3
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in startRow until startRow + 3) {
            for (j in startCol until startCol + 3) {
                if (board[i][j] == num) {
                    return false
                }
            }
        }

        return true
    }

    // Método para retornar el número de soluciones encontradas
    fun num_solucions(): Int {
        return solutionCount
    }

    // Método para saber si hay múltiples soluciones
    fun hasMultipleSolutions(): Boolean {
        return foundMultipleSolutions
    }

    // Método para retornar la primera solución encontrada
    fun getFirstSolution(): Array<IntArray>? {
        return firstSolution
    }

    // Método para imprimir el tablero
    fun printBoard(board: Array<IntArray> = this.board) {
        for (r in 0 until size) {
            for (d in 0 until size) {
                print("${board[r][d]} ")
            }
            println()
        }
        println()
    }
}

fun main() {
    val board = arrayOf(
        intArrayOf(8, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 3, 6, 0, 0, 0, 0, 0),
        intArrayOf(0, 7, 0, 0, 9, 0, 2, 0, 0),
        intArrayOf(0, 5, 0, 0, 0, 7, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 4, 5, 7, 0, 0),
        intArrayOf(0, 0, 0, 1, 0, 0, 0, 3, 0),
        intArrayOf(0, 0, 1, 0, 0, 0, 0, 6, 8),
        intArrayOf(0, 0, 8, 5, 0, 0, 0, 1, 0),
        intArrayOf(0, 9, 0, 0, 0, 0, 4, 0, 0)
    )

    val solver = SudokuSolver(board)
    solver.cerca_solucions()
    val numSolutions = solver.num_solucions()
    val hasMultiple = solver.hasMultipleSolutions()

    println("Numero de soluciones encontradas: $numSolutions")

    if (hasMultiple) {
        println("Tiene mas soluciones, no es un Sudoku")
    } else if (numSolutions == 1) {
        println("Tiene solo una solucion, es un Sudoku")
        solver.getFirstSolution()?.let { solution ->
            println("La solucion es:")
            solver.printBoard(solution)
        }
    } else {
        println("El Sudoku no a podido ser solucionado.")
    }
}