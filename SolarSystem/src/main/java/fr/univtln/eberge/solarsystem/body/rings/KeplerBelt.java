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

    private static final String[] ASTEROID_TEXTURES = {
            "Textures/Planets/asteroids/asteroid_tex.jpg",
            "Textures/Planets/asteroids/asteroid_tex2.jpg",
            "Textures/Planets/asteroids/asteroid_tex3.jpg",
            "Textures/Planets/asteroids/asteroid_tex4.jpg"
    };

    private static final Random RANDOM = new Random();

    public static Geometry createAsteroid(AssetManager assetManager) {
        // Génération d'une taille aléatoire pour l'astéroïde
        float radius = 5f + RANDOM.nextFloat() * 10f;
        Sphere asteroidMesh = new Sphere(3, 3, radius);
        Geometry asteroidGeo = new Geometry("Asteroid", asteroidMesh);

        // Sélection aléatoire d'une texture
        String selectedTexture = ASTEROID_TEXTURES[RANDOM.nextInt(ASTEROID_TEXTURES.length)];

        // Matériau avec texture aléatoire
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture(selectedTexture));

        asteroidGeo.setMaterial(mat);
        return asteroidGeo;
    }

//    public static Geometry createAsteroid(AssetManager assetManager) {
//        // Enregistrer le chargeur STL
//        assetManager.registerLoader(StlLoader.class, "stl");
//
//        // Charger le modèle 3D existant au format STL
//        Spatial asteroidModel = assetManager.loadModel("Models/Asteroid/Asteroid.stl");
//
//        // Génération d'une taille aléatoire pour l'astéroïde
//        float scale = 0.5f + RANDOM.nextFloat() * 1.5f;
//        asteroidModel.scale(scale);
//
//        // Sélection aléatoire d'une texture
//        String selectedTexture = ASTEROID_TEXTURES[RANDOM.nextInt(ASTEROID_TEXTURES.length)];
//
//        // Matériau avec texture aléatoire
//        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//        mat.setTexture("DiffuseMap", assetManager.loadTexture(selectedTexture));
//
//        asteroidModel.setMaterial(mat);
//
//        // Créer une géométrie à partir du modèle chargé
//        Geometry asteroidGeo = new Geometry("Asteroid", new Sphere(3, 3, 1));  // Créer une sphère temporaire
//        asteroidGeo.setMesh(asteroidModel.getControl(Geometry.class).getMesh());  // Remplacer la sphère par le modèle chargé
//
//        return asteroidGeo;

}
