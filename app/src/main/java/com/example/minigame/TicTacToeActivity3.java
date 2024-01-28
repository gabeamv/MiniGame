package com.example.minigame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Handler;

public class TicTacToeActivity3 extends AppCompatActivity implements TicTacToeFragment.boardToggle {

    // Constant indicating empty space on board.
    static final int TIC_TAC_TOE_EMPTY_SPACE_NO_WINNER = 0;
    // Constants indicating player.
    static final int TIC_TAC_TOE_X = 1;
    static final int TIC_TAC_TOE_O = 2;

    // Stores the button of choosing X or O.
    private Button buttonChooseX, buttonChooseO;

    // Stores the player type of the player and computer.
    private int playerType, computerType;

    // Reference to fragment.
    TicTacToeFragment fragment;

    // Fragment container.
    FrameLayout fragmentContainer;

    // Stores the boolean value whether the board is visible or not.
    boolean boardIsVisible;

    // Delay handler to handle delays.
    Handler delayHandler = new Handler();


    // Inflate the tic tac toe layout which will prompt the user to choose between X or O.
    public TicTacToeActivity3() {
        super(R.layout.layout_tic_tac_toe);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Store that the board is not visible.
        boardIsVisible = false;

        // We create two buttons for the user to choose what they want to play, X or O.
        buttonChooseX = (Button) findViewById(R.id.tic_tac_toe_button_x);
        buttonChooseO = (Button) findViewById(R.id.tic_tac_toe_button_o);

        // Store reference to the fragment container.
        fragmentContainer = findViewById(R.id.tic_tac_toe_fragment_container_view);

        // Handles the buttons that will determine whether the user plays X or O.
        View.OnClickListener playerTypeChoose = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupGame(v);
            }
        };

        // Set pass in the button handler to the buttons.
        buttonChooseX.setOnClickListener(playerTypeChoose);
        buttonChooseO.setOnClickListener(playerTypeChoose);

    }

    // Method that the TicTacToe fragment will signal this activity make the fragment container visible or gone.
    @Override
    public void toggleBoard(boolean visible) {
        // If the board is visible, fragment container is removed and choosing buttons are visible.
        if (visible) {
            removeFragment();
            buttonChooseO.setVisibility(View.VISIBLE);
            buttonChooseX.setVisibility(View.VISIBLE);
        }
        // If the board is not visible, fragment is initialized and choosing buttons are invisible.
        else {
            buttonChooseO.setVisibility(View.GONE);
            buttonChooseX.setVisibility(View.GONE);
            initializeFragment();
        }
        // Bit flip
        boardIsVisible = !boardIsVisible;
    }

    // Method to initialize fragment.
    private void initializeFragment() {
        // Instantiate the fragment and store it.
        fragment = new TicTacToeFragment();

        // Store the player types of the fragment.
        fragment.setPlayerTypes(playerType, computerType);

        // Initialize the fragment for the board.
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.tic_tac_toe_fragment_container_view, fragment, null)
                .commit();
    }

    // Method to remove fragment.
    private void removeFragment() {
        // Remove fragment for the board.
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }

    // Method to setup the game. Called in the onClick method.
    private void setupGame(View v) {
        // Store playerType and computerType.
        if (v.getId() == R.id.tic_tac_toe_button_x) {
            playerType = TIC_TAC_TOE_X;
            computerType = TIC_TAC_TOE_O;
        }
        // Store playerType and computerType.
        if (v.getId() == R.id.tic_tac_toe_button_o) {
            playerType = TIC_TAC_TOE_O;
            computerType = TIC_TAC_TOE_X;
        }
        // Start the fragment.
        toggleBoard(boardIsVisible);
    }
}


