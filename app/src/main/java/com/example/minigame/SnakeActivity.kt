package com.example.minigame

import android.os.Bundle
import androidx.activity.ComponentActivity

import android.widget.Button

import androidx.constraintlayout.widget.ConstraintLayout

class SnakeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_snake)

        var snake = SnakeView(this)
        addContentView(snake, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT))

        val right: Button = findViewById(R.id.snake_move_right)
        right.setOnClickListener {snake.moveSnakeRight(10)}

    }

}

