package com.mahendra.tictactoe.core;

import com.mahendra.tictactoe.utils.DIFFICULTY;
import com.mahendra.tictactoe.utils.Utils;

import java.util.ArrayList;

/**
 * Created by mahendraliya on 25/07/16.
 */
public class TicTacToe {
    private static TicTacToe instance;
    public static final String COMPUTER_SYMBOL = "O";
    public static final String USER_SYMBOL = "X";

    private DIFFICULTY difficultyLevel;
    private int totalLogicalMoves;
    private int logicalMovesMade;
    private int movesMade;

    private boolean computerPlayingFirst = true;

    private String[] game;

    private TicTacToe() {

    }

    public static TicTacToe getInstance() {
        if(instance == null) {
            instance = new TicTacToe();
        }

        return instance;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setDifficultyLeve(DIFFICULTY level) {
        this.difficultyLevel = level;
    }

    public DIFFICULTY getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setComputerPlayingFirst(boolean isComputerPlayingFirst) {
        this.computerPlayingFirst = isComputerPlayingFirst;
    }

    public boolean isComputerPlayingFirst() {
        return computerPlayingFirst;
    }

    public void startNewGame() {
        this.game = new String[9];
        this.logicalMovesMade = 0;
        this.movesMade = 0;

        determineLogicalMovesCount();
    }

    public int getNextMove() {
        if(logicalMovesMade >= totalLogicalMoves) {
            return getRandomMove();
        } else {
            return getLogicalMove();
        }
    }

    public void updateGameMove(int position, String symbol) {
        if(position >= 0 && position <= 8) {
            game[position] = symbol;
            movesMade++;
        }
    }

    public boolean isGameOver() {
        return isRowFilledWithSameContent() || isColumnFilledWithSameContent() || isDiagonalFilledWithSameContent();
    }

    // Private Methods

    private void determineLogicalMovesCount() {
        if(difficultyLevel == DIFFICULTY.EASY) {
            totalLogicalMoves = 1;
        } else if(difficultyLevel == DIFFICULTY.MEDIUM) {
            totalLogicalMoves = 3;
        } else if(difficultyLevel == DIFFICULTY.DIFFICULT) {
            if(computerPlayingFirst) {
                totalLogicalMoves = 5;
            } else {
                totalLogicalMoves = 4;
            }
        }
    }

    private int getRandomMove() {
        ArrayList<Integer> vacantPositions = getVacantPositions();
        int randomPosition = Utils.getRandomValue(vacantPositions.size());
        return vacantPositions.get(randomPosition);
    }

    private ArrayList<Integer> getVacantPositions() {
        ArrayList<Integer> vacantPositions = new ArrayList<>();
        for(int i=0; i<game.length; i++){
            if(game[i] == null) {
                vacantPositions.add(Integer.valueOf(i));
            }
        }

        return vacantPositions;
    }

    private int getLogicalMove() {
        /*  A game can only be own if minimum of 4 steps are completed and
            If ours is the 4th turn, we need to determine the move in such a way
            that the 5th move doesn't result into a loss.

            Alternatively, if computer makes the first move, we can have a winning move
            immediately after the fourth turn.
        */
        logicalMovesMade++;

        if(movesMade <= 2) {
            return getCenterOrCornerPosition();
        } else { //if(movesMade >= 3) {
            int move = -1;
            if(movesMade >= 4) {
                move = getMoveToWin();
                System.out.println("*** Move from getMoveToWin: "  + move + " ***");
                if(move != -1) {
                    return move;
                }
            }

            move = getMoveToPreventLose();
            System.out.println("*** Move from getMoveToPreventLose: "  + move + " ***");
            if(move != -1) {
                return move;
            }

            if(movesMade <= 4) {
                move = getMoveToLayTrap();
                System.out.println("*** Move from getMoveToLayTrap: "  + move + " ***");
                if(move != -1) {
                    return move;
                }
            }

            move = getFallbackMove();
            System.out.println("*** Move from getFallbackMove: "  + move + " ***");
            return move;
        }
    }

    private int getCenterOrCornerPosition() {
        // Check if center position is empty, if yes - return it
        if(game[4] == null) {
            return 4;
        }

        ArrayList<Integer> vacantCornerPosition = new ArrayList<>();
        if(game[0] == null) {
            vacantCornerPosition.add(Integer.valueOf(0));
        }
        if(game[2] == null) {
            vacantCornerPosition.add(Integer.valueOf(2));
        }
        if(game[6] == null) {
            vacantCornerPosition.add(Integer.valueOf(6));
        }
        if(game[8] == null) {
            vacantCornerPosition.add(Integer.valueOf(8));
        }

        if(vacantCornerPosition.size() > 0) {
            int randomPosition = Utils.getRandomValue(vacantCornerPosition.size());
            System.out.println("*** random corner position: " + randomPosition + " ***");
            return vacantCornerPosition.get(randomPosition);
        } else {
            return -1;
        }
    }

    private int getMoveToLayTrap() {
        /*
            If the User's 4th Move is at any of the positions: 1, 3, 5, 7 the we can lay a trap.

            Trap can be formed in any of the following ways:
             00x
             0x0
             00x

             000
             0x0
             x0x

             x00
             0x0
             x00

             x0x
             0x0
             000
         */

        if(game[1] == USER_SYMBOL || game[3] == USER_SYMBOL || game[5] == USER_SYMBOL || game[7] == USER_SYMBOL) {
            return getCenterOrCornerPosition();
        }

        return -1;
    }

    private int getMoveToPreventLose() {

        // Cell 0
        if(
                (game[1] != null && game[1].equalsIgnoreCase(game[2])
                        || (game[3] != null && game[3].equalsIgnoreCase(game[6]))
                        || (game[4] != null && game[4].equalsIgnoreCase(game[8])))
                        && (game[0] == null)
                ) {
            return 0;
        }

        // Cell 1
        if(
                (game[0] != null && game[0].equalsIgnoreCase(game[2])
                        || (game[4] != null && game[4].equalsIgnoreCase(game[7])))
                        && (game[1] == null)
                ) {
            return 1;
        }

        // Cell 2
        if(
                (game[0] != null && game[0].equalsIgnoreCase(game[1])
                        || (game[5] != null && game[5].equalsIgnoreCase(game[8]))
                        || (game[4] != null && game[4].equalsIgnoreCase(game[6])))
                        && (game[2] == null)
                ) {
            return 2;
        }

        // Cell 3
        if(
                (game[0] != null && game[0].equalsIgnoreCase(game[6])
                        || (game[4] != null && game[4].equalsIgnoreCase(game[5])))
                        && (game[3] == null)
                ) {
            return 3;
        }

        // Cell 4
        if(
                (game[1] != null && game[1].equalsIgnoreCase(game[7])
                        || (game[3] != null && game[3].equalsIgnoreCase(game[5]))
                        || (game[0] != null && game[0].equalsIgnoreCase(game[8]))
                        || (game[2] != null && game[2].equalsIgnoreCase(game[6])))

                        && (game[4] == null)
                ) {
            return 4;
        }

        // Cell 5
        if(
                (game[2] != null && game[2].equalsIgnoreCase(game[8])
                        || (game[3] != null && game[3].equalsIgnoreCase(game[4]))
                        && (game[5] == null))
                ) {
            return 5;
        }

        // Cell 6
        if(
                (game[0] != null && game[0].equalsIgnoreCase(game[3])
                        || (game[7] != null && game[7].equalsIgnoreCase(game[8]))
                        || (game[4] != null && game[4].equalsIgnoreCase(game[2])))
                        && (game[6] == null)
                ) {
            return 6;
        }

        // Cell 7
        if(
                (game[6] != null && game[6].equalsIgnoreCase(game[8])
                        || (game[1] != null && game[1].equalsIgnoreCase(game[4])))
                        && (game[7] == null)
                ) {
            return 7;
        }

        // Cell 8
        if(
                (game[6] != null && game[6].equalsIgnoreCase(game[7])
                        || (game[0] != null && game[0].equalsIgnoreCase(game[4]))
                        || (game[2] != null && game[2].equalsIgnoreCase(game[5])))
                        && (game[8] == null)
                ) {
            return 8;
        }

        return -1;
    }

    private int getMoveToWin() {
        // Rows
        // ---------------
        if(game[0] == null && (game[1] != null && game[1].equalsIgnoreCase(game[2]) && game[1].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 0;
        }

        if(game[1] == null && (game[0] != null && game[0].equalsIgnoreCase(game[2]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 1;
        }

        if(game[2] == null && (game[0] != null && game[0].equalsIgnoreCase(game[1]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 2;
        }

        if(game[3] == null && (game[4] != null && game[4].equalsIgnoreCase(game[5]) && game[4].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 3;
        }

        if(game[4] == null && (game[3] != null && game[3].equalsIgnoreCase(game[5]) && game[3].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 4;
        }

        if(game[5] == null && (game[3] != null && game[3].equalsIgnoreCase(game[4]) && game[3].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 5;
        }

        if(game[6] == null && (game[7] != null && game[7].equalsIgnoreCase(game[8]) && game[7].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 6;
        }

        if(game[7] == null && (game[6] != null && game[6].equalsIgnoreCase(game[8]) && game[6].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 7;
        }

        if(game[8] == null && (game[6] != null && game[6].equalsIgnoreCase(game[7]) && game[6].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 8;
        }

        // Columns
        // ---------------
        if(game[0] == null && (game[3] != null && game[3].equalsIgnoreCase(game[6]) && game[3].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 0;
        }
        if(game[3] == null && (game[0] != null && game[0].equalsIgnoreCase(game[6]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 3;
        }
        if(game[6] == null && (game[0] != null && game[0].equalsIgnoreCase(game[3]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 6;
        }

        if(game[1] == null && (game[4] != null && game[4].equalsIgnoreCase(game[7]) && game[4].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 1;
        }
        if(game[4] == null && (game[1] != null && game[1].equalsIgnoreCase(game[7]) && game[1].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 4;
        }
        if(game[7] == null && (game[1] != null && game[1].equalsIgnoreCase(game[4]) && game[1].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 7;
        }

        if(game[2] == null && (game[5] != null && game[5].equalsIgnoreCase(game[8]) && game[5].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 2;
        }
        if(game[5] == null && (game[2] != null && game[2].equalsIgnoreCase(game[8]) && game[2].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 5;
        }
        if(game[8] == null && (game[2] != null && game[2].equalsIgnoreCase(game[5]) && game[2].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 8;
        }

        // Diagonals
        // ---------------
        if(game[0] == null && (game[4] != null && game[4].equalsIgnoreCase(game[8]) && game[4].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 0;
        }
        if(game[4] == null && (game[0] != null && game[0].equalsIgnoreCase(game[8]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 4;
        }
        if(game[8] == null && (game[0] != null && game[0].equalsIgnoreCase(game[4]) && game[0].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 8;
        }

        if(game[2] == null && (game[4] != null && game[4].equalsIgnoreCase(game[6]) && game[4].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 2;
        }
        if(game[4] == null && (game[2] != null && game[2].equalsIgnoreCase(game[6]) && game[2].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 4;
        }
        if(game[6] == null && (game[2] != null && game[2].equalsIgnoreCase(game[4]) && game[2].equalsIgnoreCase(COMPUTER_SYMBOL))) {
            return 6;
        }

        return -1;
    }

    private int getFallbackMove() {
        // Return corners, if available
        int move = getCenterOrCornerPosition();
        if(move != -1) {
            return move;
        }
        return getRandomMove();
    }

    private boolean isRowFilledWithSameContent() {
        boolean isRowFilled = (game[0] != null && game[0].equalsIgnoreCase(game[1]) && game[0].equalsIgnoreCase(game[2]))
                || (game[3] != null && game[3].equalsIgnoreCase(game[4]) && game[3].equalsIgnoreCase(game[5]))
                || (game[6] != null && game[6].equalsIgnoreCase(game[7]) && game[6].equalsIgnoreCase(game[8]));

        return isRowFilled;
    }

    private boolean isColumnFilledWithSameContent() {
        boolean isColumnFilled = (game[0] != null && game[0].equalsIgnoreCase(game[3]) && game[0].equalsIgnoreCase(game[6]))
                || (game[1] != null && game[1].equalsIgnoreCase(game[4]) && game[1].equalsIgnoreCase(game[7]))
                || (game[2] != null && game[2].equalsIgnoreCase(game[5]) && game[2].equalsIgnoreCase(game[8]));

        return isColumnFilled;
    }

    private boolean isDiagonalFilledWithSameContent() {
        boolean isDiagonalFill = (game[0] != null && game[0].equalsIgnoreCase(game[4]) && game[0].equalsIgnoreCase(game[8]))
                || (game[2] != null && game[2].equalsIgnoreCase(game[4]) && game[2].equalsIgnoreCase(game[6]));

        return isDiagonalFill;
    }
}
