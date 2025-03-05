package fr.univtln.eberge.solarsystem.visuals.panel;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

import fr.univtln.eberge.solarsystem.utils.time.TimeManager;

import com.jme3.asset.AssetManager;

public class HUD {
    private BitmapText hudText;
    private Node guiNode;

    public HUD(Node guiNode, AssetManager assetManager, AppSettings settings) {
        this.guiNode = guiNode;
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        hudText = new BitmapText(font, false);
        hudText.setSize(font.getCharSet().getRenderedSize() * 2);
        hudText.setColor(ColorRGBA.White);
        hudText.setText("Vitesse : x1.0 | Direction : + | Temps : 0 ans 0j 0h 0m 0s");
        hudText.setLocalTranslation(10, settings.getHeight() - 10, 0);
        guiNode.attachChild(hudText);
    }

    public void updateHUD(TimeManager timeManager, double time) {
        String direction = (timeManager.getSpeedFactor() > 0) ? "+" : "-";
        String formattedTime = timeManager.getFormattedTime(time);
        hudText.setText(String.format("Vitesse : x%.2f | Direction : %s | Temps : %s",
                Math.abs(timeManager.getSpeedFactor()), direction, formattedTime));
    }
    public void setVisible(boolean visible) {
        hudText.setCullHint(visible ? Spatial.CullHint.Never : Spatial.CullHint.Always);
    }

//    private BitmapText hudText;
//    private Node guiNode;
//
//    public HUD(Node guiNode, AssetManager assetManager, AppSettings settings) {
//        this.guiNode = guiNode;
//        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        hudText = new BitmapText(font, false);
//        hudText.setSize(font.getCharSet().getRenderedSize() * 2);
//        hudText.setColor(ColorRGBA.White);
//        hudText.setText("Vitesse : x1.0 | Direction : + | Temps : 0 ans 0j 0h 0m 0s");
//        hudText.setLocalTranslation(10, settings.getHeight() - 10, 0);
//        guiNode.attachChild(hudText);
//    }
//
//    public void updateHUD(TimeManager timeManager) {
//        String direction = (timeManager.getSpeedFactor() > 0) ? "+" : "-";
//        String time = timeManager.getFormattedTime();
//        int years = timeManager.getElapsedYears();
//        hudText.setText(String.format("Vitesse : x%.2f | Direction : %s | Temps : %d ans %s", Math.abs(timeManager.getSpeedFactor()), direction, years,  time));
//    }
}