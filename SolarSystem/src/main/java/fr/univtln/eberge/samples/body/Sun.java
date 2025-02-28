package fr.univtln.eberge.samples.body;

import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;

public class Sun extends Astre {
    private Geometry geometry;
    private PointLight sunLight;

    public Sun(AssetManager assetManager, Node rootNode) {
        // Création de la sphère pour le Soleil
        Sphere sunSphere = new Sphere(64, 64, 109.0f);
        this.geometry = new Geometry("Soleil", sunSphere);

        // Création de la lumière du Soleil
        sunLight = new PointLight();
        sunLight.setColor(ColorRGBA.White);
        sunLight.setRadius(0);
        sunLight.setPosition(Vector3f.ZERO);
        rootNode.addLight(sunLight);

        // Matériau avec texture
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/Planets/sun_tex.jpg");
        mat.setTexture("ColorMap", texture);
        this.geometry.setMaterial(mat);
    }

    public Geometry getGeometry() {
        return geometry;
    }
    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }
    public void rotateSun(float tpf, float speedFactor) {
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(speedFactor * tpf, Vector3f.UNIT_Y);
        geometry.rotate(rotation);
    }
}
