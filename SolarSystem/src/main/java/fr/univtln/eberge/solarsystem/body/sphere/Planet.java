package fr.univtln.eberge.solarsystem.body.sphere;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Planet {
    private String name;
    private float size;
    private float distanceFromSun;
    private float rotationPeriod;
    private float revolutionPeriod;
    private Node orbitNode;
    private Node planetNode;
    private Geometry geometry;

    public Planet(String name, float size, float distanceFromSun, float rotationPeriod, float revolutionPeriod, String texturePath, AssetManager assetManager) {
        this.name = name;
        this.size = size;
        this.distanceFromSun = distanceFromSun;
        this.rotationPeriod = rotationPeriod;
        this.revolutionPeriod = revolutionPeriod;

        orbitNode = new Node(name + "_orbitNode");
        planetNode = new Node(name + "_planetNode");
        orbitNode.attachChild(planetNode);

        Sphere sphere = new Sphere(32, 32, size);
        geometry = new Geometry(name, sphere);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        geometry.setMaterial(material);

        planetNode.attachChild(geometry);
        planetNode.setLocalTranslation(new Vector3f(distanceFromSun + 109f, 0, 0));
    }

    public Node getOrbitNode() {
        return orbitNode;
    }

    public Node getPlanetNode() {
        return planetNode;
    }

    public float getRotationPeriod() {
        return rotationPeriod;
    }

    public float getRevolutionPeriod() {
        return revolutionPeriod;
    }

    public Node getBodyNode() {
        return planetNode;
    }
    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }

    public String getName() {
        return name;
    }
    public float getSize() {
        return size;
    }

    public float getRotationSpeed() {
        return 360 / rotationPeriod;
    }
    public float getRevolutionSpeed() {
        return 360 / revolutionPeriod;
    }

    public Vector3f getLocalTranslation() {
        return planetNode.getLocalTranslation();
    }
}