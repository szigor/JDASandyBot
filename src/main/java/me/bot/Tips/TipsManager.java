package me.bot.Tips;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Scanner;

public class TipsManager {

    private String champion;

    public TipsManager(String champion) {
        this.champion = champion;
    }

    public List<String> getAllyTips() {

        JSONObject champObject = getJsonObject(champion);

        String allyTips = champObject.get("allytips").toString().replaceAll("[\\[\\]\"]", "");
        //List<String> allyList = new java.util.ArrayList<>(List.of(allyTips.split("\\.")));
        List<String> allyList = new ArrayList<>();
        String[] splitted = allyTips.split("\\.");
        for (String s : splitted) {
            allyList.add(s);
        }
        clearList(allyList);

        return allyList;
    }

    public List<String> getEnemyTips() {

        JSONObject champObject = getJsonObject(champion);

        String enemyTips = champObject.get("enemytips").toString().replaceAll("[\\[\\]\"]", "");
        //List<String> enemyList = new java.util.ArrayList<>(List.of(enemyTips.split("\\.")));
        List<String> enemyList = new ArrayList<>();
        String[] splitted = enemyTips.split("\\.");
        for (String s : splitted) {
            enemyList.add(s);
        }
        clearList(enemyList);

        return enemyList;
    }

    private JSONObject getJsonObject(String champion) {
        try {
            URL url = new URL("http://ddragon.leagueoflegends.com/cdn/12.17.1/data/en_US/champion/" + champion + ".json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);

            } else {
                StringBuilder jsonContent = new StringBuilder();
                Scanner scan = new Scanner(url.openStream());

                while (scan.hasNext()) {
                    jsonContent.append(scan.nextLine());
                }
                scan.close();

                JSONParser jsonParser = new JSONParser();

                Object firstObject = jsonParser.parse(jsonContent.toString());
                JSONObject jsonFirstObject = (JSONObject) firstObject;
                JSONObject dataObject = (JSONObject) jsonFirstObject.get("data");
                return (JSONObject) dataObject.get(champion);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearList(List<String> list) {
        list.forEach(s -> {
            if (s.startsWith(",")) list.set(list.indexOf(s), s.replaceFirst(",", ""));
        });
    }

}
