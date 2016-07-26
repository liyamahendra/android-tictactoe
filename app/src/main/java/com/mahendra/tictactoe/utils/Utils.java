package com.mahendra.tictactoe.utils;

import java.util.Random;

/**
 * Created by mahendraliya on 26/07/16.
 */
public class Utils {
    public static int getRandomValue(int max) {
        Random rand = new Random();
        return rand.nextInt(max); // This will is left inclusive
    }
}
