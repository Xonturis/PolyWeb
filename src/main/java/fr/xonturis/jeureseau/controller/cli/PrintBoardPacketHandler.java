package fr.xonturis.jeureseau.controller.cli;

import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class PrintBoardPacketHandler extends PacketHandler {

    @PacketType(packetName = "printClientBoard")
    private void printBoard(PacketWrapper packetWrapper) {
        clearScreen();
        Board board = (Board) packetWrapper.getPacket().getObject("board");
        board.printClientBoard();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
