package fr.xonturis.jeureseau.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 5/29/2020.
 */
@AllArgsConstructor
public final class Move implements Serializable {

    public Move() {
    }

    @Getter
    private UUID pawnUUID;
    @Getter
    private Direction direction;

}
