package org.example

class SudokuSolver(private val board: Array<IntArray>) {
    private val size = 9
    private var solutionCount = 0

    // Método para buscar todas las soluciones del Sudoku
    fun cerca_solucions() {
        solve(0, 0)
    }



    // Método recursivo para resolver el Sudoku
    fun solve(row: Int, col: Int): Boolean {
        var currentRow = row
        var currentCol = col
        var foundSolution = false

        if (currentRow == size) {
            currentRow = 0
            if (++currentCol == size) {
                solutionCount++
                printBoard()
                println("Solution $solutionCount found")
                foundSolution = true
            }
        }

        if (!foundSolution && board[currentRow][currentCol] != 0) {
            foundSolution = solve(currentRow + 1, currentCol)
        }

        for (num in 1..9) {
            if (!foundSolution && isSafe(currentRow, currentCol, num)) {
                board[currentRow][currentCol] = num
                foundSolution = solve(currentRow + 1, currentCol)
                if (!foundSolution) {
                    board[currentRow][currentCol] = 0
                }
            }
        }

        return foundSolution
    }

    // Método para comprobar si es seguro colocar un número en una celda
    fun isSafe(row: Int, col: Int, num: Int): Boolean {
        var safe = true

        // Verificar la fila y la columna
        for (i in 0 until size) {
            if (board[row][i] == num || board[i][col] == num) {
                safe = false
            }
        }

        // Verificar el subcuadro de 3x3
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in startRow until startRow + 3) {
            for (j in startCol until startCol + 3) {
                if (board[i][j] == num) {
                    safe = false
                }
            }
        }

        return safe
    }

    // Método para retornar el número de soluciones encontradas
    fun num_solucions(): Int {
        return solutionCount
    }

    // Método para imprimir el tablero
    fun printBoard() {
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
