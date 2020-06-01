package fr.xonturis.jeureseau.model.impl.gamephases;

import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.GamePhase;
import fr.xonturis.jeureseau.model.Player;
import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.model.impl.game.NeutronGameImpl;
import fr.xonturis.jeureseau.model.impl.Pawn;

import java.awt.*;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class InitialGamePhaseImpl extends GamePhase {
    public InitialGamePhaseImpl(Game parentGame) {
        super(parentGame);
    }

    @Override
    public void play() {
        NeutronGameImpl game = (NeutronGameImpl) getParentGame();
        Board board = game.getBoard();
        Player playerOne = game.getPlayerOne();
        Player playerTwo = game.getPlayerTwo();

        for (int i = 0; i < 5; i++) {
            Pawn pawn = new Pawn(playerOne, new Point(i, 4)); // Joueur du haut
            board.addPawn(pawn);
        }

        for (int i = 0; i < 5; i++) {
            Pawn pawn = new Pawn(playerTwo, new Point(i, 0)); // Joueur du bas
            board.addPawn(pawn);
        }

        game.nextGamePhase();
        game.playGamePhase();
    }
}
