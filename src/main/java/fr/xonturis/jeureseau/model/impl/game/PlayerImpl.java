package fr.xonturis.jeureseau.model.impl.game;

import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.Callback;
import fr.xonturis.jeureseau.model.impl.Move;
import fr.xonturis.jeureseau.network.NetworkAPI;
import fr.xonturis.jeureseau.network.packets.Transaction;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class PlayerImpl extends Player {

    public PlayerImpl(String playerName) {
        super(playerName);
    }

    public void sv_askForNextMove(Callback<Move> future) {
        Transaction<Move> transaction = new Transaction<>(future, "askForNextMove");
        NetworkAPI.sendPacketToClient(this, transaction);
    }
}
