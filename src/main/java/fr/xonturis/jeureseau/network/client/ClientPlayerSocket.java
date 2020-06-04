package fr.xonturis.jeureseau.network.client;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.packets.NetworkHandler;
import fr.xonturis.jeureseau.network.packets.Packet;
import fr.xonturis.jeureseau.network.PlayerSocket;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Xonturis on 5/31/2020.
 */
public class ClientPlayerSocket implements PlayerSocket, Runnable {

    @Getter
    private final Socket socket;
    @Getter
    private Player player;
    @Getter
    private ObjectOutputStream objectOutputStream;

    @SneakyThrows
    public ClientPlayerSocket(@NotNull Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    public ClientPlayerSocket(@NotNull Socket socket, Player player) {
        this(socket);
        this.player = player;
    }

    @SneakyThrows
    @Override
    public void run() {
        Packet received;
        ObjectInputStream objectInputStream;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        send(new Packet("init").setObject("player", player));
        GameLogger.debug("Sent init");

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        do {
            try {
                received = (Packet) objectInputStream.readObject();
                NetworkHandler.handle(this, received);
            } catch (SocketException socketException) {
                if (socketException.getMessage().equals("Connection reset") || socketException.getMessage().equals("socket closed")) {
                    GameLogger.info("Le serveur ferme !!");
                    Thread.sleep(1000);
                    System.exit(1);
                } else {
                    socketException.printStackTrace();
                }
            }
        } while (true);
    }

    @SneakyThrows
    @Override
    public void send(Packet packet) {
        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();
    }
}
