package fr.xonturis.jeureseau.network.client;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.network.NetworkHandler;
import fr.xonturis.jeureseau.network.Packet;
import fr.xonturis.jeureseau.network.PlayerSocket;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        GameLogger.log("Sent init");

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        do {
//            while (objectInputStream.available() == 0){Thread.sleep(10);}

            received = (Packet) objectInputStream.readObject();
            NetworkHandler.handle(this, received);
        } while (!received.getName().equals("stop"));
    }

    @SneakyThrows
    @Override
    public void send(Packet packet) {
        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();
    }
}
