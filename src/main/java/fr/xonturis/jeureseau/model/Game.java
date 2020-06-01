package fr.xonturis.jeureseau.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Xonturis on 5/29/2020.
 */
public abstract class Game implements Serializable {

    @NotNull
    private final List<GamePhase> gamePhases;
    @NotNull
    protected final List<Player> players;

    @Getter
    @Nullable
    private GamePhase actualGamePhase;
    @Getter
    private int round;

    public Game() {
        this.gamePhases = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public void addPlayers(@NotNull Player... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public void removePlayers(@NotNull Player... players) {
        this.players.removeAll(Arrays.asList(players));
    }

    public void addGamePhases(@NotNull GamePhase... gamePhases) {
        this.gamePhases.addAll(Arrays.asList(gamePhases));
    }

    @NotNull
    public Iterator<Player> getPlayers() {
        return players.iterator();
    }

    @NotNull
    public Iterator<GamePhase> getGamePhases() {
        return gamePhases.iterator();
    }

    protected void startGame() {
        this.gamePhases.get(0).start();
    }

    public void playGamePhase() {
        actualGamePhase.start();
    }

    public void nextGamePhase() {
        actualGamePhase = gamePhases.get(gamePhases.indexOf(actualGamePhase) + 1);
    }

    public void endGame() {
        this.gamePhases.get(this.gamePhases.size() - 1).start();
    }

    public abstract void errorGame();
}
