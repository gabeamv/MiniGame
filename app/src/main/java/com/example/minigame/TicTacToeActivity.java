package com.example.minigame;

import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;

// The activity for the Tic-tac-toe mini game.
public class TicTacToeActivity extends AppCompatActivity {

    // Constant indicating empty space on board.
    static final int TIC_TAC_TOE_EMPTY_SPACE = 0;

    // Constants indicating player.
    static final int TIC_TAC_TOE_X = 1;
    static final int TIC_TAC_TOE_O = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We set the content view to the Tic-tac-toe game.
        setContentView(R.layout.layout_tic_tac_toe);
        // We create two buttons for the user to choose what they want to play, X or O.
        Button buttonChooseX = (Button) findViewById(R.id.tic_tac_toe_button_x);
        Button buttonChooseO = (Button) findViewById(R.id.tic_tac_toe_button_o);

        // Handles the buttons that will determine whether the user plays X or O.
        View.OnClickListener playerTypeChoose = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.tic_tac_toe_button_x) {
                    // User plays tic-tac-toe as X. Computer plays as O.
                    playTicTacToe(TIC_TAC_TOE_X, TIC_TAC_TOE_O);
                } else if (v.getId() == R.id.tic_tac_toe_button_o) {
                    // User plays tic-tac-toe as O. Computer plays as X.
                    playTicTacToe(TIC_TAC_TOE_O, TIC_TAC_TOE_X);
                }
            }
        };

        // Set pass in the button handler to the buttons.
        buttonChooseX.setOnClickListener(playerTypeChoose);
        buttonChooseO.setOnClickListener(playerTypeChoose);
    }

    // Method to handle the gameplay of tic-tac-toe.
    private void playTicTacToe(int playerType, int computerType) {
        // Instantiate the game object to start tic-tac-toe.
        TicTacToeGame game = new TicTacToeGame(playerType, computerType);
    }

    // A class that defines an object which will handle the gameplay of tic-tac-toe.
    // TODO: Most likely going to turn this class into just a series of methods. It is redundant for the game to be an object.
    private class TicTacToeGame {

        // Stores the button of the tic-tac-toe board.
        private ImageButton topLeft, topMiddle, topRight, middleLeft, middle, middleRight, bottomLeft,
                bottomMiddle, bottomRight;

        // TEST
        private Button resetBoardButtonTest;

        // Stores the board of the tic-tac-toe game.
        private int[] board;

        // Stores the player type of the player and computer.
        private int playerType, computerType;

        // Constructor for game passes in...
        public TicTacToeGame(int playerType, int computerType) {
            // Display the board for the user.
            displayBoard();

            // Instantiate buttons with their correct button.
            topLeft = (ImageButton) findViewById(R.id.tic_tac_toe_button_top_left);
            topMiddle = (ImageButton) findViewById(R.id.tic_tac_toe_button_top_middle);
            topRight = (ImageButton) findViewById(R.id.tic_tac_toe_button_top_right);
            middleLeft = (ImageButton) findViewById(R.id.tic_tac_toe_button_middle_left);
            middle = (ImageButton) findViewById(R.id.tic_tac_toe_button_middle);
            middleRight = (ImageButton) findViewById(R.id.tic_tac_toe_button_middle_right);
            bottomLeft = (ImageButton) findViewById(R.id.tic_tac_toe_button_bottom_left);
            bottomMiddle = (ImageButton) findViewById(R.id.tic_tac_toe_button_bottom_middle);
            bottomRight = (ImageButton) findViewById(R.id.tic_tac_toe_button_bottom_right);


            // Instantiate the game board.
            board = new int[9];

            // Assign the player type and computer type.
            this.playerType = playerType;
            this.computerType = computerType;

            // Test to reset board.
            resetBoardButtonTest = (Button) findViewById(R.id.reset_board_button_test);
            resetBoardButtonTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetBoard();
                }
            });

            // OnClickListener for TicTacToeGame to handle the tic-tac-toe buttons user input.
            View.OnClickListener chooseBox = new View.OnClickListener() {
                // Button that the player chooses in tic-tac-toe.
                private ImageButton playerButton;

                @Override
                public void onClick(View v) {
                    // Depending on what button is chosen, we will update the array that holds the
                    // data for our board, as well as get a reference to the button.

                    // Top left button is chosen.
                    if (v == topLeft) {
                        // TODO: top left
                        board[0] = playerType;
                        playerButton = topLeft;

                    // Top middle button is chosen.
                    } else if (v == topMiddle) {
                        // TODO: top middle
                        board[1] = playerType;
                        playerButton = topMiddle;

                    // Top right button is chosen.
                    } else if (v == topRight) {
                        // TODO: top right
                        board[2] = playerType;
                        playerButton = topRight;

                    // Middle left button is chosen.
                    } else if (v == middleLeft) {
                        // TODO: middle left
                        board[3] = playerType;
                        playerButton = middleLeft;

                    // Middle button is chosen.
                    } else if (v == middle) {
                        // TODO: middle
                        board[4] = playerType;
                        playerButton = middle;

                    // Middle right button is chosen.
                    } else if (v == middleRight) {
                        // TODO: middle right
                        board[5] = playerType;
                        playerButton = middleRight;

                    // Bottom left button is chosen.
                    } else if (v == bottomLeft) {
                        // TODO: bottom left
                        board[6] = playerType;
                        playerButton = bottomLeft;

                    // Bottom middle button is chosen.
                    } else if (v == bottomMiddle) {
                        // TODO: bottom middle
                        board[7] = playerType;
                        playerButton = bottomMiddle;

                    // Bottom right button is chosen.
                    } else if (v == bottomRight) {
                        // TODO: bottom right
                        board[8] = playerType;
                        playerButton = bottomRight;
                    }

                    // Depending on whether the player chose their play type as X or O, we change
                    // the background of the button.
                    if (playerType == TIC_TAC_TOE_X) {
                        playerButton.setBackgroundResource(R.drawable.tic_tac_toe_x);
                    } else if (playerType == TIC_TAC_TOE_O) {
                        playerButton.setBackgroundResource(R.drawable.tic_tac_toe_o);
                    }

                    // Check if board is filled up by iterating through board..
                    // TODO: will be its own method. Clean up code.
                    boolean isEmpty = true;
                    for (int i : board) {
                        if (i != TIC_TAC_TOE_O && i != TIC_TAC_TOE_X) {
                            isEmpty = false;
                            break;
                        }
                    }
                    if (isEmpty) {
                        resetBoard();
                        return;
                    }


                    // TODO: Needs cleanup. We will most likely use this method to handle the delay of computer move. https://www.w3docs.com/snippets/java/how-to-call-a-method-after-a-delay-in-android.html#:~:text=To%20call%20a%20method%20after%20a%20delay%20in%20Android%2C%20you,Runnable%20after%20the%20specified%20delay.
                    // Computer then makes its move.
                    // ArrayList to hold the empty positions from the board.
                    ArrayList<Integer> emptyBoxIndexes = new ArrayList<Integer>();
                    // Iterate through the array board. If the space of the board is empty, add the index to the array list.
                    for (int i = 0; i < board.length; i++) if (board[i] == TIC_TAC_TOE_EMPTY_SPACE) emptyBoxIndexes.add(i);
                    // Select a random index of the board that is empty. https://www.baeldung.com/java-generating-random-numbers-in-range
                    int randomIndexEmptyBox = (int) (Math.random() * emptyBoxIndexes.size());
                    int randomBoardIndex = emptyBoxIndexes.get(randomIndexEmptyBox);
                    // Update the array board.
                    board[randomBoardIndex] = computerType;

                    // Get the computer box.
                    // Initialize button variable.
                    ImageButton computerButton = new ImageButton(TicTacToeActivity.this);
                    // Get the correct reference based off of the random position.
                    // TODO: Plan on making into method for cleaner look.
                    if (randomBoardIndex == 0) {
                        computerButton = topLeft;
                    } else if (randomBoardIndex == 1) {
                        computerButton = topMiddle;
                    } else if (randomBoardIndex == 2) {
                        computerButton = topRight;
                    } else if (randomBoardIndex == 3) {
                        computerButton = middleLeft;
                    } else if (randomBoardIndex == 4) {
                        computerButton = middle;
                    } else if (randomBoardIndex == 5) {
                        computerButton = middleRight;
                    } else if (randomBoardIndex == 6) {
                        computerButton = bottomLeft;
                    } else if (randomBoardIndex == 7) {
                        computerButton = bottomMiddle;
                    } else if (randomBoardIndex == 8) {
                        computerButton = bottomRight;
                    }

                    // Determine whether computer puts X or O.
                    // TODO: Plan on adding animation and clean up. Maybe make it into method.
                    if (computerType == TIC_TAC_TOE_X) computerButton.setBackgroundResource(R.drawable.tic_tac_toe_x);
                    else if (computerType == TIC_TAC_TOE_O) computerButton.setBackgroundResource(R.drawable.tic_tac_toe_o);

                    // TODO: Check if there is a winner.


                }
            };

            // Set the button handler for all tic-tac-toe board buttons.
            topLeft.setOnClickListener(chooseBox);
            topMiddle.setOnClickListener(chooseBox);
            topRight.setOnClickListener(chooseBox);
            middleLeft.setOnClickListener(chooseBox);
            middle.setOnClickListener(chooseBox);
            middleRight.setOnClickListener(chooseBox);
            bottomLeft.setOnClickListener(chooseBox);
            bottomMiddle.setOnClickListener(chooseBox);
            bottomRight.setOnClickListener(chooseBox);
        }

        // Method of TicTacToeGame to set the app's view to the tic-tac-toe board.
        private void displayBoard() {
            setContentView(R.layout.layout_tic_tac_toe_board);
        }

        // Method of TicTacToeGame to reset the board.
        private void resetBoard() {
            // Fill the array board with zeros to 'empty' it.
            Arrays.fill(board, TIC_TAC_TOE_EMPTY_SPACE);
            // Change the background of the buttons to black.
            topLeft.setBackgroundResource(R.color.black);
            topMiddle.setBackgroundResource(R.color.black);
            topRight.setBackgroundResource(R.color.black);
            middleLeft.setBackgroundResource(R.color.black);
            middle.setBackgroundResource(R.color.black);
            middleRight.setBackgroundResource(R.color.black);
            bottomLeft.setBackgroundResource(R.color.black);
            bottomMiddle.setBackgroundResource(R.color.black);
            bottomRight.setBackgroundResource(R.color.black);
        }
    }

}
