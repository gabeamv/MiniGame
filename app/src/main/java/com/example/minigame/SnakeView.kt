package com.example.minigame
import android.content.Context
import android.view.View
import android.graphics.Rect
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color


// Custom View that will display the movement and position of the snake.
class SnakeView(context: Context) : View(context) {
    private var paint: Paint = Paint().apply {
        color = Color.RED
    }
    private var snake: Rect = Rect(100, 100, 200, 200)

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(snake, paint)
    }

}