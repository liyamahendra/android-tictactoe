package com.mahendra.tictactoe.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mahendra.tictactoe.R;
import com.mahendra.tictactoe.core.TicTacToe;
import com.mahendra.tictactoe.utils.DIFFICULTY;

/**
 * Created by mahendraliya on 26/07/16.
 */
public class GameActivity extends BaseActivity {
    private Context mContext;

    private TextView lblDifficultyLevel;
    private Button btnGame0, btnGame1, btnGame2, btnGame3, btnGame4, btnGame5, btnGame6, btnGame7, btnGame8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mContext = this;

        setupLayout();
        setupEventListeners();

        if(TicTacToe.getInstance().isComputerPlayingFirst()) {
            askComputerToPerformStep();
        }
    }

    private void setupLayout() {
        lblDifficultyLevel = (TextView) findViewById(R.id.lblDifficultyLevel);

        btnGame0 = (Button) findViewById(R.id.btnGame0);
        btnGame1 = (Button) findViewById(R.id.btnGame1);
        btnGame2 = (Button) findViewById(R.id.btnGame2);
        btnGame3 = (Button) findViewById(R.id.btnGame3);
        btnGame4 = (Button) findViewById(R.id.btnGame4);
        btnGame5 = (Button) findViewById(R.id.btnGame5);
        btnGame6 = (Button) findViewById(R.id.btnGame6);
        btnGame7 = (Button) findViewById(R.id.btnGame7);
        btnGame8 = (Button) findViewById(R.id.btnGame8);

        DIFFICULTY difficulty = TicTacToe.getInstance().getDifficultyLevel();
        if(difficulty == DIFFICULTY.EASY) {
            lblDifficultyLevel.setText(new StringBuilder(getString(R.string.lbl_difficulty_level_prefix)).append(" ").append(getString(R.string.lbl_difficulty_level_easy)));
        } else if(difficulty == DIFFICULTY.MEDIUM) {
            lblDifficultyLevel.setText(new StringBuilder(getString(R.string.lbl_difficulty_level_prefix)).append(" ").append(getString(R.string.lbl_difficulty_level_medium)));
        } else if(difficulty == DIFFICULTY.DIFFICULT) {
            lblDifficultyLevel.setText(new StringBuilder(getString(R.string.lbl_difficulty_level_prefix)).append(" ").append(getString(R.string.lbl_difficulty_level_difficult)));
        }

        TicTacToe.getInstance().startNewGame();
    }

    private void setupEventListeners() {
        Events events = new Events();

        btnGame0.setOnClickListener(events.gameButtonClickListener);
        btnGame1.setOnClickListener(events.gameButtonClickListener);
        btnGame2.setOnClickListener(events.gameButtonClickListener);
        btnGame3.setOnClickListener(events.gameButtonClickListener);
        btnGame4.setOnClickListener(events.gameButtonClickListener);
        btnGame5.setOnClickListener(events.gameButtonClickListener);
        btnGame6.setOnClickListener(events.gameButtonClickListener);
        btnGame7.setOnClickListener(events.gameButtonClickListener);
        btnGame8.setOnClickListener(events.gameButtonClickListener);
    }

    private class Events {
        View.OnClickListener gameButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int position = Integer.parseInt(v.getTag().toString());

                // Check if the button is already used
                if(position == -1) {
                    return;
                }

                ((Button) v).setText(TicTacToe.USER_SYMBOL);
                TicTacToe.getInstance().updateGameMove(position, TicTacToe.USER_SYMBOL);

                if(TicTacToe.getInstance().isGameOver()) {
                    showToastLong(getString(R.string.lbl_you_won));
                    lblDifficultyLevel.setText(getString(R.string.lbl_you_won));
                    finishGame();
                } else if(TicTacToe.getInstance().getMovesMade() == 9) {
                    showToastLong(getString(R.string.lbl_game_draw));
                    lblDifficultyLevel.setText(getString(R.string.lbl_game_draw));
                    finishGame();
                } else {
                    // Mark as used
                    v.setTag("-1");
                    askComputerToPerformStep();
                }
            }
        };
    }

    private void askComputerToPerformStep() {
        int movePosition = TicTacToe.getInstance().getNextMove();

        switch (movePosition) {
            case 0:
                btnGame0.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame0.setTag("-1");
                break;

            case 1:
                btnGame1.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame1.setTag("-1");
                break;

            case 2:
                btnGame2.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame2.setTag("-1");
                break;

            case 3:
                btnGame3.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame3.setTag("-1");
                break;

            case 4:
                btnGame4.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame4.setTag("-1");
                break;

            case 5:
                btnGame5.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame5.setTag("-1");
                break;

            case 6:
                btnGame6.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame6.setTag("-1");
                break;

            case 7:
                btnGame7.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame7.setTag("-1");
                break;

            case 8:
                btnGame8.setText(TicTacToe.COMPUTER_SYMBOL);
                btnGame8.setTag("-1");
                break;
        }

        TicTacToe.getInstance().updateGameMove(movePosition, TicTacToe.COMPUTER_SYMBOL);

        if(TicTacToe.getInstance().isGameOver()) {
            showToastLong(getString(R.string.lbl_you_lost));
            lblDifficultyLevel.setText(getString(R.string.lbl_you_lost));
            finishGame();
        } else if(TicTacToe.getInstance().getMovesMade() == 9) {
            showToastLong(getString(R.string.lbl_game_draw));
            lblDifficultyLevel.setText(getString(R.string.lbl_game_draw));
            finishGame();
        }
    }

    private void finishGame() {
        btnGame0.setEnabled(false);
        btnGame1.setEnabled(false);
        btnGame2.setEnabled(false);
        btnGame3.setEnabled(false);
        btnGame4.setEnabled(false);
        btnGame5.setEnabled(false);
        btnGame6.setEnabled(false);
        btnGame7.setEnabled(false);
        btnGame8.setEnabled(false);
    }
}
