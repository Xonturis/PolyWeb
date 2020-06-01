package fr.xonturis.jeureseau.network;

/**
 * Created by Xonturis on 5/31/2020.
 */
public abstract class PacketHandler {
    public PacketHandler() {
        NetworkHandler.registerHandler(this);
    }
}
