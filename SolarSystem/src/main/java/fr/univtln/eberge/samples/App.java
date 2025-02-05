package fr.univtln.eberge.samples;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import fr.univtln.eberge.samples.body.Astre;
import fr.univtln.eberge.samples.body.Planet;
import fr.univtln.eberge.samples.body.Sun;

public class App extends SimpleApplication {

    private Planet[] planets;
    private float speedFactor = 1.0f;
    private BitmapText hudText;
    private boolean paused = false;
    private Sun sun;

    public static void main(String[] args) {
        App app = new App();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Système Solaire");
        settings.setWidth(1920);
        settings.setHeight(1080);
        app.setSettings(settings);
        app.start();
    }


    @Override
    public void simpleInitApp() {

        // Paramètres de la caméra
        flyCam.setMoveSpeed(1000);
        flyCam.setRotationSpeed(1);
        flyCam.setUpVector(Vector3f.UNIT_Y);

        // Position de la caméra
        cam.setLocation(new Vector3f(500, 500, 1800));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setFrustumFar(30000);

        // Création du système solaire
        Node solarSystem = new Node("Système Solaire");
        rootNode.attachChild(solarSystem);

        // Création du Soleil
        sun = new Sun(assetManager, rootNode);
        solarSystem.attachChild(sun.getGeometry());
        sun.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * -90, Vector3f.UNIT_X));

        // Création de l'espace
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(), "Textures/Planets/space_tex.jpg", SkyFactory.EnvMapType.SphereMap));

        // Liste des planètes
        String[] planetNames = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        float[] sizes = {3.8f, 9.5f, 10.0f, 5.3f, 100.98f, 90.14f, 39.8f, 38.6f};
        float[] distances = {100, 160, 240, 380, 600, 1000, 1400, 1800};
        float[] rotationSpeeds = {58.6f, -243.0f, 1.0f, 1.03f, 0.41f, 0.45f, -0.72f, 0.67f};
        float[] revolutionSpeeds = {4.15f, 1.62f, 1.0f, 0.53f, 0.084f, 0.034f, 0.011f, 0.006f};

        planets = new Planet[planetNames.length];

        for (int i = 0; i < planetNames.length; i++) {
            planets[i] = new Planet(planetNames[i], sizes[i], 109.0f + distances[i], rotationSpeeds[i], revolutionSpeeds[i],
                    "Textures/Planets/" + planetNames[i].toLowerCase() + "_tex" + ".jpg", assetManager);
            planets[i].setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * -90, Vector3f.UNIT_X));
//            planets[i].setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * inclinations[i], Vector3f.UNIT_Z));

            solarSystem.attachChild(planets[i].getOrbitNode());
        }

        initKeys();
        initHUD();
    }

    @Override
    public void simpleUpdate(float tpf) {

        sun.rotateAstre(sun, tpf * speedFactor);

        for (Planet planet : planets) {
            Astre.revolveAstre(planet, tpf * speedFactor);
            Astre.rotateAstre(planet, tpf * speedFactor);
        }
        updateHUD();
    }


    // Initialisation des touches
    private void initKeys() {
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SpeedDown", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "SpeedUp","SpeedDown","Reverse","Pause");
    }

    // Gestion des touches
    private final ActionListener actionListener = (name, isPressed, tpf) -> {
        System.out.println(name + " : " + isPressed);
        if (isPressed) {
            switch (name) {
                case "SpeedUp" -> {
                    speedFactor *= 2; // Double la vitesse

                    System.out.println("Vitesse multipliée : " + speedFactor);
                }
                case "SpeedDown" -> {
                    speedFactor /= 2; // Divise la vitesse par 2

                    System.out.println("Vitesse réduite : " + speedFactor);
                }
                case "Reverse" -> {
                    speedFactor *= -1; // Inverse la direction

                    System.out.println("Inversion de la direction, facteur : " + speedFactor);
                }
                case "Pause" -> {
                    if (paused) {
                        speedFactor = 1.0f; // Remet la vitesse à 1
                        paused = false;
                    } else {
                        speedFactor = 0.0f; // Met la vitesse à 0
                        paused = true;
                    }
                }
            }
        }
    };

    // Initialisation du HUD
    private void initHUD() {
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        hudText = new BitmapText(font, false);
        hudText.setSize(font.getCharSet().getRenderedSize() * 2);
        hudText.setColor(com.jme3.math.ColorRGBA.White);
        hudText.setText("Vitesse : x1.0 | Direction : +");
        hudText.setLocalTranslation(10, settings.getHeight() - 10, 0);
        guiNode.attachChild(hudText);
    }

    // Mise à jour du HUD
    private void updateHUD() {
        String direction = (speedFactor > 0) ? "+" : "-";
        hudText.setText(String.format("Vitesse : x%.2f | Direction : %s", Math.abs(speedFactor), direction));
    }
}
