package fr.xonturis.jeureseau.model.impl.game;

import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.Callback;
import fr.xonturis.jeureseau.model.impl.Move;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class PlayerImpl extends Player {
    public PlayerImpl(String playerName) {
        super(playerName);
    }

    public void askForNextMove(Callback<Move> future) {
        // Todo implementer le réseau
    }
}
