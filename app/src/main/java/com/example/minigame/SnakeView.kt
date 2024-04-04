package com.example.minigame
import android.content.Context
import android.view.View
import android.graphics.Rect
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.Queue
import java.util.LinkedList


// Custom View that will display the movement and position of the snake.
class SnakeView(context: Context) : View(context) {
    // The paint of the snake.
    private var snakePaint: Paint = Paint().apply {color = Color.WHITE}
    // The paint of the apple.
    private var applePaint: Paint = Paint().apply {color = Color.RED}
    // The dimensions of the apple.
    private var apple: Rect = Rect(500, 500, 550, 550)
    // Represents the score view of the snake.
    //private var scoreView: TextView = findViewById(R.id.snake_score)

    // Everytime the snake changes direction, a new head will be put at the head of the queue.
    // As the snake moves overtime, the tail will dequeue.
    private var snakeBody: LinkedList<Rect> = LinkedList<Rect>()
    // Linked List queue to store the dimension that needs to be taken away from the snake as it moves.
    private var snakeTailRemove: LinkedList<String> = LinkedList<String>()

    // Stores the direction snake is going in.
    private var right: Boolean = false
    private var left: Boolean = false
    private var up: Boolean = false
    private var down: Boolean = false

    // Represents dimension of the snake to remove.
    private val LEFT: String = "left"
    private val RIGHT: String = "right"
    private val TOP: String = "top"
    private val BOTTOM: String = "bottom"

    // Stores whether there has been a direction change, when the user inputs up/down/left/right
    private var directionChange: Boolean = false

    // Handler to recursively schedule the snake's movement 16 ms apart.
    private val delay: Handler = Handler(Looper.getMainLooper())
    // Runnable object that will be recursively called to move the snake
    private val game: Runnable = object: Runnable {
        // Method to play the game
        override fun run() {
            // When there has been a direction change,
            if (directionChange) {
                directionChange = false
                delay.postDelayed(this, 120)
                return
            }
            // Move the snake.
            moveSnake()
            // Update the game
            invalidate()
            // delayed recursive call, delay is 16 because 60 fps = 1000ms / 60 "=" 16.67 ms per frame
            delay.postDelayed(this, 200)
        }
    }
    // Initialize the game.
    init {
        // Snake goes right first, snake head is created, snake dimension of the tail that will be removed.
        right = true
        snakeBody.addFirst(Rect(50, 50, 100, 100))
        snakeTailRemove.addLast(LEFT)
    }

    // Method to return a runnable in the activity to start the game.
    fun startGame(): Runnable {return game}
    // Method to move the snake by 50 depending on the direction its takes TODO: update needed
    private fun moveSnake() {
        // Move the head.
        moveHead()
        // Eat apple if you can.
        if (eatApple()) {
            // Increase score.
            //updateScore()
            // Return so that the tail will not be updated during the game iteration that this is executed in.
            // The snake will grow +50 length.
            return
        }
        // Move the tail
        moveTail()
    }
    // Method to set snake direction called from the activity.
    fun setSnakeRight() {
        // We store that there has been a direction change.
        directionChange = true
        // Set snake to new direction
        right = true
        left = false
        up = false
        down = false
        // Depending on the orientation of the original head, we update the snake with a new head (Rect).
        // Once the user presses a direction button, a new head (rect) will be generated right of the hold head.
        // This will be done outside of the scope of the moveSnake() method to all a more accurate real time user input.
        when (snakeTailRemove.peek()) {
            TOP -> snakeBody.addFirst(Rect(snakeBody.first.left + 50, snakeBody.first.bottom - 50, snakeBody.first.right + 50, snakeBody.first.bottom))
            BOTTOM -> snakeBody.addFirst(Rect(snakeBody.first.left + 50, snakeBody.first.top, snakeBody.first.right + 50, snakeBody.first.top + 50))
        }
        // Dimension to remove for this part of the snake.
        snakeTailRemove.addFirst(LEFT)
        // If eaten apple, don't move tail, invalidate, and return.
        if (eatApple()) {
            invalidate()
            return
        }
        // We move the tail after the head is updated.
        moveTail()
        // Update the canvas.
        invalidate()
    }
    // Method to set snake direction called from the activity.
    fun setSnakeLeft(){
        // We store that there has been a direction change.
        directionChange = true
        // Set snake to new direction
        right = false
        left = true
        up = false
        down = false
        // Depending on the orientation of the original head, we update the snake with a new head (Rect).
        // Once the user presses a direction button, a new head (rect) will be generated left of the hold head.
        // This will be done outside of the scope of the moveSnake() method to all a more accurate real time user input.
        when (snakeTailRemove.peek()) {
            TOP -> snakeBody.addFirst(Rect(snakeBody.first.left - 50, snakeBody.first.bottom - 50, snakeBody.first.right - 50, snakeBody.first.bottom))
            BOTTOM -> snakeBody.addFirst(Rect(snakeBody.first.left - 50, snakeBody.first.top, snakeBody.first.right - 50, snakeBody.first.top + 50))
        }
        // Dimension to remove for this part of the snake.
        snakeTailRemove.addFirst(RIGHT)
        // If eaten apple, don't move tail, invalidate, and return.
        if (eatApple()) {
            invalidate()
            return
        }
        // We move the tail after the head is updated.
        moveTail()
        // Update the canvas.
        invalidate()
    }
    // Method to set snake direction called from the activity.
    fun setSnakeUp(){
        // We store that there has been a direction change.
        directionChange = true
        // Set snake to new direction
        right = false
        left = false
        up = true
        down = false
        // Depending on the orientation of the original head, we update the snake with a new head (Rect).
        // Once the user presses a direction button, a new head (rect) will be generated above the hold head.
        // This will be done outside of the scope of the moveSnake() method to all a more accurate real time user input.
        when (snakeTailRemove.peek()) {
            LEFT -> snakeBody.addFirst(Rect(snakeBody.first.right - 50, snakeBody.first.top - 50, snakeBody.first.right, snakeBody.first.bottom - 50))
            RIGHT -> snakeBody.addFirst(Rect(snakeBody.first.left, snakeBody.first.top - 50, snakeBody.first.left + 50, snakeBody.first.bottom - 50))
        }
        // Dimension to remove for this part of the snake.
        snakeTailRemove.addFirst(BOTTOM)
        // If eaten apple, don't move tail, invalidate, and return.
        if (eatApple()) {
            invalidate()
            return
        }
        // We move the tail after the head is updated.
        moveTail()
        // Update the canvas.
        invalidate()
    }
    // Method to set snake direction called from the activity.
    fun setSnakeDown(){
        // We store that there has been a direction change.
        directionChange = true
        // Set snake to new direction
        right = false
        left = false
        up = false
        down = true
        // Depending on the orientation of the original head, we update the snake with a new head (Rect).
        // Once the user presses a direction button, a new head (rect) will be generated below the hold head.
        // This will be done outside of the scope of the moveSnake() method to all a more accurate real time user input.
        when (snakeTailRemove.peek()) {
            LEFT -> snakeBody.addFirst(Rect(snakeBody.first.right - 50, snakeBody.first.top + 50, snakeBody.first.right, snakeBody.first.bottom + 50))
            RIGHT -> snakeBody.addFirst(Rect(snakeBody.first.left, snakeBody.first.top + 50, snakeBody.first.left + 50, snakeBody.first.bottom + 50))
        }
        // Dimension to remove for this part of the snake.
        snakeTailRemove.addFirst(TOP)
        // If eaten apple, don't move tail, invalidate, and return.
        if (eatApple()) {
            invalidate()
            return
        }
        // We move the tail after the head is updated.
        moveTail()
        // Update the canvas.
        invalidate()
    }
    private fun moveHead() {
        // Based on the heads direction, we increase the dimension by 50.
        if (right) snakeBody.first.right += 50
        if (left) snakeBody.first.left -= 50
        if (up) snakeBody.first.top -= 50
        if (down) snakeBody.first.bottom += 50
    }
    private fun eatApple(): Boolean {
        // Based on the heads direction, if it matches the apples same corresponding dimension
        // then the snake eats the apple.
        if (right && snakeBody.first.right == apple.right && snakeBody.first.bottom == apple.bottom
            && snakeBody.first.top == apple.top) return true
        if (left && snakeBody.first.left == apple.left && snakeBody.first.bottom == apple.bottom
            && snakeBody.first.top == apple.top) return true
        if (up && snakeBody.first.top == apple.top && snakeBody.first.left == apple.left
            && snakeBody.first.right == apple.right) return true
        if (down && snakeBody.first.bottom == apple.bottom && snakeBody.first.left == apple.left
            && snakeBody.first.right == apple.right) return true
        return false
    }
    private fun moveTail() {
        // We check the tail's dimension to move from, and we update accordingly
        when (snakeTailRemove.last) {
            LEFT -> snakeBody.last.left += 50
            RIGHT -> snakeBody.last.right -= 50
            TOP -> snakeBody.last.top += 50
            BOTTOM -> snakeBody.last.bottom -= 50
        }
        //
        if (snakeBody.last.left == snakeBody.last.right || snakeBody.last.top == snakeBody.last.bottom) {
            snakeBody.removeLast()
            snakeTailRemove.removeLast()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(apple, applePaint)
        for (i in snakeBody) canvas.drawRect(i, snakePaint)
    }

}