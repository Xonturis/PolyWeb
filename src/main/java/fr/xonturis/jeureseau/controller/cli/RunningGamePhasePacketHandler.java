package fr.xonturis.jeureseau.controller.cli;

import fr.xonturis.jeureseau.Util.AnsiColors;
import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.model.impl.Direction;
import fr.xonturis.jeureseau.model.impl.Move;
import fr.xonturis.jeureseau.model.impl.Pawn;
import fr.xonturis.jeureseau.network.NetworkAPI;
import fr.xonturis.jeureseau.network.client.GameSocketClient;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.packets.Transaction;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

import java.util.regex.Pattern;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class RunningGamePhasePacketHandler extends PacketHandler {

    private static final Pattern PATTERN = Pattern.compile("[\\d]{2} .+");

    @PacketType(transactionName = "askForNextMove")
    private void receiveTransaction(PacketWrapper packetWrapper) {
        Board board = (Board) packetWrapper.getPacket().getObject("board");
        Move move = null;
        boolean isNeutron = (boolean) packetWrapper.getPacket().getObject("neutron");

        do { // While the move is invalid

            String input;
            do { // While the input is invalid
                GameLogger.log(Direction.getDirectionsDisplay());
                if (board.getRev() == 0) {
                    GameLogger.dim("Format : XY Direction");
                    GameLogger.dim("Exemple : 00 1 (signifie le pion en 00 (en haut à gauche) va en bas");
                }
                GameLogger.info("Quel déplacement ?");

                if (isNeutron) {
                    GameLogger.info("Vous devez bouger le " + AnsiColors.ANSI_BLUE + "Neutron");
                }

                input = CliController.inputString();
            } while (!PATTERN.matcher(input).matches());

            // Converting input into x y integers
            char[] positionChar = input.substring(0, 2).toCharArray();
            int[] position = new int[2];
            position[0] = positionChar[0] - '0';
            position[1] = positionChar[1] - '0';

            // Converting input into direction
            String directionStr = input.substring(3);
            Direction direction = Direction.getDirectionFromString(directionStr);
            if (direction == null) {
                GameLogger.err("Direction invalide !");
                continue;
            }

            // Testing if the pawn is owned by the player
            Pawn pawn = board.getPawn(position[0], position[1]);
            if (pawn == null) {
                GameLogger.err("Il n'y a pas de pions ici !");
                continue;
            }

            if (isNeutron) {
                if (!pawn.isNeutron()) {
                    GameLogger.err("Tu dois bouger le " + AnsiColors.ANSI_BLUE + "Neutron" + AnsiColors.ANSI_RED + " !");
                    continue;
                }
            } else {
                if (!pawn.isOwner(GameSocketClient.getINSTANCE().getClientPlayerSocket().getPlayer())) {
                    GameLogger.err("Ce pion ne t'appartient pas !");
                    continue;
                }
            }

            // Testing if move is possible
            if (!board.canMovePawn(pawn, direction)) {
                GameLogger.err("Vous ne pouvez pas faire ce déplacement !");
                continue;
            }

            move = new Move(pawn.getUuid(), direction);
        } while (move == null);

        Transaction<Move> transaction = (Transaction<Move>) packetWrapper.getPacket();
        NetworkAPI.sendPacketToServer(transaction.answer(move));
    }

}
