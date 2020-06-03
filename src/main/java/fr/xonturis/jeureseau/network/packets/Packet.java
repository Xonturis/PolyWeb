package fr.xonturis.jeureseau.network.packets;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xonturis on 5/31/2020.
 */
public class Packet implements Serializable {

    @Getter
    private String name;
    private Map<String, Object> data;

    public Packet(String name) {
        this.name = name;
        this.data = new HashMap<>();
    }

    public Packet setObject(@NotNull String key, Serializable value) {
        this.data.put(key, value);
        return this;
    }

    @Nullable
    public Object getObject(@NotNull String key) {
        return this.data.get(key);
    }

}
