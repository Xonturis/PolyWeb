package fr.xonturis.jeureseau.controller.cli;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.network.packets.PacketHandler;
import fr.xonturis.jeureseau.network.packets.PacketType;
import fr.xonturis.jeureseau.network.server.PacketWrapper;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class RunningGamePhasePacketHandler extends PacketHandler {

    @PacketType(transactionName = "askForNextMove")
    private void receiveTransaction(PacketWrapper packetWrapper) {
        GameLogger.log("Quel déplacement ?");
        String strMove = CliController.inputString();
    }

}
