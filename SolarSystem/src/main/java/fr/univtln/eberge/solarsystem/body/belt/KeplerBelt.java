package fr.univtln.eberge.solarsystem.body.belt;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Random;

/**
 * Classe permettant de générer une ceinture d'astéroïdes.
 * Les astéroïdes sont générés aléatoirement sur un cercle.
 * Chaque astéroïde est un modèle 3D avec une texture associée.
 * Les modèles et textures sont choisis aléatoirement parmi une liste prédéfinie.
 * Les astéroïdes sont ensuite ajoutés à un Node qui représente la ceinture d'astéroïdes.
 * @see #createBelt(AssetManager, int, float, float)
 * @see #createAsteroid(AssetManager)
 * @author eberge
 */

public class KeplerBelt {

    public static Node createBelt(AssetManager assetManager, int numAsteroids, float minRadius, float maxRadius) {
        Node asteroidBelt = new Node("AsteroidBelt");
        Random random = new Random();

        for (int i = 0; i < numAsteroids; i++) {
            float angle = random.nextFloat() * FastMath.TWO_PI; // Angle aléatoire
            float radius = minRadius + random.nextFloat() * (maxRadius - minRadius); // Rayon aléatoire
            float x = FastMath.cos(angle) * radius;
            float z = FastMath.sin(angle) * radius;
            float y = (random.nextFloat() - 0.5f) * 100f; // Variation en hauteur

            Geometry asteroid = createAsteroid(assetManager);
            asteroid.setLocalTranslation(new Vector3f(x, y, z));
            asteroidBelt.attachChild(asteroid);
        }

        return asteroidBelt;
    }

    private static final Random RANDOM = new Random();

public static Geometry createAsteroid(AssetManager assetManager) {
    String[][] asteroidModelsAndTextures = {
            {"Models/Asteroid/Asteroid1.j3o", "Textures/Asteroid/Asteroid1_tex.png"},
            {"Models/Asteroid/Asteroid2.j3o", "Textures/Asteroid/Asteroid2_tex.png"},
            {"Models/Asteroid/Asteroid3.j3o", "Textures/Asteroid/Asteroid3_tex.png"},
    };
    String[] selectedModelAndTexture = asteroidModelsAndTextures[RANDOM.nextInt(asteroidModelsAndTextures.length)];
    Spatial asteroidGeo = assetManager.loadModel(selectedModelAndTexture[0]);
    asteroidGeo.setLocalScale(1.5f);
    Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    mat.setTexture("DiffuseMap", assetManager.loadTexture(selectedModelAndTexture[1]));
    asteroidGeo.setMaterial(mat);
    return (Geometry) asteroidGeo;


}


}
