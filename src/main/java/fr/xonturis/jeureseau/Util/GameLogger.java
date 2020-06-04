package fr.xonturis.jeureseau.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Xonturis on 6/1/2020.
 */
public class GameLogger {

    public static boolean doesDebug = false;


    public static void debug(@NotNull Object logRecord) {
        if (doesDebug)
            synchronized (System.out) {
                System.out.println(logRecord);
            }
    }

    public static void strongDebug(@NotNull Object logRecord) {
        if (doesDebug)
            synchronized (System.out) {
                System.out.println(AnsiColors.ANSI_YELLOW + logRecord + AnsiColors.ANSI_RESET);
            }
    }

    public static void log(@NotNull Object logRecord) {
        synchronized (System.out) {
            System.out.println(logRecord);
        }
    }

    public static void info(@NotNull Object logRecord) {
        synchronized (System.out) {
            System.out.println(AnsiColors.ANSI_YELLOW + logRecord + AnsiColors.ANSI_RESET);
        }
    }

    public static void err(@NotNull String logRecord) {
        synchronized (System.out) {
            System.out.println(AnsiColors.ANSI_RED + logRecord + AnsiColors.ANSI_RESET);
        }
    }

    public static void logStackTrace() {
        if (!doesDebug) return;
        Exception e = new Exception("Stack trace log");
        e.printStackTrace();
    }

    public static void dim(@NotNull String logRecord) {
        synchronized (System.out) {
            System.out.println(AnsiColors.ANSI_GRAY + logRecord + AnsiColors.ANSI_RESET);
        }
    }
}
