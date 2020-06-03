package fr.xonturis.jeureseau.network.packets;

/**
 * Created by Xonturis on 5/31/2020.
 */
public abstract class PacketHandler {
    public PacketHandler() {
        NetworkHandler.registerHandler(this);
    }

    protected void unregister() {
        NetworkHandler.unregisterHandler(this);
    }
}
