package com.example.minigame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Handler;

public class TicTacToeActivity2 extends AppCompatActivity {
    // Constant indicating empty space on board.
    static final int TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER = 0;
    // Constants indicating player.
    static final int TIC_TAC_TOE_X = 1;
    static final int TIC_TAC_TOE_O = 2;

    // Stores the button of the tic-tac-toe board.
    private ImageButton topLeft, topMiddle, topRight, middleLeft, middle, middleRight, bottomLeft,
            bottomMiddle, bottomRight;

    // Test button to reset the board.
    private Button resetBoardButtonTest;

    // Stores the board of the tic-tac-toe game. From left to right, top to bottom, the board boxes
    // where the Xs and Os will go will be 0 indexed. For instance,  the top left position will be
    // at index 0 and the bottom right position will be at index 8.
    private int[] board;

    // Stores the player type of the player and computer.
    private int playerType, computerType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We set the content view to the Tic-tac-toe game.
        setContentView(R.layout.layout_tic_tac_toe);
        // We create two buttons for the user to choose what they want to play, X or O.
        Button buttonChooseX = findViewById(R.id.tic_tac_toe_button_x);
        Button buttonChooseO = (Button) findViewById(R.id.tic_tac_toe_button_o);

        // Handles the buttons that will determine whether the user plays X or O.
        View.OnClickListener playerTypeChoose = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.tic_tac_toe_button_x) {
                    // Set the player and computer type.
                    playerType = TIC_TAC_TOE_X;
                    computerType = TIC_TAC_TOE_O;
                    // User plays tic-tac-toe as X. Computer plays as O.
                    playTicTacToe();
                } else if (v.getId() == R.id.tic_tac_toe_button_o) {
                    // Set the player and computer type.
                    playerType = TIC_TAC_TOE_O;
                    computerType = TIC_TAC_TOE_X;
                    // User plays tic-tac-toe as O. Computer plays as X.
                    playTicTacToe();
                }
            }
        };

        // Set pass in the button handler to the buttons.
        buttonChooseX.setOnClickListener(playerTypeChoose);
        buttonChooseO.setOnClickListener(playerTypeChoose);
    }

    // Method to handle the gameplay of tic-tac-toe.
    private void playTicTacToe() {
        // Display the board for the user by changing the layout.
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

        // Test button to reset board with onClickListener.
        resetBoardButtonTest = (Button) findViewById(R.id.reset_board_button_test);
        resetBoardButtonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });

        // OnClickListener for TicTacToeGame to handle the tic-tac-toe buttons user input.
        View.OnClickListener chooseBox = new View.OnClickListener() {
            // Button that will store the reference to the button that the user tapped.
            private ImageButton playerButton, computerButton;

            // Handler object to handle the user input. There will be a delay between when the
            // user taps a tic-tac-toe box and the computer making their move.
            private final Handler delayHandler = new Handler();

            // Runnable object that has the functionality of setting the background resource of a computer's chosen
            // tic-tac-toe box to X or O. This function will be delayed by some seconds after the user makes their move.
            // TODO:
            private final Runnable computerMove = new Runnable() {
                @Override
                public void run() {
                    // Determine whether computer puts X or O.
                    // TODO: Plan on adding animation and clean up.
                    if (computerType == TIC_TAC_TOE_X) computerButton.setBackgroundResource(R.drawable.tic_tac_toe_x);
                    else if (computerType == TIC_TAC_TOE_O) computerButton.setBackgroundResource(R.drawable.tic_tac_toe_o);

                    // Enabled the board buttons that are still in play.
                    enableUnusedBoardInput();

                    // Check if there is a winner by invoking checkWinner. If there is a winner, checkWinner
                    // will not return 0.
                    if (checkWinner() != 0) {
                        // Update the winner's score. Check winner returns the int value of player type. 0 if no winner.
                        updateWinnerScore(checkWinner());
                        // Reset the board.
                        resetBoard();
                    }
                }
            };

            @Override
            public void onClick(View v) {
                // Depending on what button is chosen, we will update the array that holds the
                // data for our board, as well as get a reference to the button.

                // We first disable all board buttons to prevent user from inputting during the
                // computer's turn.
                disableAllBoardInput();

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


                // Method for player to draw on the board.
                // delayHandler.postDelayed(playerMove, 0);
                // Depending on whether the player chose their play type as X or O, we change
                // the background of the button.
                // TODO: Plan on adding animation and clean up.
                if (playerType == TIC_TAC_TOE_X) {
                    playerButton.setBackgroundResource(R.drawable.tic_tac_toe_x);
                } else if (playerType == TIC_TAC_TOE_O) {
                    playerButton.setBackgroundResource(R.drawable.tic_tac_toe_o);
                }

                // Check if there is a winner. If there is a winner, reset the board, and the onClick method terminates.
                if (checkWinner() != 0) {
                    // Update the winner's score. Check winner returns the int value of player type. 0 if no winner.
                    updateWinnerScore(checkWinner());
                    resetBoard();
                    return;
                }

                // If the board is full, reset the board and terminate the onClick method.
                if (isFull()) {
                    resetBoard();
                    return;
                }


                // TODO: Needs cleanup. We will most likely use this method to handle the delay of computer move.
                // Computer then makes its move.
                // ArrayList to hold the empty positions from the board.
                ArrayList<Integer> emptyBoxIndexes = new ArrayList<Integer>();
                // Iterate through the array board. If the space of the board is empty, add the index to the array list.
                for (int i = 0; i < board.length; i++) if (board[i] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) emptyBoxIndexes.add(i);
                // Select a random index of the board that is empty. https://www.baeldung.com/java-generating-random-numbers-in-range
                int randomIndexEmptyBox = (int) (Math.random() * emptyBoxIndexes.size());
                int randomBoardIndex = emptyBoxIndexes.get(randomIndexEmptyBox);

                // Update the array board.
                board[randomBoardIndex] = computerType;

                // Get the computer box.
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


                // TODO: https://www.w3docs.com/snippets/java/how-to-call-a-method-after-a-delay-in-android.html#:~:text=To%20call%20a%20method%20after%20a%20delay%20in%20Android%2C%20you,Runnable%20after%20the%20specified%20delay.
                // Method for computer to draw on the board. This method will also enable board buttons.
                delayHandler.postDelayed(computerMove, 1000);

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
        Arrays.fill(board, TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER);
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
        // Enable user to input onto the board.
        enableAllBoardInput();
    }

    // Method to check if there is a winner.
    private int checkWinner() {
        // Win from the top horizontal.
        if (board[0] == board[1] && board[1] == board[2] && board[0] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[0];
        // Win from the middle horizontal.
        if (board[3] == board[4] && board[4] == board[5] && board[3] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[3];
        // Win from the bottom horizontal.
        if (board[6] == board[7] && board[7] == board[8] && board[6] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[6];
        // Win from the left vertical.
        if (board[0] == board[3] && board[3] == board[6] && board[0] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[0];
        // Win from the middle vertical.
        if (board[1] == board[4] && board[4] == board[7] && board[1] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[1];
        // Win from the right vertical.
        if (board[2] == board[5] && board[5] == board[8] && board[2] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[2];
        // Win from the top left to bottom right diagonal.
        if (board[0] == board[4] && board[4] == board[8] && board[0] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[0];
        // Win from the bottom left to top right diagonal.
        if (board[6] == board[4] && board[4] == board[2] && board[6] != TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) return board[6];

        // Return that there is no winner (0).
        return 0;
    }

    // Check if board is full by iterating through board.
    private boolean isFull() {
        // Iterate through the entire board going through each box.
        for (int i : board) {
            // If the box does not have an X or an O, the board is not full and return false.
            if (i != TIC_TAC_TOE_O && i != TIC_TAC_TOE_X) {
                return false;
            }
        }
        // The board is full if we can iterate through the entire board.
        return true;
    }

    // Method to disable user input on the board.
    private void disableAllBoardInput() {
        topLeft.setEnabled(false);
        topMiddle.setEnabled(false);
        topRight.setEnabled(false);
        middleLeft.setEnabled(false);
        middle.setEnabled(false);
        middleRight.setEnabled(false);
        bottomLeft.setEnabled(false);
        bottomMiddle.setEnabled(false);
        bottomRight.setEnabled(false);
    }

    // Method to enable user input on the board.
    private void enableAllBoardInput() {
        topLeft.setEnabled(true);
        topMiddle.setEnabled(true);
        topRight.setEnabled(true);
        middleLeft.setEnabled(true);
        middle.setEnabled(true);
        middleRight.setEnabled(true);
        bottomLeft.setEnabled(true);
        bottomMiddle.setEnabled(true);
        bottomRight.setEnabled(true);
    }

    // Method to only enable the buttons that were not pressed.
    private void enableUnusedBoardInput() {
        // If there is an empty space in the board array, we enable the button for that corresponding index.
        if (board[0] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) topLeft.setEnabled(true);
        if (board[1] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) topMiddle.setEnabled(true);
        if (board[2] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) topRight.setEnabled(true);
        if (board[3] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) middleLeft.setEnabled(true);
        if (board[4] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) middle.setEnabled(true);
        if (board[5] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) middleRight.setEnabled(true);
        if (board[6] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) bottomLeft.setEnabled(true);
        if (board[7] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) bottomMiddle.setEnabled(true);
        if (board[8] == TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER) bottomRight.setEnabled(true);
    }

    // Method to update the score of the game. Passes in the integer value of player type. See constants
    // above such as TIC_TAC_TOE_X and TIC_TAC_TOE_O.
    private void updateWinnerScore(int winner) {
        // Variable to store the reference view that contains the score of the winner of the match.
        TextView scoreView;

        // If the winner is player o. Get the reference to the view of the o player.
        if (winner == TIC_TAC_TOE_O) scoreView = (TextView) findViewById(R.id.tic_tac_toe_o_score);

        // If the winner is player x. Get the reference to the view of the x player.
        else scoreView = (TextView) findViewById(R.id.tic_tac_toe_x_score);

        // Parse the text of view into an integer.
        int currScore = Integer.parseInt(scoreView.getText().toString());
        // Update the score by one since they won.
        currScore += 1;
        // Update the TextView.
        scoreView.setText(String.valueOf(currScore));
    }

}
