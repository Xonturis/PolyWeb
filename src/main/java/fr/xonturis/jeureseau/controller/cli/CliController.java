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
    }

    protected static String inputString() {
        Scanner sc = new Scanner(System.in);
        String ret = sc.nextLine();
        sc.close();
        return ret;
    }
}
