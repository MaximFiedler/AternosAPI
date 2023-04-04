package com.maximde.example;

import com.maximde.aternosapi.AternosAPI;

public class Main {
    public static void main(String[] args) throws Exception {
        printServers();
    }

    private static void printServers() throws Exception {
        System.out.println("--------- GETTING THE 10 NEWEST SERVERS FROM https://board.aternos.org/board/90-serverliste/ ---------");
        for(String serverIP : AternosAPI.getNewestServersFromList(10)) {
            System.out.println(serverIP);
        }
        System.out.println("----------------------------------------------- DONE! ------------------------------------------------");
    }

    private static void printSpecificServer() throws Exception {
        //FROM LINK: https://board.aternos.org/thread/136468-survival-smp/
        System.out.println(AternosAPI.getAternosIP(136468));
        //OUTPUT: abnormalmcSMP.aternos.me
    }

}