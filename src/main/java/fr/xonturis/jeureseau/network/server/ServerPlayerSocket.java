package fr.xonturis.jeureseau.network.server;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.PlayerSocket;
import fr.xonturis.jeureseau.network.packets.NetworkHandler;
import fr.xonturis.jeureseau.network.packets.Packet;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Xonturis on 5/31/2020.
 */
public class ServerPlayerSocket implements PlayerSocket, Runnable {

    @Getter
    private final Socket socket;
    @Getter
    @Setter
    private Player player;

    @Getter
    private ObjectOutputStream objectOutputStream;

    @SneakyThrows
    public ServerPlayerSocket(@NotNull Socket socket) {
        GameLogger.log("New connection");
        this.socket = socket;
        GameLogger.log("Finish init connection");
    }

    @SneakyThrows
    public ServerPlayerSocket(@NotNull Socket socket, Player player) {
        this(socket);
        this.player = player;
    }

    @SneakyThrows
    @Override
    public void run() {
        ObjectInputStream objectInputStream;

        GameLogger.log("Running connection");
        Packet received;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        do {
            received = (Packet) objectInputStream.readObject();
            NetworkHandler.handle(this, received);
        } while (!received.getName().equals("stop"));
    }

    @SneakyThrows
    @Override
    public void send(Packet packet) {
        if (this.objectOutputStream == null)
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();
    }
}
