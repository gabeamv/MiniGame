package com.example.minigame
import android.content.Context
import android.view.View
import android.graphics.Rect
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.os.Handler
import android.os.Looper

// Custom View that will display the movement and position of the snake.
class SnakeView(context: Context) : View(context) {
    // The paint of the snake
    private var paint: Paint = Paint().apply {
        color = Color.WHITE
    }
    // The dimensions of the snake.
    private var snake: Rect = Rect(50, 50, 100, 100)

    // Stores the direction snake is going in.
    private var right: Boolean = false
    private var left: Boolean = false
    private var up: Boolean = false
    private var down: Boolean = false

    // Handler to recursively schedule the snake's movement 16 ms apart.
    private val delay: Handler = Handler(Looper.getMainLooper())
    // Runnable object that will be recursively called to move the snake
    private val game: Runnable = object: Runnable {
        // Method to play the game
        override fun run() {
            if (!right && !left && !up && !down) {
                right =  true
            }
            // method to move snake
            if (right) moveSnakeRight(8)
            if (left) moveSnakeLeft(8)
            if (up) moveSnakeUp(8)
            if (down) moveSnakeDown(8)
            // delayed recursive call, delay is 16 because 60 fps = 1000ms / 60 "=" 16.67 ms per frame
            delay.postDelayed(this, 16)
        }
    }

    fun startGame(): Runnable {
        return game
    }

    fun moveSnakeRight(dx: Int) {
        snake.offset(dx, 0)
        invalidate()
    }
    fun moveSnakeLeft(dx: Int) {
        snake.offset(-dx, 0)
        invalidate()
    }
    fun moveSnakeUp(dy: Int) {
        snake.offset(0, -dy)
        invalidate()
    }
    fun moveSnakeDown(dy: Int) {
        snake.offset(0, dy)
        invalidate()
    }

    fun setSnakeRight(){
        right = true
        left = false
        up = false
        down = false
    }
    fun setSnakeLeft(){
        right = false
        left = true
        up = false
        down = false
    }

    fun setSnakeUp(){
        right = false
        left = false
        up = true
        down = false
    }

    fun setSnakeDown(){
        right = false
        left = false
        up = false
        down = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(snake, paint)
    }

}