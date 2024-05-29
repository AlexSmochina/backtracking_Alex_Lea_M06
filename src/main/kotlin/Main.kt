package org.example

class SudokuSolver(private val board: Array<IntArray>) {
    private val size = 9
    private var solutionCount = 0

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
                printBoard()
                println("Solution $solutionCount found")
                return true
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
        var verificar = true
        // Verificar la fila y la columna
        for (i in 0 until size) {
            if (board[row][i] == num || board[i][col] == num) {
                verificar = false
            }
        }

        // Verificar el subcuadro de 3x3
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in startRow until startRow + 3) {
            for (j in startCol until startCol + 3) {
                if (board[i][j] == num) {
                    verificar = false
                }
            }
        }

        return verificar
    }

    // Método para retornar el número de soluciones encontradas
    fun num_solucions(): Int {
        return solutionCount
    }

    // Método para imprimir el tablero
    private fun printBoard() {
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
    println("Number of solutions found: ${solver.num_solucions()}")
}