package fr.xonturis.jeureseau.network;

import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import fr.xonturis.jeureseau.network.packets.Packet;
import fr.xonturis.jeureseau.network.server.GameSocketServer;
import fr.xonturis.jeureseau.network.server.ServerPlayerSocket;

import java.util.List;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class NetworkAPI {

    public static void sendPacketToClient(Player player, Packet packet) {
        PlayerSocket socket = GameSocketServer.getINSTANCE().getPlayerSocket(player);
        socket.send(packet);
    }

    public static void sendPacketToClients(Packet packet) {
        List<ServerPlayerSocket> sockets = GameSocketServer.getINSTANCE().getPlayerSockets();
        for (ServerPlayerSocket serverPlayerSocket : sockets) {
            serverPlayerSocket.send(packet);
        }
    }

    public static void sendPacketToServer(Packet packet) {
        GameSocketClient.getINSTANCE().getClientPlayerSocket().send(packet);
    }

}
