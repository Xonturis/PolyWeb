package fr.xonturis.jeureseau.network.server;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.packets.Packet;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.PlayerSocket;
import lombok.Getter;
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
    private int neededNumberOfPlayers;

    @Getter
    private static GameSocketServer INSTANCE = null;

    public GameSocketServer(int neededNumberOfPlayers) {
        super();

        if (INSTANCE != null) {
            throw new IllegalStateException("Only one instance of GameSocketServer can run at the same time.");
        }

        this.clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(8000);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        this.neededNumberOfPlayers = neededNumberOfPlayers;
        INSTANCE = this;
        listen();
    }

    @SneakyThrows
    private void listen() {
        Thread newSocketListener = new Thread(() -> {
            while (clients.size() != neededNumberOfPlayers) {
                try {
                    Socket socket = serverSocket.accept();
                    ServerPlayerSocket serverPlayerSocket = new ServerPlayerSocket(socket);
//            clients.add(serverPlayerSocket);
                    Thread thread = new Thread(serverPlayerSocket);
                    thread.start();
                    GameLogger.log("Successfully added new connection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        newSocketListener.start();
        while (clients.size() != neededNumberOfPlayers) {
            Thread.sleep(1);
        }
        newSocketListener.interrupt();
        GameLogger.log("End listening");
    }

    @PacketType(packetName = "init")
    private void handleInitPacket(PacketWrapper packetWrapper) {
        GameLogger.log("Handled init");
        ServerPlayerSocket serverPlayerSocket = ((ServerPlayerSocket) packetWrapper.getPlayerSocket());
        String color = clients.size() == 0 ? "\u001B[32m" : "\u001B[31m";
        Player player = (Player) packetWrapper.getPacket().getObject("player");
        player.setColor(color);
        serverPlayerSocket.setPlayer(player);
        serverPlayerSocket.send(new Packet("init").setObject("color", color));
        clients.add(serverPlayerSocket);
    }

    /**
     * Gives the non null players
     *
     * @return a list of players
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (ServerPlayerSocket client : clients) {
            if (client.getPlayer() != null) {
                players.add(client.getPlayer());
            }
        }
        return players;
    }

    public PlayerSocket getPlayerSocket(Player player) {
        for (ServerPlayerSocket client : clients) {
            if (client.getPlayer().equals(player))
                return client;
        }
        return null;
    }

    public List<ServerPlayerSocket> getPlayerSockets() {
        return clients;
    }
}
