package fr.univtln.eberge.samples.body;

import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Curve;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;

public class Planet extends Astre {
    private Geometry geometry;
    private Geometry orbit;
    private Node orbitNode;  // Node pour gérer la révolution
    private float rotationSpeed;
    private float revolutionSpeed;

    public Planet(String name, float size, float distanceFromSun, float rotationPeriodInDays, float revolutionPeriodInYears, String texturePath, AssetManager assetManager) {

        this.rotationSpeed = FastMath.TWO_PI / (rotationPeriodInDays * 86400); // Rotation en radian/seconde
        this.revolutionSpeed = FastMath.TWO_PI / (revolutionPeriodInYears * (365.25f * 86400)); // Révolution en radian/seconde

        // Création de la sphère représentant la planète
        Sphere sphere = new Sphere(64, 64, size);
        this.geometry = new Geometry(name, sphere);


        // Matériau avec texture
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // Changer de shader
        mat.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));

        geometry.setMaterial(mat);

        // **Activer les ombres sur la planète**
        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        // Matériau avec texture
        Texture texture = assetManager.loadTexture(texturePath);
        this.geometry.setMaterial(mat);

        // Positionner la planète sur son orbite
        this.geometry.setLocalTranslation(distanceFromSun, 0, 0);

        // Création du node d’orbite
        this.orbitNode = new Node(name + "_Orbit");
        this.orbitNode.attachChild(this.geometry);

        // Création de l'orbite
        orbit = createOrbit(distanceFromSun, assetManager);
        orbitNode.attachChild(orbit); // Attacher l'orbite au node de révolution
    }

    public void generateLine(AssetManager assetManager) {
        // Création de la ligne pour l'orbite
        Line line = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
        Geometry lineGeometry = new Geometry();
        Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        lineMaterial.setColor("ColorMap", ColorRGBA.White);
        lineGeometry.setMaterial(lineMaterial);
        this.orbitNode.attachChild(lineGeometry);
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

    private Geometry createOrbit(float radius, AssetManager assetManager) {
        int points = 512;
        Vector3f[] vertices = new Vector3f[points + 1];

        for (int i = 0; i <= points; i++) {
            float angle = i * FastMath.TWO_PI / points;
            float x = FastMath.cos(angle) * radius;
            float z = FastMath.sin(angle) * radius;
            vertices[i] = new Vector3f(x, 0, z);
        }

        // Création de la courbe de l'orbite
        Curve curve = new Curve(vertices, 1);
        Geometry orbitGeometry = new Geometry("Orbit_" + radius, curve);

        // Matériau pour l'orbite
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        orbitGeometry.setMaterial(mat);

        return orbitGeometry;
    }
}
