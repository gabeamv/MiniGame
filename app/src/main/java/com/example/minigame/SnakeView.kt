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
    // Screen length and  width in pixels.
    private val screenWidth: Int = context.resources.displayMetrics.widthPixels
    private val screenHeight: Int = context.resources.displayMetrics.heightPixels
    // Each unit of a snake will be a multiple of 50
    private val SNAKE_UNIT: Int = 50
    // Snake board dimensions.
    private val SNAKE_BOARD_LEFT: Int = 0
    private val SNAKE_BOARD_RIGHT: Int = screenWidth
    private val SNAKE_BOARD_TOP: Int = 400
    private val SNAKE_BOARD_BOTTOM: Int = SNAKE_BOARD_TOP + (SNAKE_UNIT * 35)
    // The paint of the snake.
    private var snakePaint: Paint = Paint().apply {color = Color.WHITE}
    // The paint of the apple.
    private var applePaint: Paint = Paint().apply {color = Color.RED}
    // The dimensions of the apple.
    private var apple: Rect = Rect(700, 700, 750, 750)
    // Border
    private val borderLeft: Rect = Rect(SNAKE_BOARD_LEFT, SNAKE_BOARD_TOP, SNAKE_BOARD_LEFT + 50,SNAKE_BOARD_BOTTOM)
    private val borderRight: Rect = Rect(SNAKE_BOARD_RIGHT - 50,SNAKE_BOARD_TOP, SNAKE_BOARD_RIGHT,SNAKE_BOARD_BOTTOM)
    private val borderTop: Rect = Rect(SNAKE_BOARD_LEFT, SNAKE_BOARD_TOP, screenWidth,SNAKE_BOARD_TOP - 50)
    private val borderBottom: Rect = Rect(SNAKE_BOARD_LEFT, SNAKE_BOARD_BOTTOM - 50, SNAKE_BOARD_RIGHT, SNAKE_BOARD_BOTTOM)

    // Stores whether or not the snake has been collided.
    private var isCollided: Boolean = false

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

    // Represents direction of the snake's tail is moving from.
    private val LEFT: String = "left"
    private val RIGHT: String = "right"
    private val TOP: String = "top"
    private val BOTTOM: String = "bottom"

    // Stores whether there has been a direction change, when the user inputs up/down/left/right
    private var directionChange: Boolean = false

    // Stores the score.
    private var score: Int = 0

    // Handler to recursively schedule the snake's movement 16 ms apart.
    private val delay: Handler = Handler(Looper.getMainLooper())
    // Runnable object that will be recursively called to move the snake
    private val game: Runnable = object: Runnable {
        // Method to play the game
        override fun run() {
            // When there has been a direction change,
            if (directionChange) {
                directionChange = false
                // 120 ms delay
                delay.postDelayed(this, 120)
                return
            }
            // Move the snake.
            moveSnake()
            // Check if there is a collision. If there is, 'isCollided' will store true and game will stop.
            if(checkCollision()) return
            // Update the game
            invalidate()
            // 120 ms delay
            delay.postDelayed(this, 120)
        }
    }
    // Initialize the game.
    init {
        // Snake goes right first, snake head is created, snake dimension of the tail that will be removed.
        right = true
        snakeBody.addFirst(Rect(SNAKE_BOARD_LEFT + 100, SNAKE_BOARD_TOP + 50, SNAKE_BOARD_LEFT + 150, SNAKE_BOARD_TOP + 100))
        snakeTailRemove.addLast(LEFT)
    }

    // Method to return a runnable in the activity to start the game.
    fun startGame(): Runnable {return game}
    // Method to move the snake by 50 depending on the direction its takes TODO: update score needed
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
        // If there is a collision, we return and don't invalidate so that the head can't go through
        // the barrier.
        if (checkCollision()) return
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
        // If there is a collision, we return and don't invalidate so that the head can't go through
        // the barrier.
        if (checkCollision()) return
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
        // If there is a collision, we return and don't invalidate so that the head can't go through
        // the barrier.
        if (checkCollision()) return
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
            score++
            invalidate()
            return
        }
        // We move the tail after the head is updated.
        moveTail()
        // If there is a collision, we return and don't invalidate so that the head can't go through
        // the barrier.
        if (checkCollision()) return
        // Update the canvas.
        invalidate()
    }
    private fun moveHead() {
        // Based on the heads direction, we increase the dimension by 50.
        if (right) snakeBody.first.right += SNAKE_UNIT
        if (left) snakeBody.first.left -= SNAKE_UNIT
        if (up) snakeBody.first.top -= SNAKE_UNIT
        if (down) snakeBody.first.bottom += SNAKE_UNIT
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
            LEFT -> snakeBody.last.left += SNAKE_UNIT
            RIGHT -> snakeBody.last.right -= SNAKE_UNIT
            TOP -> snakeBody.last.top += SNAKE_UNIT
            BOTTOM -> snakeBody.last.bottom -= SNAKE_UNIT
        }
        // When the previous head reaches the end, deque it from the snake queue.
        if (snakeBody.last.left == snakeBody.last.right || snakeBody.last.top == snakeBody.last.bottom) {
            snakeBody.removeLast()
            snakeTailRemove.removeLast()
        }
    }
    private fun checkCollision (): Boolean {
        if (snakeBody.first.left < SNAKE_BOARD_LEFT + 50 ||
            snakeBody.first.right > SNAKE_BOARD_RIGHT ||
            snakeBody.first.top < SNAKE_BOARD_TOP ||
            snakeBody.first.bottom > SNAKE_BOARD_BOTTOM - 50) isCollided = true
        for (i in snakeBody) {
            if (i == snakeBody.first) continue
            else if (i.intersect(snakeBody.first)) isCollided = true
        }
        return isCollided
    }
    // TODO: move the apple
    private fun moveApple() {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(borderLeft, snakePaint)
        canvas.drawRect(borderRight, snakePaint)
        canvas.drawRect(borderTop, snakePaint)
        canvas.drawRect(borderBottom, snakePaint)
        canvas.drawRect(apple, applePaint)
        for (i in snakeBody) canvas.drawRect(i, snakePaint)
    }

}