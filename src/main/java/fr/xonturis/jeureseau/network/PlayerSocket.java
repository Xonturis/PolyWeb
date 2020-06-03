package fr.xonturis.jeureseau.network;

import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.packets.Packet;

import java.net.Socket;

/**
 * Created by Xonturis on 5/31/2020.
 */
public interface PlayerSocket {

    Player getPlayer();

    Socket getSocket();

    void send(Packet packet);

}
