package fr.xonturis.jeureseau.model.impl.game;

import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.model.impl.gamephases.EndGamePhaseImpl;
import fr.xonturis.jeureseau.model.impl.gamephases.InitialGamePhaseImpl;
import fr.xonturis.jeureseau.model.impl.gamephases.RunningGamePhaseImpl;
import lombok.Getter;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class NeutronGameImpl extends Game {

    @Getter
    private final Board board;
    @Getter
    private int roundNo = 0;
    @Getter
    private Player actualPlayer;

    public NeutronGameImpl(Player playerOne, Player playerTwo) {
        super();
        this.board = new Board();
        this.actualPlayer = playerOne;
        this.addPlayers(playerOne, playerTwo);
        this.addGamePhases(new InitialGamePhaseImpl(this), new RunningGamePhaseImpl(this), new EndGamePhaseImpl(this));

        startGame();
    }

    public Player getPlayerOne() {
        return players.get(0);
    }

    public Player getPlayerTwo() {
        return players.get(1);
    }

    public void nextRound() {
        roundNo++;
        actualPlayer = players.get(roundNo % 2);
    }

    @Override
    public void errorGame() {
        System.err.println("ERREUR, ACTION IMPOSSIBLE");
        // ToDo implementer la fermeture des connexions
        System.exit(1);
    }
}
