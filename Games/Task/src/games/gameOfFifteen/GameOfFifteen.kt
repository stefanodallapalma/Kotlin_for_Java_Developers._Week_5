package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.*

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        (board.getAllCells() zip initializer.initialPermutation).forEach {
            (cell, value) -> board[cell] = value
        }
    }

    override fun canMove() = true
    override fun hasWon() = board.getAllCells().mapNotNull { board[it] } == (1..15).toList()
    override fun processMove(direction: Direction) {
        val unoccupiedCell = board.unoccupiedCell()
        var isDirectionAllowed = false

        when(direction){
            Direction.LEFT -> isDirectionAllowed = unoccupiedCell.j < board.width
            Direction.UP -> isDirectionAllowed = unoccupiedCell.i < board.width
            Direction.DOWN -> isDirectionAllowed = unoccupiedCell.i > 1
            Direction.RIGHT -> isDirectionAllowed = unoccupiedCell.j > 1
        }

        if (isDirectionAllowed){
            when(direction){
                Direction.LEFT -> board.swapCells(unoccupiedCell, board.getCell(unoccupiedCell.i, unoccupiedCell.j+1))
                Direction.UP -> board.swapCells(unoccupiedCell, board.getCell(unoccupiedCell.i+1, unoccupiedCell.j))
                Direction.DOWN -> board.swapCells(unoccupiedCell, board.getCell(unoccupiedCell.i-1, unoccupiedCell.j))
                Direction.RIGHT -> board.swapCells(unoccupiedCell, board.getCell(unoccupiedCell.i, unoccupiedCell.j-1))
            }
        }

        return
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i, j)]
}

fun GameBoard<Int?>.unoccupiedCell(): Cell = find { it == null }!!
fun GameBoard<Int?>.swapCells(cell1: Cell, cell2: Cell) = run {
    val previousCell1Value = get(cell1)
    set(cell1, get(cell2)).also { set(cell2, previousCell1Value) }
}
