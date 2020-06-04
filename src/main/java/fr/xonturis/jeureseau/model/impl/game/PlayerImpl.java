package fr.xonturis.jeureseau.model.impl.game;

import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.model.impl.Callback;
import fr.xonturis.jeureseau.model.impl.Move;
import fr.xonturis.jeureseau.network.NetworkAPI;
import fr.xonturis.jeureseau.network.packets.Packet;
import fr.xonturis.jeureseau.network.packets.Transaction;

import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class PlayerImpl extends Player {

    public PlayerImpl(String playerName) {
        super(playerName);
    }

    public PlayerImpl(String playerName, UUID uuid) {
        super(playerName, uuid);
    }

    public void sv_askForNextMove(Board board, Callback<Move> future) {
        Transaction<Move> transaction = new Transaction<>(future, "askForNextMove");
        transaction.setObject("board", board);
        transaction.setObject("neutron", false);
        NetworkAPI.sendPacketToClient(this, transaction);
    }

    public void sv_askForNeutronMove(Board board, Callback<Move> future) {
        Transaction<Move> transaction = new Transaction<>(future, "askForNextMove");
        transaction.setObject("board", board);
        transaction.setObject("neutron", true);
        NetworkAPI.sendPacketToClient(this, transaction);
    }

    public void sv_sendLose() {
        Packet losePacket = new Packet("lose");
        NetworkAPI.sendPacketToClient(this, losePacket);
    }

    public void sv_sendWin() {
        Packet losePacket = new Packet("win");
        NetworkAPI.sendPacketToClient(this, losePacket);
    }
}
