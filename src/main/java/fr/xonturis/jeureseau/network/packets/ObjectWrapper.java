package fr.xonturis.jeureseau.network.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Xonturis on 6/4/2020.
 */
@AllArgsConstructor
public class ObjectWrapper {

    @Getter
    private String className;
    @Getter
    private String json;

}
