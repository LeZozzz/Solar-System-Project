package fr.univtln.eberge.solarsystem.visuals.panel;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.asset.AssetManager;
import fr.univtln.eberge.solarsystem.api.PlanetInfoFetcher;
import fr.univtln.eberge.solarsystem.body.sphere.Planet;

/**
 * Panneau d'informations sur une planète.
 *  - Affiche le nom de la planète et des informations détaillées. @see PlanetInfoPanel
 * - Peut être rendu visible ou invisible. @see setVisible
 * - Peut être mis à jour avec les informations d'une planète. @see updateInfo
 * @author eberge
 */

public class PlanetInfoPanel {
    private BitmapText titleText;
    private BitmapText infoText;
    private Geometry background;
    private final Node guiNode;
    private final Node panelNode;

    public PlanetInfoPanel(Node guiNode, AssetManager assetManager) {
        this.guiNode = guiNode;
        this.panelNode = new Node("PlanetInfoPanel");

        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        /*Background */
        float width = 500;
        float height = 275;
        Quad quad = new Quad(width, height);
        background = new Geometry("PanelBackground", quad);
        Material bgMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bgMat.setColor("Color", new ColorRGBA(128 / 255.0F, 128 / 255.0F, 128 / 255.0F, 204 / 255.0F));
        background.setMaterial(bgMat);
        background.setLocalTranslation(40, 80, 0); 

        /* Title */
        titleText = new BitmapText(font, false);
        titleText.setSize(font.getCharSet().getRenderedSize() * 3);
        titleText.setColor(ColorRGBA.Yellow);
        titleText.setLocalTranslation(50, 350, 0);

        /*Informations */
        infoText = new BitmapText(font, false);
        infoText.setSize(font.getCharSet().getRenderedSize() * 2);
        infoText.setColor(ColorRGBA.White);
        infoText.setLocalTranslation(50, 300, 0);

        /* Attach to panelNode */
        panelNode.attachChild(background);
        panelNode.attachChild(titleText);
        panelNode.attachChild(infoText);
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

