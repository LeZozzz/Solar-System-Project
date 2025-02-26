package fr.univtln.eberge.solarsystem.api;

import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PlanetInfoFetcher {

    private static final String API_URL = "https://api.le-systeme-solaire.net/rest/bodies/";

    public static String getPlanetInfo(String planetName) {
        try {
            // Création de l'URL
            URL url = new URL(API_URL + planetName.toLowerCase());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Vérification de la réponse
            if (conn.getResponseCode() != 200) {
                return "Erreur : Impossible de récupérer les données.";
            }

            // Lecture de la réponse JSON
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonText = new StringBuilder();
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
            scanner.close();

            // Parsing JSON
            JSONObject json = new JSONObject(jsonText.toString());

            // Extraction des informations de la planète
            return String.format(
                    "Rayon: %.2f km\nMasse: %.3fx10^%d kg\nGravité: %.2f m/s^2\nPériode orbitale: %.2f jours",
                    json.getDouble("meanRadius"),
                    json.getJSONObject("mass").getDouble("massValue"),
                    json.getJSONObject("mass").getInt("massExponent"),
                    json.getDouble("gravity"),
                    json.getDouble("sideralOrbit")
            );

        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur : Impossible de récupérer les informations.";
        }
    }
    public static String getPlanetName(String planetName){
        try {
            // Création de l'URL
            URL url = new URL(API_URL + planetName.toLowerCase());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Vérification de la réponse
            if (conn.getResponseCode() != 200) {
                return "Erreur : Impossible de récupérer les données.";
            }

            // Lecture de la réponse JSON
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonText = new StringBuilder();
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine());
            }
            scanner.close();

            // Parsing JSON
            JSONObject json = new JSONObject(jsonText.toString());

            // Extraction des informations de la planète
            return String.format(
                    json.getString("name")
            );

        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur : Impossible de récupérer les informations.";
        }
    }
}
