package com.mahendra.tictactoe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.mahendra.tictactoe.R;

import com.mahendra.tictactoe.core.TicTacToe;
import com.mahendra.tictactoe.utils.DIFFICULTY;

public class MainActivity extends BaseActivity {

    private Context mContext;
    private DIFFICULTY selectedLevel = DIFFICULTY.NONE;

    private Button btnStartGame;
    private RadioButton rdEasy, rdMedium, rdDifficult, rdUser, rdComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        setupLayout();
        setupEventListeners();
    }

    private void setupLayout() {
        rdEasy = (RadioButton) findViewById(R.id.rdEasy);
        rdMedium = (RadioButton) findViewById(R.id.rdMedium);
        rdDifficult = (RadioButton) findViewById(R.id.rdDifficult);

        rdUser = (RadioButton) findViewById(R.id.rdUser);
        rdComputer = (RadioButton) findViewById(R.id.rdComputer);

        btnStartGame = (Button) findViewById(R.id.btnStartGame);
    }

    private void setupEventListeners() {
        Events events = new Events();

        rdEasy.setOnCheckedChangeListener(events.easyCheckChangeListener);
        rdMedium.setOnCheckedChangeListener(events.mediumCheckChangeListener);
        rdDifficult.setOnCheckedChangeListener(events.difficultCheckChangeListener);
        btnStartGame.setOnClickListener(events.startGameClickListener);
    }

    private class Events {
        RadioButton.OnCheckedChangeListener easyCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedLevel = DIFFICULTY.EASY;
                }

                btnStartGame.setEnabled(true);
            }
        };

        RadioButton.OnCheckedChangeListener mediumCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedLevel = DIFFICULTY.MEDIUM;
                }

                btnStartGame.setEnabled(true);
            }
        };

        RadioButton.OnCheckedChangeListener difficultCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedLevel = DIFFICULTY.DIFFICULT;
                }

                btnStartGame.setEnabled(true);
            }
        };

        View.OnClickListener startGameClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicTacToe.getInstance().setDifficultyLeve(selectedLevel);
                TicTacToe.getInstance().setComputerPlayingFirst(rdComputer.isChecked());

                Intent gameIntent = new Intent(mContext, GameActivity.class);
                startActivity(gameIntent);
            }
        };
    }
}
