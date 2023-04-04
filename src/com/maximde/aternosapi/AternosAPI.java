package com.maximde.aternosapi;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AternosAPI {
    public static List<String> getNewestServersFromList(int count) throws Exception {
        List<String> servers = new ArrayList<>();
        int tenthThread = getServerThreadNumberFromCurrentList(count);
        if(tenthThread == 0) {
            throw new Exception("Failed to read aternos server list!");
        }
        for(int i = tenthThread; i <= (tenthThread + 11); i++) {
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


    /**
     * Get the thread number of a server in the Aternos server list
     * @param position
     * @return int server thread number (example 136466)
     */
    public static int getServerThreadNumberFromCurrentList(int position) {
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
                id = Integer.parseInt(response.substring(startIndex, endIndex)) - position;
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
    public static String getAternosIP(int threadNumber) {
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
}
