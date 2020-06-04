package fr.xonturis.jeureseau.model.impl;

import lombok.Getter;

/**
 * Created by Xonturis on 5/29/2020.
 */
public enum Direction {

    UP("Haut", 0, -1),
    DOWN("Bas", 0, 1),
    LEFT("Gauche", -1, 0),
    RIGHT("Droite", 1, 0),
    UP_LEFT("Haut Gauche", -1, -1),
    UP_RIGHT("Haut Droite", 1, -1),
    DOWN_LEFT("Bas Gauche", -1, 1),
    DOWN_RIGHT("Bas Droite", 1, 1);

    @Getter
    private int incrX, incrY;
    @Getter
    private String displayName;

    Direction(String displayName, int incrX, int incrY) {
        this.displayName = displayName;
        this.incrX = incrX;
        this.incrY = incrY;
    }

    public static Direction getDirectionFromString(String str) {
        try { // Testing for numbers
            int index = Integer.parseInt(str);
            if (values().length > index) {
                return values()[index];
            } else {
                return null;
            }
        } catch (NumberFormatException ignored) {
        } // That's just not a number :]

        // Testing for dirname
        for (Direction value : values()) {
            if (value.getDisplayName().equalsIgnoreCase(str))
                return value;
        }
        return null;
    }

    public static String getDirectionsDisplay() {
        StringBuilder builder = new StringBuilder("Voici les déplacements possibles :");
        for (int i = 0; i < values().length; i++) {
            builder.append('\n').append(" - ").append(i).append(" ").append(values()[i].getDisplayName());
        }
        return builder.toString();
    }
}
