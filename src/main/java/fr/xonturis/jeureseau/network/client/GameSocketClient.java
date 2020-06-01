package fr.xonturis.jeureseau.network.client;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.Packet;
import fr.xonturis.jeureseau.network.PacketHandler;
import fr.xonturis.jeureseau.network.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class GameSocketClient extends PacketHandler {

    private Socket socket = null;
    private ClientPlayerSocket clientPlayerSocket;
    private Player player;

    public GameSocketClient(String address, int port, Player player) {
        super();

        this.player = player;
        try {
            socket = new Socket(address, port);
            this.clientPlayerSocket = new ClientPlayerSocket(socket, player);
            GameLogger.log("Connected");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Thread thread = new Thread(clientPlayerSocket);
        thread.start();
    }

    @PacketType(packetName = "init")
    private void handleInitPacket(PacketWrapper packetWrapper) {
        GameLogger.log("Received init");
        clientPlayerSocket.send(new Packet("init").setObject("player", player));
    }

}
