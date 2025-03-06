package fr.univtln.eberge.solarsystem.body.sphere;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Moon {
    private String name;
    private float size;
    private float distanceFromPlanet;
    private float revolutionPeriod;
    private float rotationPeriod;
    private Node orbitNode;
    private Node moonNode;
    private Geometry geometry;
    private float semiMajorAxis;
    private float eccentricity;

    public Moon(String name, float size, float distanceFromPlanet,float semiMajorAxis,float eccentricity, float revolutionPeriod, float rotationPeriod, String texturePath, AssetManager assetManager) {
        this.name = name;
        this.size = size;
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.distanceFromPlanet = distanceFromPlanet;
        this.revolutionPeriod = revolutionPeriod;
        this.rotationPeriod = rotationPeriod;

        orbitNode = new Node(name + "_orbitNode");
        moonNode = new Node(name + "_moonNode");
        orbitNode.attachChild(moonNode);

        Sphere sphere = new Sphere(32, 32, size);
        geometry = new Geometry(name, sphere);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        geometry.setMaterial(material);

        moonNode.attachChild(geometry);
    }

    public Vector3f calcTrajectory(double time){
        float focalDistance = semiMajorAxis * eccentricity;
        float angle = getAngle(time);
        float x = semiMajorAxis * FastMath.cos(angle) - focalDistance; // Centré sur le foyer
        float z = semiMajorAxis * FastMath.sqrt(1 - eccentricity * eccentricity) * FastMath.sin(angle);
        return new Vector3f(x, 0, z);
    }
    public float getAngle(double time){
        return (float)(((FastMath.TWO_PI / (revolutionPeriod*365*24*60*60)) * time) % FastMath.TWO_PI);
    }

    public Node getOrbitNode() {
        return orbitNode;
    }

    public float getRevolutionPeriod() {
        return revolutionPeriod;
    }
    public float getRotationPeriod() {
        return rotationPeriod;
    }

    public Node getMoonNode() {
        return moonNode;
    }
    public String getName() {
        return name;
    }
    public float getSize() {
        return size;
    }
    public float getDistanceFromPlanet() {
        return distanceFromPlanet;
    }
    public float getSemiMajorAxis() {
        return semiMajorAxis;
    }
    public float getEccentricity() {
        return eccentricity;
    }
    public void setLocalTranslation(Vector3f vector3f) {
        moonNode.setLocalTranslation(vector3f);
    }

    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }
}
