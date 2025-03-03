package fr.univtln.eberge.solarsystem.api;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.HashMap;

public class PlanetInfoFetcher {

    private static final String JSON_FILE = "data/planets.json"; // Chemin vers le fichier JSON
    private static HashMap<String, JSONObject> planetData = new HashMap<>();

    static {
        loadPlanetData();
    }

    private static void loadPlanetData() {
        try (InputStream inputStream = PlanetInfoFetcher.class.getClassLoader().getResourceAsStream(JSON_FILE)) {
            if (inputStream == null) {
                System.err.println("Erreur : Impossible de charger le fichier " + JSON_FILE);
                return;
            }

            JSONObject json = new JSONObject(new JSONTokener(inputStream));

            for (String key : json.keySet()) {
                planetData.put(key.toLowerCase(), json.getJSONObject(key));
            }

            System.out.println("Données des planètes chargées avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier JSON.");
        }
    }

    public static String getPlanetInfo(String planetName) {
        planetName = planetName.toLowerCase();

        if (!planetData.containsKey(planetName)) {
            return "Erreur : Planète non trouvée.";
        }

        JSONObject json = planetData.get(planetName);

        return String.format(
                """
                Nom: %s
                Rayon: %.2f km
                Masse: %.3fx10^%d kg
                Gravité: %.2f m/s²
                Période orbitale: %.2f jours
                        
                        """,
                json.getString("name"),
                json.getDouble("meanRadius"),
                json.getJSONObject("mass").getDouble("massValue"),
                json.getJSONObject("mass").getInt("massExponent"),
                json.getDouble("gravity"),
                json.getDouble("sideralOrbit")
        );
    }

    public static String getPlanetName(String planetName) {
        planetName = planetName.toLowerCase();

        if (!planetData.containsKey(planetName)) {
            return "Erreur : Planète non trouvée.";
        }

        return planetData.get(planetName).getString("name");
    }
}
