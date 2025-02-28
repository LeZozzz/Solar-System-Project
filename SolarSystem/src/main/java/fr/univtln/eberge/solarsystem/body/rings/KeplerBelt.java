package fr.univtln.eberge.solarsystem.body.rings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

import java.util.Random;

public class KeplerBelt {

    public static Node createBelt(AssetManager assetManager, int numAsteroids, float minRadius, float maxRadius) {
        Node asteroidBelt = new Node("AsteroidBelt");
        Random random = new Random();

        for (int i = 0; i < numAsteroids; i++) {
            float angle = random.nextFloat() * FastMath.TWO_PI; // Angle aléatoire
            float radius = minRadius + random.nextFloat() * (maxRadius - minRadius); // Rayon aléatoire
            float x = FastMath.cos(angle) * radius;
            float z = FastMath.sin(angle) * radius;
            float y = (random.nextFloat() - 0.5f) * 50f; // Variation en hauteur

            Geometry asteroid = createAsteroid(assetManager);
            asteroid.setLocalTranslation(new Vector3f(x, y, z));
            asteroidBelt.attachChild(asteroid);
        }

        return asteroidBelt;
    }

    private static final Random RANDOM = new Random();

//    public static Geometry createAsteroid(AssetManager assetManager) {
//        // Génération d'une taille aléatoire pour l'astéroïde
//        float radius = 5f + RANDOM.nextFloat() * 10f;
//        Sphere asteroidMesh = new Sphere(3, 3, radius);
//        Geometry asteroidGeo = new Geometry("Asteroid", asteroidMesh);
//
//        // Sélection aléatoire d'une texture
//        String selectedTexture = ASTEROID_TEXTURES[RANDOM.nextInt(ASTEROID_TEXTURES.length)];
//
//        // Matériau avec texture aléatoire
//        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//        mat.setTexture("DiffuseMap", assetManager.loadTexture(selectedTexture));
//
//        asteroidGeo.setMaterial(mat);
//        return asteroidGeo;
//    }

public static Geometry createAsteroid(AssetManager assetManager) {
    // Liste des modèles .j3o disponibles avec leurs textures associées
    String[][] asteroidModelsAndTextures = {
            {"Models/Asteroid/Asteroid1.j3o", "Textures/Asteroid/Asteroid1_tex.png"},
            {"Models/Asteroid/Asteroid2.j3o", "Textures/Asteroid/Asteroid2_tex.png"},
            {"Models/Asteroid/Asteroid3.j3o", "Textures/Asteroid/Asteroid3_tex.png"},
            // Ajoutez autant de paires modèle-texture que nécessaire
    };

    // Sélection aléatoire d'un modèle avec sa texture associée
    String[] selectedModelAndTexture = asteroidModelsAndTextures[RANDOM.nextInt(asteroidModelsAndTextures.length)];

    // Chargement du modèle sélectionné
    Spatial asteroidGeo = assetManager.loadModel(selectedModelAndTexture[0]);

    asteroidGeo.setLocalScale(5f);

    // Chargement de la texture associée
    Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    mat.setTexture("DiffuseMap", assetManager.loadTexture(selectedModelAndTexture[1]));

    // Application du matériau au modèle
    asteroidGeo.setMaterial(mat);

    return (Geometry) asteroidGeo;


}


}
