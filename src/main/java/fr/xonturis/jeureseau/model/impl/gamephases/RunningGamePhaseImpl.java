package fr.xonturis.jeureseau.model.impl.gamephases;

import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.GamePhase;
import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.model.impl.Direction;
import fr.xonturis.jeureseau.model.impl.Pawn;
import fr.xonturis.jeureseau.model.impl.game.*;
import fr.xonturis.jeureseau.network.NetworkAPI;
import fr.xonturis.jeureseau.network.packets.Packet;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class RunningGamePhaseImpl extends GamePhase {
    public RunningGamePhaseImpl(Game parentGame) {
        super(parentGame);
    }

    @Override
    public void play() {
        NeutronGameImpl game = (NeutronGameImpl) getParentGame();
        PlayerImpl actualPlayer = (PlayerImpl) game.getActualPlayer();
        Board board = game.getBoard();
        NetworkAPI.sendPacketToClients(new Packet("printBoard").setObject("board", board));
        actualPlayer.sv_askForNextMove(move -> {
            Pawn pawn = board.getPawn(move.getPawnUUID());
            if (pawn == null) {
                game.errorGame();
                return;
            }
            Direction direction = move.getDirection();

            if (!board.canMovePawn(pawn, direction)) {
                game.errorGame();
                return;
            }

            board.movePawn(pawn, direction);
            game.nextRound();
        });
    }

    @Override
    public void end() {
        ((NeutronGameImpl) getParentGame()).nextRound();
    }
}
