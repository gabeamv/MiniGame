package com.example.minigame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;

// Class for the functionality of the mini game app home page. User will choose from a set of
// displayed mini games to play.
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The layout will be the home page.
        setContentView(R.layout.layout_home);

        // We create a button to start the Tic-tac-toe activity.
        Button buttonTicTacToe = (Button) findViewById(R.id.home_tic_tac_toe_button);
        buttonTicTacToe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create an intent to go to the Tic-tac-toe activity, then start it.
                Intent startTicTacToe = new Intent(HomeActivity.this, TicTacToeActivity3.class);
                startActivity(startTicTacToe);
                // No animation when changing activities.
                overridePendingTransition(0, 0);
            }
        });

        // We create a button to start the Snake activity.
        Button buttonSnake = (Button) findViewById(R.id.snake_button);
        buttonSnake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create an intent to go to the Snake activity, then start it.
                Intent startSnake = new Intent(HomeActivity.this, SnakeActivity.class);
                startActivity(startSnake);
                // No animation when changing activities.
                overridePendingTransition(0, 0);
            }
        });

    }
}
