// Noam-Shimoni-213785298-Ben-Bashvitz-324228139
package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttons: Array<MaterialButton>
    private var board = CharArray(9) { empty }
    private var currentPlayer = X
    private var winConditions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.buttons = arrayOf(
            this.binding.cell00,
            this.binding.cell01,
            this.binding.cell02,
            this.binding.cell10,
            this.binding.cell11,
            this.binding.cell12,
            this.binding.cell20,
            this.binding.cell21,
            this.binding.cell22,
        )

        this.buttons.forEachIndexed { index, button ->
            button.text = this.board[index].toString()
            button.setOnClickListener { this.makeMove(index) }
        }

        this.binding.resetButton.setOnClickListener(::resetGame)
        this.binding.resetButton.isEnabled = false
    }

    private fun makeMove(index: Int) {
        if (this.board[index] != empty || this.binding.resetButton.isEnabled) return

        this.board[index] = this.currentPlayer
        this.buttons[index].text = this.currentPlayer.toString()

        when {
            this.checkWinner() -> this.endGame("${this.currentPlayer} wins!")
            this.isDraw() -> this.endGame("It's a draw!")
            else -> this.updateCurrentPlayer()
        }
    }

    private fun updateCurrentPlayer() {
        this.currentPlayer = if (this.currentPlayer == X) O else X
        this.binding.statusTextView.text = "Player ${this.currentPlayer}'s Turn"
    }
    private fun checkWinner(): Boolean {
        return this.winConditions.any {
            this.board[it[0]] == this.currentPlayer &&
                    this.board[it[1]] == this.currentPlayer &&
                    this.board[it[2]] == this.currentPlayer
        }
    }

    private fun isDraw(): Boolean {
        return this.board.all { it != empty }
    }

    private fun endGame(message: String) {
        this.showResult(message)
        this.binding.statusTextView.text = message
        this.binding.resetButton.isEnabled = true
    }

    private fun resetGame(view: View) {
        this.board = CharArray(9) { empty }
        this.currentPlayer = X
        this.binding.statusTextView.text = "Player ${this.currentPlayer}'s Turn"
        this.buttons.forEach { it.text = "" }
        this.binding.resetButton.isEnabled = false
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}