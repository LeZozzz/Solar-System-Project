package fr.univtln.eberge.solarsystem.controls.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import fr.univtln.eberge.solarsystem.controls.camera.CameraController;
import fr.univtln.eberge.solarsystem.utils.TimeManager;

public class InputHandler {
    public static void initKeys(InputManager inputManager, TimeManager timeManager, CameraController cameraController) {
        inputManager.addMapping("SpeedUp", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SpeedDown", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("CameraNext", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("CameraPrev", new KeyTrigger(KeyInput.KEY_B));

        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed) {
                    switch (name) {
                        case "SpeedUp" -> timeManager.setSpeedFactor(timeManager.getSpeedFactor() * 2);
                        case "SpeedDown" -> timeManager.setSpeedFactor(timeManager.getSpeedFactor() / 2);
                        case "Reverse" -> timeManager.setSpeedFactor(timeManager.getSpeedFactor() * -1);
                        case "Pause" -> timeManager.setSpeedFactor(timeManager.getSpeedFactor() == 0 ? 1.0f : 0.0f);
                    }
                }
            }
        }, "SpeedUp", "SpeedDown", "Reverse", "Pause", "CameraNext", "CameraPrev");
    }
}