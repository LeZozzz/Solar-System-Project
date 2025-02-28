package fr.univtln.eberge.solarsystem.controls.movements;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;

public class Revolution {
    public static void revolvePlanet(Planet planet, double time) {
        float angle = (float)(((FastMath.TWO_PI / (planet.getRevolutionPeriod()*365*24*60*60)) * time)%FastMath.TWO_PI) ;
        Quaternion rotation = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        planet.getOrbitNode().setLocalRotation(rotation);
    }
}