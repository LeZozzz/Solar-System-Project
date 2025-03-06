package fr.univtln.eberge.solarsystem.visuals.orbit;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Curve;

import fr.univtln.eberge.solarsystem.body.sphere.Moon;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;

import com.jme3.math.Vector3f;

public class Orbit {
    public static Geometry createOrbit(Planet planet, AssetManager assetManager, ColorRGBA color) {
        int points = 1024; // Plus de points = orbite plus lisse
        Vector3f[] vertices = new Vector3f[points + 1];

        for (int i = 0; i <= points; i++) {
            vertices[i] = planet.calcTrajectory(i*planet.getRevolutionPeriod()*365*24*60*60/(points-1));
        }
   
        // Création de la courbe de l'orbite
        Curve curve = new Curve(vertices, 1);
        Geometry orbitGeometry = new Geometry("Orbit_" + planet.getName(), curve);
    
        // Matériau pour l'orbite avec couleur personnalisée
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        orbitGeometry.setMaterial(mat);
    
        return orbitGeometry;
    }
    public static Geometry createMoonOrbit(Moon moon, AssetManager assetManager, ColorRGBA color) {
        int points = 1024; // Plus de points = orbite plus lisse
        Vector3f[] vertices = new Vector3f[points + 1];
    
        for (int i = 0; i <= points; i++) {
            vertices[i] = moon.calcTrajectory(i*moon.getRevolutionPeriod()*365*24*60*60/(points-1));
        }
    
        // Création de la courbe de l'orbite
        Curve curve = new Curve(vertices, 1);
        Geometry orbitGeometry = new Geometry("Orbit_" + moon.getName(), curve);
    
        // Matériau pour l'orbite avec couleur personnalisée
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        orbitGeometry.setMaterial(mat);
    
        return orbitGeometry;
    }
    

}
