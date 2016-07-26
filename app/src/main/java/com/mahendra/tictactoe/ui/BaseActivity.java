package com.mahendra.tictactoe.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by mahendraliya on 26/07/16.
 */
public class BaseActivity extends AppCompatActivity {

    public void showToastShort(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
