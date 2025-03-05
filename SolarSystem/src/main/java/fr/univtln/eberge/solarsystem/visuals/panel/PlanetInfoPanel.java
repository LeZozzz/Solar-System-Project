package fr.univtln.eberge.solarsystem.visuals.panel;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import fr.univtln.eberge.solarsystem.api.PlanetInfoFetcher;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;

public class PlanetInfoPanel {
    private BitmapText titleText;
    private BitmapText infoText;
    private final Node guiNode;
    private final Node panelNode;

    public PlanetInfoPanel(Node guiNode, AssetManager assetManager) {
        this.guiNode = guiNode;
        this.panelNode = new Node("PlanetInfoPanel");
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        titleText = new BitmapText(font, false);
        titleText.setSize(font.getCharSet().getRenderedSize() * 3);
        titleText.setColor(ColorRGBA.Yellow);
        titleText.setLocalTranslation(50, 400, 0); 

        infoText = new BitmapText(font, false);
        infoText.setSize(font.getCharSet().getRenderedSize() * 2);
        infoText.setColor(ColorRGBA.White);
        infoText.setLocalTranslation(50, 350, 0); 

        guiNode.attachChild(titleText);
        guiNode.attachChild(infoText);
        panelNode.attachChild(infoText);
        panelNode.attachChild(titleText);
    }

    public void updateInfo(Planet planet) {
        titleText.setText(PlanetInfoFetcher.getPlanetName(planet.getName()));
        String info = PlanetInfoFetcher.getPlanetInfo(planet.getName());
        infoText.setText(info);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            guiNode.attachChild(panelNode); 
        } else {
            guiNode.detachChild(panelNode); 
        }
    }

}
