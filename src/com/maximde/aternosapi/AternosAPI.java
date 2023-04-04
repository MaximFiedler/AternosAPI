package com.maximde.aternosapi;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AternosAPI {
    public static List<String> getNewestServersFromList(int count) throws Exception {
        List<String> servers = new ArrayList<>();
        long tenthThread = getServerThreadNumberFromCurrentList(count);
        if(tenthThread == 0) {
            throw new Exception("Failed to read aternos server list!");
        }
        for(long i = tenthThread; i <= (tenthThread + count); i++) {
            String ip = getAternosIP(i);
            if(ip == null) {
                continue;
            }
            servers.add(ip);
        }
        if(servers.isEmpty()) {
            System.out.println("WARNING! No server found in server list!");
        }
        return servers;
    }

    public static List<Long> getNewestServerIDsFromList(int count) throws Exception {
        List<Long> servers = new ArrayList<>();
        long tenthThread = getServerThreadNumberFromCurrentList(count);
        if(tenthThread == 0) {
            throw new Exception("Failed to read aternos server list!");
        }
        for(long i = tenthThread; i <= (tenthThread + count); i++) {
            if(i == 0) {
                continue;
            }
            servers.add(i);
        }
        if(servers.isEmpty()) {
            System.out.println("WARNING! No server found in server list!");
        }
        return servers;
    }


    /**
     * Get the thread number of a server in the Aternos server list
     * @param position
     * @return int server thread number (example 136466)
     */
    public static long getServerThreadNumberFromCurrentList(int position) {
        String url = "https://board.aternos.org/board/90-serverliste/";
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            /**
             * GET SITE RESPONSE
             */
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();

            String startTag = "<div class=\"tabularListRow\"";
            int startIndex = response.indexOf(startTag);
            for (int i = 1; i < 10; i++) {
                startIndex = response.indexOf(startTag, startIndex + 1);
            }
            startIndex = response.indexOf("data-thread-id=\"", startIndex);
            startIndex += 16;
            int endIndex = response.indexOf("\"", startIndex);
            int id;
            try {
                id = Integer.parseInt(response.substring(startIndex, endIndex)) - (position);
            } catch (Exception e) {
                return 0;
            }
            return id;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * Get Aternos server ip by thread number of the server from the server list
     * @param threadNumber -> EXAMPLE: 136473 (from link: https://board.aternos.org/thread/136473)
     * @return Aternos server ip -> EXAMPLE: abnormalmcSMP.aternos.me
     */
    public static String getAternosIP(long threadNumber) {
        String url = "https://board.aternos.org/thread/" + threadNumber;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            /**
             * GET SITE RESPONSE
             */
            InputStream stream;
            try {
                stream = connection.getInputStream();
            } catch (Exception e) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();

            /**
             * FIND IP ELEMENT
             */
            int startIndex;
            if (response.contains("<dt>Deine Server IP</dt>")) {
                startIndex = response.indexOf("<dt>Deine Server IP</dt>");
            } else if (response.contains("<dt>Your server IP</dt>")) {
                startIndex = response.indexOf("<dt>Your server IP</dt>");
            } else {
                return null;
            }
            startIndex = response.indexOf("<dd>", startIndex) + 4;
            int endIndex = response.indexOf("</dd>", startIndex);
            return response.substring(startIndex, endIndex);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static AternosServer getAternosServer(long threadNumber) {
        AternosServer server = new AternosServer();
        server.setServerID(threadNumber);
        String url = "https://board.aternos.org/thread/" + threadNumber;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            /**
             * GET SITE RESPONSE
             */
            InputStream stream;
            try {
                stream = connection.getInputStream();
            } catch (Exception e) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();

            /**
             * FIND IP ELEMENT
             */
            int startIndex;
            if (response.contains("<dt>Deine Server IP</dt>")) {
                startIndex = response.indexOf("<dt>Deine Server IP</dt>");
            } else if (response.contains("<dt>Your server IP</dt>")) {
                startIndex = response.indexOf("<dt>Your server IP</dt>");
            } else {
                return null;
            }
            startIndex = response.indexOf("<dd>", startIndex) + 4;
            int endIndex = response.indexOf("</dd>", startIndex);
            /*
                Set server ip
             */
            server.setIp(response.substring(startIndex, endIndex));


            /**
             * FIND OWNER NAME
             */
            int startIndex1;
            int endIndex2;
            String ownerStartTag = "<a href=\"https://board.aternos.org/wcf/user/";
            if (response.contains(ownerStartTag)) {
                startIndex1 = response.indexOf(ownerStartTag);
                startIndex1 = response.indexOf("<span>", startIndex1) + 6;
                endIndex2 = response.indexOf("</span>", startIndex1);
                server.setOwner(response.substring(startIndex1, endIndex2));
            }

            /**
             * FIND DISCORD INVITE ELEMENT
             */
            // Find the start index of the Discord invite element
            int startIndex2;
            if (response.contains("<dt>Discord Einladung</dt>")) {
                startIndex2 = response.indexOf("<dt>Discord Einladung</dt>");
            } else if (response.contains("<dt>Discord invite</dt>")) {
                startIndex2 = response.indexOf("<dt>Discord invite</dt>");
            } else {
                startIndex2 = -1;
            }

            /**
             * FIND EDITION ELEMENT
             */
            int startIndex4;
            if (response.contains("<dt>Minecraft Edition</dt>")) {
                startIndex4 = response.indexOf("<dt>Minecraft Edition</dt>");
            } else {
                return null;
            }
            startIndex4 = response.indexOf("<dd>", startIndex4) + 4;
            int endIndex4 = response.indexOf("</dd>", startIndex4);
            /*
                Set edition ip
             */
            String sEdition = response.substring(startIndex4, endIndex4);
            AternosServer.minecraftEdition edition;

            if(sEdition.contains("<br>")) {
                String[] editionArr = sEdition.split("<br>");
                String javaEdition = editionArr[0].trim();
                String bedrockEdition = editionArr[1].trim();
                // Set both Java and Bedrock editions
                edition = AternosServer.minecraftEdition.BedrockAndJava;
            } else if(sEdition.contains("Java Edition")) {
                edition = AternosServer.minecraftEdition.Java;
                server.setEdition(edition);
            } else if(sEdition.contains("Bedrock Edition")) {
                edition = AternosServer.minecraftEdition.Bedrock;
                server.setEdition(edition);
            } else {
                edition = AternosServer.minecraftEdition.Unknown;
                server.setEdition(edition);
            }

            server.setEdition(edition);


            /**
             * FIND VERSION ELEMENT
             */
            int startIndex5;
            if (response.contains("<dt>Unterstütze Version</dt>")) {
                startIndex5 = response.indexOf("<dt>Unterstütze Version</dt>");
            } else if (response.contains("<dt>Supported Version</dt>")) {
                startIndex5 = response.indexOf("<dt>Supported Version</dt>");
            } else {
                return null;
            }
            startIndex5 = response.indexOf("<dd>", startIndex5) + 4;
            int endIndex5 = response.indexOf("</dd>", startIndex5);
            /*
                Set version
             */
            server.setVersion(response.substring(startIndex5, endIndex5));

            // Extract the URL from the HTML element
            if (startIndex2 != -1) {
                startIndex2 = response.indexOf("<a href=\"", startIndex2) + 9;
                int endIndex3 = response.indexOf("\"", startIndex2);
                String discordInvite = response.substring(startIndex2, endIndex3);
                server.setDiscordInvite(discordInvite);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return server;
    }
}
