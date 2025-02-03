package fr.univtln.eberge.samples.body;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;

public class Sun {
    private Geometry geometry;

    public Sun(AssetManager assetManager) {
        // Création de la sphère pour le Soleil
        Sphere sunSphere = new Sphere(64, 64, 109.0f);
        this.geometry = new Geometry("Soleil", sunSphere);

        // Matériau avec texture
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("Textures/Planets/sun_tex.jpg");
        mat.setTexture("ColorMap", texture);
        this.geometry.setMaterial(mat);
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
