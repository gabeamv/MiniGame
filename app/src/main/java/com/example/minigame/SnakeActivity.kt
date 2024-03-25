package com.example.minigame

import android.os.Bundle
import androidx.activity.ComponentActivity

import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View.OnClickListener
import android.view.View

class SnakeActivity : ComponentActivity() {
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

        // Listener for start button
        start.setOnClickListener {snake.startGame().run()}

        // Listener for the dPad buttons.
        val dPadListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.snake_right -> snake.setSnakeRight()
                R.id.snake_left -> snake.setSnakeLeft()
                R.id.snake_up -> snake.setSnakeUp()
                R.id.snake_down -> snake.setSnakeDown()
            }
        }
        // Set listener for buttons
        right.setOnClickListener(dPadListener)
        left.setOnClickListener(dPadListener)
        up.setOnClickListener(dPadListener)
        down.setOnClickListener(dPadListener)
    }

}

