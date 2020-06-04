package fr.xonturis.jeureseau;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.controller.cli.CliController;
import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.game.NeutronGameImpl;
import fr.xonturis.jeureseau.model.impl.game.PlayerImpl;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import fr.xonturis.jeureseau.network.server.GameSocketServer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class Appli {
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        if (argsList.contains("server")) {
            GameSocketServer server = new GameSocketServer(2);
            server.getExecutor().submit(() -> {
                List<Player> players = server.getPlayers();
                Player playerOne = players.get(0);
                Player playerTwo = players.get(1);
                Game neutronGame = new NeutronGameImpl(playerOne, playerTwo);
            });
        } else {
            // Todo lancer la gui et tout
            if (argsList.contains("log")) {
                GameLogger.doesDebug = true;
            }
            Scanner sc = new Scanner(System.in);
            String pseudo, host;
            pseudo = host = "";
            GameLogger.info("Pseudo ?");
            if (sc.hasNext())
                pseudo = sc.nextLine();
            GameLogger.info("Adresse ?");
            if (sc.hasNext())
                host = sc.nextLine();

            if (pseudo.isEmpty() || host.isEmpty()) {
                GameLogger.err("Vous devez avoir un pseudo et un host");
                System.exit(1);
            }
            GameSocketClient client = new GameSocketClient(host, 8000, new PlayerImpl(pseudo));
            CliController cliController = new CliController();
        }
    }

}
