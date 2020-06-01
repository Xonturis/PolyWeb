package fr.xonturis.jeureseau.model.impl;

/**
 * Created by Xonturis on 5/29/2020.
 */
@FunctionalInterface
public interface Callback<V> {

    void run(V v);

}
