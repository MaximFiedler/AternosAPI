package com.maximde.aternosapi;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("--------- GETTING THE 10 NEWEST SERVERS FROM https://board.aternos.org/board/90-serverliste/ ---------");
        for(String serverIP : AternosAPI.getNewestServersFromList(10)) {
            System.out.println(serverIP);
        }
        System.out.println("----------------------------------------------- DONE! ------------------------------------------------");
    }


}