package fr.univtln.eberge.solarsystem.body.sphere;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Sun {
    private Node sunNode;
    private PointLight sunLight;
    private Geometry geometry;

    public Sun(AssetManager assetManager) {
        sunNode = new Node("Sun_Node");

        Sphere sphere = new Sphere(32, 32, 109.0f);
        geometry = new Geometry("Sun", sphere);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", assetManager.loadTexture("Textures/Planets/sun_tex.jpg"));
        geometry.setMaterial(material);

        sunNode.attachChild(geometry);

        sunLight = new PointLight();
        sunLight.setColor(ColorRGBA.White);
        sunLight.setRadius(0);
        sunLight.setPosition(Vector3f.ZERO);
        sunNode.addLight(sunLight);
    }

    public Node getSunNode() {
        return sunNode;
    }

    public PointLight getSunLight() {
        return sunLight;
    }
    public void setLocalRotation(Quaternion quaternion) {
        this.geometry.setLocalRotation(quaternion);
    }
}