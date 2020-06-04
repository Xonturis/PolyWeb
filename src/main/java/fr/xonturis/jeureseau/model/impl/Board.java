package fr.xonturis.jeureseau.model.impl;

import fr.xonturis.jeureseau.Util.AnsiColors;
import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class Board implements Serializable {

    private Pawn[][] BOARD;
    @Getter
    private int rev;

    public Board(Board board) {
        this.BOARD = new Pawn[5][5];

        for (int i = 0; i < board.BOARD.length; i++) {
            for (int j = 0; j < board.BOARD[i].length; j++) {
                this.BOARD[i][j] = board.BOARD[i][j];
            }
        }

        this.rev = board.rev;
    }

    public Board() {
        this.BOARD = new Pawn[5][5];
        this.rev = 0;
    }

    @Nullable
    public Pawn getPawn(int x, int y) {
        return BOARD[y][x];
    }

    public void movePawn(Pawn pawn, Direction direction) {
        Point nextPoint = getNextPoint(pawn, direction);
        BOARD[pawn.getY()][pawn.getX()] = null;

        pawn.setPosition(nextPoint);
        BOARD[pawn.getY()][pawn.getX()] = pawn;
        rev++;
    }

    public boolean canMovePawn(Pawn pawn, Direction direction) {
        Point nextPoint = getNextPoint(pawn, direction);
        return !nextPoint.equals(pawn.getPosition());
    }

    private Point getNextPoint(Pawn origin, Direction direction) {
        Point nextPoint;

        int incrX = direction.getIncrX();
        int incrY = direction.getIncrY();

        int originX = origin.getX();
        int originY = origin.getY();

        // Getting next position
        int cursorX = originX + incrX;
        int cursorY = originY + incrY;

        // Getting previous position
        nextPoint = new Point(cursorX - incrX, cursorY - incrY);
        while (inBounds(cursorX, cursorY) && getPawn(cursorX, cursorY) == null) { // Loop while we are in bounds and there is no pawn at the position
            // Getting next position
            cursorX += incrX;
            cursorY += incrY;

            // Getting next position
            nextPoint = new Point(cursorX - incrX, cursorY - incrY);
        }

        return nextPoint;
    }

    private Pawn[] getInDir(Pawn origin, Direction direction) {
        Pawn[] inDir = new Pawn[5];

        int incrX = direction.getIncrX();
        int incrY = direction.getIncrY();

        int originX = origin.getX();
        int originY = origin.getY();

        int cursorX = originX + incrX;
        int cursorY = originY + incrY;

        int index = direction.getIncrX() == 0 ? cursorY : cursorX;
        while (cursorX != originX && cursorY != originY) {
            inDir[index] = BOARD[cursorY][cursorX];
            cursorX += incrX;
            cursorY += incrY;
            index++;

            cursorX %= 5;
            cursorY %= 5;
            index %= 5;
        }

        return inDir;
    }

    private boolean inBounds(int x, int y) {
        return x < 5 && x >= 0 && y < 5 && y >= 0;
    }

    public void addPawn(Pawn pawn) {
        BOARD[pawn.getY()][pawn.getX()] = pawn;
    }

    @Nullable
    public Pawn getPawn(UUID uuid) {
        for (Pawn[] pawns : BOARD)
            for (Pawn pawn : pawns)
                if (pawn != null && pawn.getUuid().equals(uuid))
                    return pawn;
        return null;
    }

    public void printClientBoard() {
        // Print x axis
        String playerColor = GameSocketClient.getINSTANCE().getClientPlayerSocket().getPlayer().getColor();
        GameLogger.log(playerColor + "Ceci est votre couleur" + AnsiColors.ANSI_RESET);
        printServerBoard();
    }

    public void printServerBoard() {
        // Print x axis
//        GameLogger.log("Rev = " + rev);
        GameLogger.log("    0  1  2  3  4");

        // Print actual board
        GameLogger.log("   +==+==+==+==+==+");
        for (int i = 0; i < BOARD.length; i++) {
            StringBuilder line = new StringBuilder(" " + i + " |");
            for (int j = 0; j < BOARD[i].length; j++) {
                Pawn pawn = BOARD[i][j];
                if (pawn == null) {
                    line.append("  |");
                } else {
                    line.append(pawn.getColor()).append(j).append(i).append(AnsiColors.ANSI_RESET).append("|");
                }
            }

            GameLogger.log(line.toString());
            GameLogger.log("   +==+==+==+==+==+");
        }
    }
}
