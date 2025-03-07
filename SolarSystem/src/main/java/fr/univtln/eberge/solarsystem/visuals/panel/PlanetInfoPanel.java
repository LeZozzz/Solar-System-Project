// package fr.univtln.eberge.solarsystem.visuals.panel;

// import com.jme3.font.BitmapFont;
// import com.jme3.font.BitmapText;
// import com.jme3.scene.Node;
// import com.jme3.asset.AssetManager;
// import com.jme3.math.ColorRGBA;
// import fr.univtln.eberge.solarsystem.api.PlanetInfoFetcher;
// import fr.univtln.eberge.solarsystem.body.sphere.Planet;

// public class PlanetInfoPanel {
//     private BitmapText titleText;
//     private BitmapText infoText;
//     private final Node guiNode;
//     private final Node panelNode;

//     public PlanetInfoPanel(Node guiNode, AssetManager assetManager) {
//         this.guiNode = guiNode;
//         this.panelNode = new Node("PlanetInfoPanel");
//         BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
//         // Titre
//         titleText = new BitmapText(font, false);
//         titleText.setSize(font.getCharSet().getRenderedSize() * 3);
//         titleText.setColor(ColorRGBA.Yellow);
//         titleText.setLocalTranslation(50, 400, 0); 
//         // Informations
//         infoText = new BitmapText(font, false);
//         infoText.setSize(font.getCharSet().getRenderedSize() * 2);
//         infoText.setColor(ColorRGBA.White);
//         infoText.setLocalTranslation(50, 350, 0); 

//         guiNode.attachChild(titleText);
//         guiNode.attachChild(infoText);
//         panelNode.attachChild(infoText);
//         panelNode.attachChild(titleText);
//     }

//     public void updateInfo(Planet planet) {
//         titleText.setText(PlanetInfoFetcher.getPlanetName(planet.getName()));
//         String info = PlanetInfoFetcher.getPlanetInfo(planet.getName());
//         infoText.setText(info);
//     }

//     public void setVisible(boolean visible) {
//         if (visible) {
//             guiNode.attachChild(panelNode); 
//         } else {
//             guiNode.detachChild(panelNode); 
//         }
//     }

// }
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

        // 🔹 Création d'un fond semi-transparent flou
        float width = 500;
        float height = 275;
        Quad quad = new Quad(width, height);
        background = new Geometry("PanelBackground", quad);
        Material bgMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bgMat.setColor("Color", new ColorRGBA(128 / 255.0F, 128 / 255.0F, 128 / 255.0F, 204 / 255.0F));
        background.setMaterial(bgMat);
        background.setLocalTranslation(40, 80, 0); 

        // 🔹 Titre
        titleText = new BitmapText(font, false);
        titleText.setSize(font.getCharSet().getRenderedSize() * 3);
        titleText.setColor(ColorRGBA.Yellow);
        titleText.setLocalTranslation(50, 350, 0);

        // 🔹 Informations détaillées
        infoText = new BitmapText(font, false);
        infoText.setSize(font.getCharSet().getRenderedSize() * 2);
        infoText.setColor(ColorRGBA.White);
        infoText.setLocalTranslation(50, 300, 0);

        // Ajout des éléments au panneau
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

