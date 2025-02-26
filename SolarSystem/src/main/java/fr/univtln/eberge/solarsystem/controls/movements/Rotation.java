package fr.univtln.eberge.solarsystem.controls.movements;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;
import fr.univtln.eberge.solarsystem.body.sphere.Sun;

public class Rotation {
    public static void rotatePlanet(Planet planet, float tpf) {
        float angle = (FastMath.TWO_PI / planet.getRotationPeriod()) * tpf;
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        planet.getPlanetNode().rotate(rotation);
    }

    public static void rotateSun(Sun sun, float tpf) {
        float angle = (FastMath.TWO_PI / 25.0f) * tpf; // Approx rotation period of the Sun in days
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        sun.getSunNode().rotate(rotation);
    }
}