package fr.xonturis.jeureseau;

import fr.xonturis.jeureseau.controller.cli.CliController;
import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.game.NeutronGameImpl;
import fr.xonturis.jeureseau.model.impl.game.PlayerImpl;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import fr.xonturis.jeureseau.network.server.GameSocketServer;

import java.util.List;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class Appli {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("server")) {
            GameSocketServer server = new GameSocketServer(2);
            List<Player> players = server.getPlayers();
            Player playerOne = players.get(0);
            Player playerTwo = players.get(1);
            Game neutronGame = new NeutronGameImpl(playerOne, playerTwo);
        } else {
            // Todo lancer la gui et tout
            GameSocketClient client = new GameSocketClient("localhost", 8000, new PlayerImpl("Xonturis"));
            CliController cliController = new CliController();
        }
    }

}
