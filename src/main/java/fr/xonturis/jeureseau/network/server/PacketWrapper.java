package fr.xonturis.jeureseau.network.server;

import fr.xonturis.jeureseau.network.Packet;
import fr.xonturis.jeureseau.network.PlayerSocket;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Xonturis on 5/31/2020.
 */
@AllArgsConstructor
public class PacketWrapper {
    @Getter
    private PlayerSocket playerSocket;
    @Getter
    private Packet packet;
}
