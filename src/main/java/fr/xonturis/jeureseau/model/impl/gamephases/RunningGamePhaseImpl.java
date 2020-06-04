package fr.xonturis.jeureseau.model.impl.gamephases;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.Game;
import fr.xonturis.jeureseau.model.GamePhase;
import fr.xonturis.jeureseau.model.impl.*;
import fr.xonturis.jeureseau.model.impl.game.NeutronGameImpl;
import fr.xonturis.jeureseau.model.impl.game.PlayerImpl;
import fr.xonturis.jeureseau.network.NetworkAPI;
import fr.xonturis.jeureseau.network.packets.Packet;
import fr.xonturis.jeureseau.network.server.GameSocketServer;
import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Xonturis on 5/29/2020.
 */
public class RunningGamePhaseImpl extends GamePhase {

    public RunningGamePhaseImpl(Game parentGame) {
        super(parentGame);
    }

    @SneakyThrows
    @Override
    public void play() {
        NeutronGameImpl game = (NeutronGameImpl) getParentGame();
        PlayerImpl actualPlayer = (PlayerImpl) game.getActualPlayer();
        AtomicReference<Board> board = new AtomicReference<>(game.getBoard());

        sendBoard(board.get());
        AtomicReference<Callback<Move>> processMove = new AtomicReference<>();

        processMove.set(move -> {
            GameLogger.log("Processing move...");

            Pawn pawn = board.get().getPawn(move.getPawnUUID());
            if (pawn == null) {
                game.errorGame();
                return;
            }

            Direction direction = move.getDirection();
            if (!board.get().canMovePawn(pawn, direction)) {
                game.errorGame();
                return;
            }

            board.get().movePawn(pawn, direction);
            sendBoard(board.get());

            // Was neutron move ?
            if (pawn.isNeutron()) {

                if (pawn.getY() == 4) { // Player one wins
                    GameSocketServer.getINSTANCE().getExecutor().submit(((PlayerImpl) game.getPlayerOne())::sv_sendWin);
                    GameSocketServer.getINSTANCE().getExecutor().submit(((PlayerImpl) game.getPlayerTwo())::sv_sendLose);
                    System.exit(1);
                } else if (pawn.getY() == 0) { // Player two wins
                    GameSocketServer.getINSTANCE().getExecutor().submit(((PlayerImpl) game.getPlayerOne())::sv_sendLose);
                    GameSocketServer.getINSTANCE().getExecutor().submit(((PlayerImpl) game.getPlayerTwo())::sv_sendWin);
                    System.exit(1);
                }

                GameSocketServer.getINSTANCE().getExecutor().submit(() -> actualPlayer.sv_askForNextMove(board.get(), processMove.get()));
            } else {
                GameSocketServer.getINSTANCE().getExecutor().submit(RunningGamePhaseImpl.this::end);
            }
        });

        if (game.getRoundNo() == 0) {
            actualPlayer.sv_askForNextMove(board.get(), processMove.get());
        } else {
            actualPlayer.sv_askForNeutronMove(board.get(), processMove.get());
        }
    }

    private void sendBoard(Board board) {
        AtomicReference<Board> sentBoard = new AtomicReference<>(new Board(board));
        NetworkAPI.sendPacketToClients(new Packet("printClientBoard").setObject("board", sentBoard.get()));
        sentBoard.get().printServerBoard();
    }

    @Override
    public void end() {
        ((NeutronGameImpl) getParentGame()).nextRound();
    }
}
