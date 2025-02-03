package fr.univtln.eberge.samples.movement;

import fr.univtln.eberge.samples.body.Planet;
import com.jme3.math.FastMath;


public class Revolution {
    public static void revolvePlanet(Planet planet, float tpf) {
        planet.getOrbitNode().rotate(0, planet.getRevolutionSpeed() * tpf * FastMath.DEG_TO_RAD, 0);
    }
}
