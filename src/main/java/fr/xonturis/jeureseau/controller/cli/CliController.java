package fr.xonturis.jeureseau.controller.cli;

import java.util.Scanner;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class CliController {

    public CliController() {
        registerListeners();
    }

    private void registerListeners() {
        new RunningGamePhasePacketHandler();
        new PrintBoardPacketHandler();
        new WinLoseHandler();
    }

    protected static String inputString() {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNext()) {
            return sc.nextLine();
        }
        sc.close();
        return "";
    }
}
