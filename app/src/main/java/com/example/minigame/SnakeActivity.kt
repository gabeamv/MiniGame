package com.example.minigame

import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View.OnClickListener
import android.view.View

class SnakeActivity : ComponentActivity() {

    private lateinit var scoreView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_snake)
        // Handler to delay the snake move.

        // Snake head that will move around the screen.
        var snake: SnakeView = SnakeView(this)
        addContentView(snake, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT))

        // Button that will move the snake head across the screen
        val start: Button = findViewById(R.id.snake_start)
        val right: Button = findViewById(R.id.snake_right)
        val left: Button = findViewById(R.id.snake_left)
        val up: Button = findViewById(R.id.snake_up)
        val down: Button = findViewById(R.id.snake_down)

        // View that will store the score.
        scoreView = findViewById(R.id.snake_score)

        // Listener for start button
        start.setOnClickListener {
            snake.startGame().run()
            start.isEnabled = false
        }

        // Listener for the dPad buttons.
        val dPadListener = View.OnClickListener { view ->
            when (view.id) {
                // When a certain button is clicked, it sets the trajectory of the snake head.
                R.id.snake_right -> {
                    snake.setSnakeRight()
                    right.isEnabled = false
                    left.isEnabled = false
                    up.isEnabled = true
                    down.isEnabled = true
                }
                R.id.snake_left -> {
                    snake.setSnakeLeft()
                    right.isEnabled = false
                    left.isEnabled = false
                    up.isEnabled = true
                    down.isEnabled = true
                }
                R.id.snake_up -> {
                    snake.setSnakeUp()
                    right.isEnabled = true
                    left.isEnabled = true
                    up.isEnabled = false
                    down.isEnabled = false
                }
                R.id.snake_down -> {
                    snake.setSnakeDown()
                    right.isEnabled = true
                    left.isEnabled = true
                    up.isEnabled = false
                    down.isEnabled = false
                }
            }
        }
        // Set listener for buttons
        right.setOnClickListener(dPadListener)
        left.setOnClickListener(dPadListener)
        up.setOnClickListener(dPadListener)
        down.setOnClickListener(dPadListener)
    }

    // Update the score in the view.
    private fun updateScore() {
        // Get the integer of the score in the view.
        var currScore: Int = scoreView.text.toString().toInt()
        // Increment.
        currScore++
        // Set the text to the updated score.
        scoreView.text = currScore.toString()
    }

}

