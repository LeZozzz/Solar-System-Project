package fr.univtln.eberge.solarsystem.visuals;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Curve;
import com.jme3.math.Vector3f;

public class Orbit {
    public static Geometry createOrbit(float radius, AssetManager assetManager, ColorRGBA color) {
        int points = 1024;
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

        // Matériau pour l'orbite avec couleur personnalisée
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        orbitGeometry.setMaterial(mat);

        return orbitGeometry;
    }

}
