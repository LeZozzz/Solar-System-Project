package fr.univtln.eberge.samples.body;

import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;

public class Planet {
    private Geometry geometry;
    private Node orbitNode;  // Node pour gérer la révolution
    private float rotationSpeed;
    private float revolutionSpeed;

    public Planet(String name, float size, float distanceFromSun, float rotationSpeed, float revolutionSpeed, String texturePath, AssetManager assetManager) {
        this.rotationSpeed = rotationSpeed;
        this.revolutionSpeed = revolutionSpeed;

        // Création de la sphère représentant la planète
        Sphere sphere = new Sphere(64, 64, size);
        this.geometry = new Geometry(name, sphere);

        // Matériau avec texture
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(texturePath);
        mat.setTexture("ColorMap", texture);
        this.geometry.setMaterial(mat);

        // Positionner la planète sur son orbite
        this.geometry.setLocalTranslation(distanceFromSun, 0, 0);

        // Création du node d’orbite
        this.orbitNode = new Node(name + "_Orbit");
        this.orbitNode.attachChild(this.geometry);
    }

    public Node getOrbitNode() {
        return orbitNode;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public float getRevolutionSpeed() {
        return revolutionSpeed;
    }

    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }
}
