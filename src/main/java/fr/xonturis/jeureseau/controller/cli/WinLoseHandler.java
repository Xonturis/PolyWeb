package fr.xonturis.jeureseau.controller.cli;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

import java.io.IOException;

/**
 * Created by Xonturis on 6/4/2020.
 */
public class WinLoseHandler extends PacketHandler {

    @PacketType(packetName = "win")
    private void onWin(PacketWrapper packetWrapper) throws IOException {
        GameLogger.info("Vous avez gagné :) !");
        packetWrapper.getPlayerSocket().getSocket().close();
    }

    @PacketType(packetName = "lose")
    private void onLose(PacketWrapper packetWrapper) throws IOException {
        GameLogger.info("Vous n'avez pas gagné :( !");
        packetWrapper.getPlayerSocket().getSocket().close();
    }
}
