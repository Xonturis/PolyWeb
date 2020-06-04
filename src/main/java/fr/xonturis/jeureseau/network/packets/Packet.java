package fr.xonturis.jeureseau.network.packets;

import com.google.gson.Gson;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xonturis on 5/31/2020.
 */
public class Packet implements Serializable {

    private static final Gson gson = new Gson();

    @Getter
    private String name;
    private Map<String, String> data;

    public Packet(String name) {
        this.name = name;
        this.data = new HashMap<>();
    }

    public Packet setObject(@NotNull String key, Serializable value) {
        ObjectWrapper wrapper = new ObjectWrapper(value.getClass().getName(), gson.toJson(value));
        data.put(key, gson.toJson(wrapper));
        return this;
    }

    @Nullable
    public Object getObject(@NotNull String key) {
        ObjectWrapper wrapper = gson.fromJson(data.get(key), ObjectWrapper.class);
        try {
            return gson.fromJson(wrapper.getJson(), getClass().getClassLoader().loadClass(wrapper.getClassName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
