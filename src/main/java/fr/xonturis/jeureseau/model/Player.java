package fr.xonturis.jeureseau.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
public abstract class Player implements Serializable {

    @Getter
    @Setter
    private String playerName;
    @Getter
    @Setter
    private UUID uuid;
    @Setter
    @Getter
    private String color;

    public Player(String playerName) {
        this.playerName = playerName;
        this.uuid = UUID.randomUUID();
    }

    public Player(String playerName, UUID uuid) {
        this.playerName = playerName;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (getPlayerName() != null ? !getPlayerName().equals(player.getPlayerName()) : player.getPlayerName() != null)
            return false;
        return getUuid() != null ? getUuid().equals(player.getUuid()) : player.getUuid() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlayerName() != null ? getPlayerName().hashCode() : 0;
        result = 31 * result + (getUuid() != null ? getUuid().hashCode() : 0);
        return result;
    }
}
