package fr.xonturis.jeureseau.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Xonturis on 5/29/2020.
 */
public abstract class GamePhase implements Serializable {

    @Getter
    Game parentGame;

    public GamePhase(Game parentGame) {
        this.parentGame = parentGame;
    }

    /**
     * Method called at the begging of the game phase (Optional)
     */
    public void start() {
        play();
    }

    /**
     * Method called after start
     */
    public abstract void play();

    /**
     * Method called at the end of the game phase (Optional)
     */
    public void end() {
    }

}
