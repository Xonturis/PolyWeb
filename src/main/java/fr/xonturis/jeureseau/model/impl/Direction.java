package fr.xonturis.jeureseau.model.impl;

import lombok.Getter;

/**
 * Created by Xonturis on 5/29/2020.
 */
public enum Direction {

    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0), UP_LEFT(-1, 1), UP_RIGHT(1, 1), DOWN_LEFT(-1, -1), DOWN_RIGHT(1, -1);

    @Getter
    private int incrX, incrY;

    Direction(int incrX, int incrY) {
        this.incrX = incrX;
        this.incrY = incrY;
    }
}
