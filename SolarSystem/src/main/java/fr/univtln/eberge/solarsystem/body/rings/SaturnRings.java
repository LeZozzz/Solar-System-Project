package fr.univtln.eberge.solarsystem.body.rings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

public class SaturnRings {

    public static Node createRings(AssetManager assetManager, float innerRadius, float outerRadius) {
        Node ringsNode = new Node("SaturnRings");

        // Vérification des dimensions minimales
        if (innerRadius < 1 || outerRadius <= innerRadius) {
            throw new IllegalArgumentException("Les rayons doivent être positifs et outerRadius > innerRadius.");
        }

        // Création des anneaux comme un disque plat
        int axisSamples = 2; // Nombre d'échantillons en hauteur (min. 2 pour éviter l'erreur)
        int radialSamples = 64; // Plus la valeur est grande, plus l'anneau est lisse
        float height = 0.01f; // Épaisseur très fine pour simuler un disque

        Cylinder rings = new Cylinder(axisSamples, radialSamples, innerRadius, outerRadius, height, true, false);
        Geometry ringsGeo = new Geometry("Saturn Rings", rings);

        // Matériau avec texture semi-transparente
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Planets/saturn_belt_tex.jpeg"));
        mat.getAdditionalRenderState().setBlendMode(com.jme3.material.RenderState.BlendMode.Alpha);
        ringsGeo.setMaterial(mat);

        // Rotation pour aligner horizontalement
        ringsGeo.rotate(FastMath.HALF_PI, 0, 0);

        ringsNode.attachChild(ringsGeo);
        return ringsNode;
    }
}
