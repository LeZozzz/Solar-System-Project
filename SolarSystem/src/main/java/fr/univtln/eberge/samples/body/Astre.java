package fr.univtln.eberge.samples.body;

import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.Collections;

public class Astre {
    private Geometry geometry;
    private float rotationSpeed = 1.0f;

    public static void rotateAstre(Astre astre, float tpf) {
        astre.getGeometry().rotate(0, 0, astre.getRotationSpeed() * tpf * FastMath.DEG_TO_RAD);
    }

    public static void revolveAstre(Astre astre, float tpf) {
        if (astre instanceof Planet) {
            Planet planet = (Planet) astre;
            planet.getOrbitNode().rotate(0, planet.getRevolutionSpeed() * tpf * FastMath.DEG_TO_RAD, 0);
        }
    }

    public Geometry getGeometry() {
        return geometry;
    }
    public float getRotationSpeed() {
        return rotationSpeed;
    }
}
