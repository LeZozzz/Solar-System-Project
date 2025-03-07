package fr.univtln.eberge.solarsystem.body.sphere;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * Classe abstraite représentant un corps céleste.
 * Un corps céleste est un objet qui orbite autour d'une étoile.
 * Il possède un nom, une taille, une distance à l'étoile, une période de rotation et une période de révolution.
 * Il possède également un noeud pour son orbite et un noeud pour son corps.
 * Il possède une géométrie pour son corps.
 * Il est possible de récupérer les noeuds de l'orbite et du corps, ainsi que les périodes de rotation et de révolution.
 * @author eberge
 */

public abstract class CelestialBody {
    protected String name;
    protected float size;
    protected float distanceFromSun;
    protected float rotationPeriod;
    protected float revolutionPeriod;
    protected Node orbitNode;
    protected Node bodyNode;
    protected Geometry geometry;

    public CelestialBody(String name, float size, float distanceFromSun, float rotationPeriod, float revolutionPeriod, String texturePath, AssetManager assetManager) {
        this.name = name;
        this.size = size;
        this.distanceFromSun = distanceFromSun;
        this.rotationPeriod = rotationPeriod;
        this.revolutionPeriod = revolutionPeriod;

        orbitNode = new Node(name + "_orbitNode");
        bodyNode = new Node(name + "_bodyNode");
        orbitNode.attachChild(bodyNode);

        Sphere sphere = new Sphere(32, 32, size);
        geometry = new Geometry(name, sphere);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        geometry.setMaterial(material);

        bodyNode.attachChild(geometry);
        bodyNode.setLocalTranslation(new Vector3f(distanceFromSun, 0, 0));
    }

    public Node getOrbitNode() {
        return orbitNode;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public float getRotationPeriod() {
        return rotationPeriod;
    }

    public float getRevolutionPeriod() {
        return revolutionPeriod;
    }
}
