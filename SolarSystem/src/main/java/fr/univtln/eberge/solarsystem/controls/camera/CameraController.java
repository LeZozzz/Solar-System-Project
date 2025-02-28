package fr.univtln.eberge.solarsystem.controls.camera;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import fr.univtln.eberge.solarsystem.visuals.PlanetInfoPanel;

import java.util.List;


public class CameraController extends BaseAppState {
    private ChaseCamera chaseCam;
    private boolean isChaseCamActive = false;
    private SimpleApplication app;
    private InputManager inputManager;
    private List<Planet> planets;
    private static int currentPlanetIndex = 1; // Terre par défaut
    private PlanetInfoPanel planetInfoPanel;
    private FlyByCamera flyCam;


    public CameraController(InputManager inputManager, List<Planet> planets, PlanetInfoPanel planetInfoPanel, FlyByCamera flyCam) {
        this.inputManager = inputManager;
        this.planets = planets;
        this.planetInfoPanel = planetInfoPanel;
        this.flyCam = flyCam;
    }

    @Override
    protected void initialize(Application app) {
        this.app = (SimpleApplication) app;
        chaseCam = new ChaseCamera(app.getCamera(), planets.get(currentPlanetIndex).getBodyNode(), inputManager);
        chaseCam.setDefaultDistance(100);
        chaseCam.setMaxDistance(10000);
        chaseCam.setZoomInTrigger(new KeyTrigger(KeyInput.KEY_UP));
        chaseCam.setZoomOutTrigger(new KeyTrigger(KeyInput.KEY_DOWN));
        chaseCam.setZoomSensitivity(250);
        chaseCam.getLookAtOffset().set(0, 0, 0);
        planetInfoPanel.updateInfo(planets.get(currentPlanetIndex));

        initKeys();
    }

    private void initKeys() {
        inputManager.addMapping("NextPlanet", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("PrevPlanet", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addListener(actionListener, "NextPlanet");
        inputManager.addListener(actionListener, "PrevPlanet");
        inputManager.addMapping("ToggleCamera", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(actionListener, "ToggleCamera");
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed && name.equals("NextPlanet")) {
                switchToNextPlanet();
            }
            if (isPressed && name.equals("PrevPlanet")) {
                switchToPrevPlanet();
            }
            if (isPressed) {
                switch (name) {
                    case "NextPlanet":
                        switchToNextPlanet();
                        break;
                    case "ToggleCamera":
                        toggleCameraMode();
                        break;
                }
            }

        }
    };

    private void toggleCameraMode() {
        isChaseCamActive = !isChaseCamActive;

        if (isChaseCamActive) {
            chaseCam.setEnabled(true);
            flyCam.setEnabled(false);  // Désactiver FlyCam
            inputManager.setCursorVisible(true);
            planetInfoPanel.setVisible(true);
        } else {
            chaseCam.setEnabled(false);
            flyCam.setEnabled(true);  // Activer FlyCam
            flyCam.setDragToRotate(true); // Permet la rotation avec la souris
            inputManager.setCursorVisible(false);
            planetInfoPanel.setVisible(false);
        }
    }



    private void switchToNextPlanet() {
        if(flyCam.isEnabled()){
            return;
        }
        currentPlanetIndex = (currentPlanetIndex + 1) % planets.size();
        chaseCam.setSpatial(planets.get(currentPlanetIndex).getBodyNode());
        chaseCam.setDefaultDistance(200);

        // Mise à jour des infos de la planète avec l'API
        planetInfoPanel.updateInfo(planets.get(currentPlanetIndex));
    }
    private void switchToPrevPlanet() {
        if(flyCam.isEnabled()){
            return;
        }
        currentPlanetIndex = (((currentPlanetIndex - 1) % planets.size()) + planets.size())% planets.size();
        chaseCam.setSpatial(planets.get(currentPlanetIndex).getBodyNode());
        planetInfoPanel.updateInfo(planets.get(currentPlanetIndex));
    }
    private void updateChaseCamUI(boolean isVisible) {
        // Masquer/Afficher les informations de la planète suivie (HUD, fiche descriptive, etc.)
        if (isVisible) {
            System.out.println("Affichage des informations de la planète");
            // Ici, appelle la méthode qui affiche les infos (exemple : showPlanetInfo())
        } else {
            System.out.println("Masquage des informations de la planète");
            // Ici, appelle la méthode qui cache les infos (exemple : hidePlanetInfo())
        }
    }


    @Override
    protected void cleanup(Application app) {}

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
