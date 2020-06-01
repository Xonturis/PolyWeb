package fr.xonturis.jeureseau.model.impl;

import org.jetbrains.annotations.Nullable;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
@NoArgsConstructor
public class Board {

    private final Pawn[][] BOARD = new Pawn[5][5];

    @Nullable
    private Pawn getPawn(int x, int y) {
        return BOARD[x][y];
    }

    public void movePawn(Pawn pawn, Direction direction) {
        Point nextPoint = getNextPoint(pawn, direction);
        pawn.setPosition(nextPoint);
        BOARD[(int) nextPoint.getX()][(int) nextPoint.getY()] = pawn;
        BOARD[pawn.getX()][pawn.getY()] = null;
    }

    public boolean canMovePawn(Pawn pawn, Direction direction) {
        Point nextPoint = getNextPoint(pawn, direction);
        return !nextPoint.equals(pawn.getPosition());
    }

    private Point getNextPoint(Pawn origin, Direction direction) {
        Point nextPoint = null;

        int incrX = direction.getIncrX();
        int incrY = direction.getIncrY();

        int originX = origin.getX();
        int originY = origin.getY();

        int cursorX = originX + incrX;
        int cursorY = originY + incrY;

        while (inBounds(cursorX, cursorY)) {

            if (getPawn(cursorX, cursorY) != null) {
                nextPoint = new Point(cursorX - incrX, cursorY - incrY);
                break;
            }

            cursorX += incrX;
            cursorY += incrY;
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
            inDir[index] = BOARD[cursorX][cursorY];
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
        BOARD[pawn.getX()][pawn.getY()] = pawn;
    }

    @Nullable
    public Pawn getPawn(UUID uuid) {
        for (Pawn[] pawns : BOARD)
            for (Pawn pawn : pawns)
                if (pawn.getUuid().equals(uuid))
                    return pawn;
        return null;
    }
}
