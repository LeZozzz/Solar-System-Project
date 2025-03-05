package fr.univtln.eberge.solarsystem;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;

import fr.univtln.eberge.solarsystem.body.belt.KeplerBelt;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;
import fr.univtln.eberge.solarsystem.body.sphere.Sun;
import fr.univtln.eberge.solarsystem.controls.camera.CameraController;
import fr.univtln.eberge.solarsystem.controls.input.InputHandler;
import fr.univtln.eberge.solarsystem.controls.movements.Movement;
import fr.univtln.eberge.solarsystem.utils.time.TimeManager;
import fr.univtln.eberge.solarsystem.visuals.*;
import fr.univtln.eberge.solarsystem.visuals.color.PastelColors;
import fr.univtln.eberge.solarsystem.visuals.orbit.Orbit;
import fr.univtln.eberge.solarsystem.visuals.panel.HUD;
import fr.univtln.eberge.solarsystem.visuals.panel.PlanetInfoPanel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class App2 extends SimpleApplication {
    private List<Planet> planets = new ArrayList<>();
    private Sun sun;
    private TimeManager timeManager;
    private HUD hud;
    private double time = Instant.now().getEpochSecond();
    private List<Node> belts = new ArrayList<>();

    public static void main(String[] args) {
        App2 app = new App2();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Système Solaire");
        settings.setResolution(1920, 1080);
        settings.setFrameRate(144);
        settings.setFullscreen(true);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(1000);
        flyCam.setUpVector(Vector3f.UNIT_Y);
        cam.setLocation(new Vector3f(500, 500, 4000));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumFar(30000);
        setDisplayStatView(false);
        

        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Planets/space_tex.jpg", SkyFactory.EnvMapType.SphereMap));

        sun = new Sun(assetManager);
        sun.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * -90, Vector3f.UNIT_X));
        rootNode.attachChild(sun.getSunNode());
        rootNode.addLight(sun.getSunLight());

        Node principalBelt = KeplerBelt.createBelt(assetManager, 1200, 429f, 578f); 
        rootNode.attachChild(principalBelt);
        Node kuiperBelt = KeplerBelt.createBelt(assetManager, 10000, 4800f, 5200f); 
        rootNode.attachChild(kuiperBelt);
        belts.add(principalBelt);
        belts.add(kuiperBelt);

        createPlanets();
        timeManager = new TimeManager();
        PlanetInfoPanel planetInfoPanel = new PlanetInfoPanel(guiNode, assetManager);
        CameraController cameraController = new CameraController(inputManager, planets, planetInfoPanel, flyCam);
        stateManager.attach(cameraController);
        hud = new HUD(guiNode, assetManager, settings);

        InputHandler.initKeys(inputManager, timeManager, cameraController);
    }

    private void createPlanets() {
        String[] names = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        float[] sizes = {3.8f, 9.5f, 10.0f, 5.3f, 100.98f, 90.14f, 39.8f, 38.6f};
        float[] distances = {58, 108, 150, 228, 778, 1429, 2870, 4498};
        float[] rotationPeriods = {58.6f, -243.0f, 1.0f, 1.03f, 0.41f, 0.45f, -0.72f, 0.67f};
        float[] revolutionPeriods = {0.24f, 0.62f, 1.0f, 1.88f, 11.86f, 29.46f, 84.01f, 164.8f};
        ColorRGBA[] color = {PastelColors.MERCURY, PastelColors.VENUS, PastelColors.EARTH, PastelColors.MARS, PastelColors.JUPITER, PastelColors.SATURN, PastelColors.URANUS, PastelColors.NEPTUNE};
        for (int i = 0; i < names.length; i++) {
            Planet planet = new Planet(names[i], sizes[i], distances[i], rotationPeriods[i], revolutionPeriods[i],
                    "Textures/Planets/" + names[i].toLowerCase() + "_tex" + ".jpg", assetManager);

            planet.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * -90, Vector3f.UNIT_X));
            planets.add(planet);
            rootNode.attachChild(planet.getOrbitNode());
            rootNode.attachChild(Orbit.createOrbit(distances[i]+109f, assetManager, color[i]));

            if (planet.getName().equals("Saturn")) {
                Geometry rings = Planet.createPlanetRings("Textures/Planets/saturn_ring_tex.png",assetManager); 
                rings.setLocalTranslation(planet.getLocalTranslation()); 
                rootNode.attachChild(rings);
                planet.getOrbitNode().attachChild(rings);
            }
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        time += tpf *timeManager.getSpeedFactor();
        for (Planet planet : planets) {
            Movement.revolvePlanet(planet, time);
            Movement.rotatePlanet(planet, time);
        }
        Movement.revolveBelt(belts.get(0), time, 5.93f);
        Movement.revolveBelt(belts.get(1), time, 200f);
        hud.updateHUD(timeManager, time);
    }
}