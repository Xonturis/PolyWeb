package fr.xonturis.jeureseau.network.server;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.PacketHandler;
import fr.xonturis.jeureseau.network.PacketType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class GameSocketServer extends PacketHandler {

    private final List<ServerPlayerSocket> clients;
    private ServerSocket serverSocket;

    public GameSocketServer() {
        super();

        this.clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(8000);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        listen();
    }

    @SneakyThrows
    private void listen() {
        while (true) {
            Socket socket = serverSocket.accept();
            ServerPlayerSocket serverPlayerSocket = new ServerPlayerSocket(socket);
            clients.add(serverPlayerSocket);
            Thread thread = new Thread(serverPlayerSocket);
            thread.start();
            GameLogger.log("Successfully added new connection");
        }
    }

    @PacketType(packetName = "init")
    private void handleInitPacket(PacketWrapper packetWrapper) {
        GameLogger.log("Handled init");
        Player player = (Player) packetWrapper.getPacket().getObject("player");
        ((ServerPlayerSocket) packetWrapper.getPlayerSocket()).setPlayer(player);
    }

}
