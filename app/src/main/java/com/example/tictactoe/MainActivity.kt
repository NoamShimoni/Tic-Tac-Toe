package com.example.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttons: Array<MaterialButton>
    private lateinit var resetButton: MaterialButton
    private var board = CharArray(9) { ' ' }
    private val xString = 'X'
    private val oString = 'O'
    private val emptyString = ' '
    private var currentPlayer = this.xString
    private var winConditions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.main)

        buttons = arrayOf(
            findViewById(this.binding.cell00.id),
            findViewById(this.binding.cell01.id),
            findViewById(this.binding.cell02.id),
            findViewById(this.binding.cell10.id),
            findViewById(this.binding.cell11.id),
            findViewById(this.binding.cell12.id),
            findViewById(this.binding.cell20.id),
            findViewById(this.binding.cell21.id),
            findViewById(this.binding.cell22.id),
        )

        this.buttons.forEachIndexed { index, button ->
            button.text = this.board[index].toString().trim()
            button.setOnClickListener { this.makeMove(index) }
        }

        this.resetButton = findViewById(this.binding.resetButton.id)
        this.resetButton.setOnClickListener { ::resetGame }
        this.resetButton.isEnabled = false
    }

    private fun makeMove(index: Int) {
        if (this.board[index] != this.emptyString) return

        if(this.resetButton.isEnabled) return

        this.board[index] = this.currentPlayer
        this.buttons[index].text = this.currentPlayer.toString()

        when {
            this.checkWinner() -> this.endGame("${this.currentPlayer} wins!")
            this.isDraw() -> this.endGame("It's a draw!")
            else -> this.currentPlayer = if (this.currentPlayer == this.xString) this.oString else this.xString
        }
    }

    private fun checkWinner(): Boolean {
        return this.winConditions.any {
            this.board[it[0]] == this.currentPlayer &&
                    this.board[it[1]] == this.currentPlayer &&
                    this.board[it[2]] == this.currentPlayer
        }
    }

    private fun isDraw(): Boolean {
        return this.board.all { it != this.emptyString }
    }

    private fun endGame(message: String) {
        this.showResult(message)
        this.resetButton.isEnabled = true
    }

    private fun resetGame() {
        this.board = CharArray(9) { this.emptyString }
        this.currentPlayer = this.xString
        this.buttons.forEach { it.text = "" }
        this.resetButton.isEnabled = false
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}