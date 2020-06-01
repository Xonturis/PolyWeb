package fr.xonturis.jeureseau.model.impl;

import fr.xonturis.jeureseau.model.impl.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
@AllArgsConstructor
public final class Move implements Serializable {

    @Getter
    private final UUID pawnUUID;
    @Getter
    private final Direction direction;

}
