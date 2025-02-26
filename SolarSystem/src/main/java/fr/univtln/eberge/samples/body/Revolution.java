package fr.univtln.eberge.samples.body;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.univtln.eberge.samples.body.Planet;

public class Revolution {

    public static void revolvePlanet(Planet planet, float tpf) {
        // **Angle de révolution de la planète (en fonction de sa période orbitale)**
        float angle = planet.getRevolutionSpeed() * tpf;

        // **Applique la rotation autour du Soleil**
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(angle, Vector3f.UNIT_Y);

        // **Appliquer la rotation au noeud de révolution**
        planet.getOrbitNode().rotate(rotation);
    }
}
