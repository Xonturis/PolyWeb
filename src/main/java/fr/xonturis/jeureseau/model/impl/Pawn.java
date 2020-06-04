package fr.xonturis.jeureseau.model.impl;

import fr.xonturis.jeureseau.Util.AnsiColors;
import fr.xonturis.jeureseau.model.Player;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class Pawn implements Serializable {

    @Getter
    private Player owner;
    @Getter
    private UUID uuid = UUID.randomUUID();
    @Getter
    @Setter
    private Point position;

    public Pawn() {
    }

    public Pawn(Player owner, Point position) {
        this.owner = owner;
        this.position = position;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public void setX(int x) {
        position.x = x;
    }

    public void setY(int y) {
        position.y = y;
    }

    public boolean isOwner(Player player) {
        return player.equals(this.owner);
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "owner=" + owner +
                ", uuid=" + uuid +
                ", position=" + position +
                '}';
    }

    public String getColor() {
        return getOwner() == null ? AnsiColors.ANSI_BLUE : getOwner().getColor();
    }

    public boolean isNeutron() {
        return getOwner() == null;
    }
}
