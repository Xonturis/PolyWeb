package fr.xonturis.jeureseau.controller.cli;

import fr.xonturis.jeureseau.model.impl.Board;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class PrintBoardPacketHandler extends PacketHandler {

    @PacketType(packetName = "printBoard")
    private void printBoard(PacketWrapper packetWrapper) {
        Board board = (Board) packetWrapper.getPacket().getObject("board");
        board.printBoard();
    }

}
