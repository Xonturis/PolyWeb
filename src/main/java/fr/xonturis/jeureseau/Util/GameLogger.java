package fr.xonturis.jeureseau.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Xonturis on 6/1/2020.
 */
public class GameLogger {

    public static void log(@NotNull String logRecord) {
        synchronized (System.out) {
            System.out.println(logRecord);
        }
    }

    public static void logStackTrace() {
        Exception e = new Exception("Stack trace log");
        e.printStackTrace();
    }
}
