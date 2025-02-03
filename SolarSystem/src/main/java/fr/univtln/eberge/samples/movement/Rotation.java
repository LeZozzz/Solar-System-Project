package fr.univtln.eberge.samples.movement;

import com.jme3.math.FastMath;
import fr.univtln.eberge.samples.body.Planet;

public class Rotation {
    public static void rotatePlanet(Planet planet, float tpf) {
        planet.getGeometry().rotate(0, planet.getRotationSpeed() * tpf * FastMath.DEG_TO_RAD, 0);
    }
}
