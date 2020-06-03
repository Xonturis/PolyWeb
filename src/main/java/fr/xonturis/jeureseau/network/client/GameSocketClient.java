package fr.xonturis.jeureseau.network.client;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;
import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class GameSocketClient extends PacketHandler {

    @Getter
    private ClientPlayerSocket clientPlayerSocket;
    private final Player player;

    @Getter
    private static GameSocketClient INSTANCE = null;

    public GameSocketClient(String address, int port, Player player) {
        super();

        if (INSTANCE != null) {
            throw new IllegalStateException("Only one instance of GameSocketClient can run at the same time.");
        }
        INSTANCE = this;

        this.player = player;
        try {
            Socket socket = new Socket(address, port);
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
        player.setColor((String) packetWrapper.getPacket().getObject("color"));
    }

}
