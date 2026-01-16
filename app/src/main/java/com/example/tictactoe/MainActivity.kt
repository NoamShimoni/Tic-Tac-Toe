package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var resetButton: Button
    private var board = CharArray(9) { ' ' }
    private var currentPlayer = 'X'
    private var winConditions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttons = arrayOf(
            findViewById(R.id.cell_0_0),
            findViewById(R.id.cell_0_1),
            findViewById(R.id.cell_0_2),
            findViewById(R.id.cell_1_0),
            findViewById(R.id.cell_1_1),
            findViewById(R.id.cell_1_2),
            findViewById(R.id.cell_2_0),
            findViewById(R.id.cell_2_1),
            findViewById(R.id.cell_2_2),
        )

        buttons.forEachIndexed { index, button ->
            button.text = board[index].toString().trim()
            button.setOnClickListener { makeMove(index) }
        }

        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener { resetGame() }
        resetButton.isEnabled = false
    }

    private fun makeMove(index: Int) {
        if (board[index] != ' ') return

        if(resetButton.isEnabled) return

        board[index] = currentPlayer
        buttons[index].text = currentPlayer.toString()

        when {
            checkWinner() -> endGame("$currentPlayer wins!")
            isDraw() -> endGame("It's a draw!")
            else -> currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        }
    }

    private fun checkWinner(): Boolean {
        return winConditions.any {
            board[it[0]] == currentPlayer &&
                    board[it[1]] == currentPlayer &&
                    board[it[2]] == currentPlayer
        }
    }

    private fun isDraw(): Boolean {
        return board.all { it != ' ' }
    }

    private fun endGame(message: String) {
        showResult(message)
        resetButton.isEnabled = true
    }

    private fun resetGame() {
        board = CharArray(9) { ' ' }
        currentPlayer = 'X'
        buttons.forEach { it.text = "" }
        resetButton.isEnabled = false
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}