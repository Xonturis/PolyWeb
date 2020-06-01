package fr.xonturis.jeureseau;

import fr.xonturis.jeureseau.model.impl.game.PlayerImpl;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import fr.xonturis.jeureseau.network.server.GameSocketServer;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class Appli {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("server")) {
            GameSocketServer server = new GameSocketServer();
        } else {
            // Todo lancer la gui et tout
            GameSocketClient client = new GameSocketClient("localhost", 8000, new PlayerImpl("Xonturis"));
        }
    }

}