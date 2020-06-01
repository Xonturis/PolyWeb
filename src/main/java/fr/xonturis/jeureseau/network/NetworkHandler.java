package fr.xonturis.jeureseau.network;

import fr.xonturis.jeureseau.Util.GameLogger;
import fr.xonturis.jeureseau.network.server.PacketWrapper;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xonturis on 5/31/2020.
 */
public class NetworkHandler {

    private static final List<PacketHandler> handlers = new ArrayList<>();

    @SneakyThrows
    public synchronized static void handle(PlayerSocket playerSocket, Packet packet) {
        GameLogger.log("Received packet " + packet.getName());
        for (PacketHandler handler : handlers) {
            for (Method method : handler.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PacketType.class)) {
                    PacketType packetType = method.getAnnotation(PacketType.class);
                    if (packet.getName().equals(packetType.packetName())) {
                        if (method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(PacketWrapper.class)) {
                            boolean isAccessible = method.isAccessible();
                            method.setAccessible(true);
                            method.invoke(handler, new PacketWrapper(playerSocket, packet));
                            method.setAccessible(isAccessible);
                        }
                    }
                }
            }
        }
    }

    public synchronized static void registerHandler(PacketHandler packetHandler) {
        handlers.add(packetHandler);
    }
}