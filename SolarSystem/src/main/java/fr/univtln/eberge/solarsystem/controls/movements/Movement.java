package fr.univtln.eberge.solarsystem.controls.movements;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import fr.univtln.eberge.solarsystem.body.sphere.Moon;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;

public class Movement {
    public static void rotatePlanet(Planet planet, double time) {
        float angle = (float)(((FastMath.TWO_PI / (planet.getRotationPeriod()*24*60*60)) * time)%FastMath.TWO_PI);
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        planet.getPlanetNode().setLocalRotation(rotation);
    }
    public static void revolveBelt(Node asteroidBelt, double time, float period) {
        float angle = (float)(((FastMath.TWO_PI / (period*365*24*60*60)) * time)%FastMath.TWO_PI) ;
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        asteroidBelt.setLocalRotation(rotation);
    }
    // public static void rotateSun(Sun sun, float tpf) {
    //     float angle = (FastMath.TWO_PI / 25.0f) * tpf; // Approx rotation period of the Sun in days
    //     Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
    //     sun.getSunNode().rotate(rotation);
    // }
    public static void revolvePlanet(Planet planet, double time) {
        planet.getOrbitNode().setLocalTranslation(planet.calcTrajectory(time));
    }
    public static void revolveMoon(Moon moon, double time) {
        moon.getOrbitNode().setLocalTranslation(moon.calcTrajectory(time));
    }
    public static void rotateMoon(Moon moon, double time) {
        float angle = (float)(((FastMath.TWO_PI / (moon.getRotationPeriod()*24*60*60)) * time)%FastMath.TWO_PI);
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        moon.getMoonNode().setLocalRotation(rotation);
    }
}