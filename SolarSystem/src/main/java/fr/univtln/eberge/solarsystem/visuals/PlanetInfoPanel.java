package fr.univtln.eberge.solarsystem.visuals;

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

        // 🌍 Titre en grand et en couleur
        titleText = new BitmapText(font, false);
        titleText.setSize(font.getCharSet().getRenderedSize() * 3);
        titleText.setColor(ColorRGBA.Yellow);
        titleText.setLocalTranslation(50, 400, 0); // Position en haut

        // 📜 Texte des informations
        infoText = new BitmapText(font, false);
        infoText.setSize(font.getCharSet().getRenderedSize() * 2);
        infoText.setColor(ColorRGBA.White);
        infoText.setLocalTranslation(50, 350, 0); // Position sous le titre

        // Ajout à l'interface graphique
        guiNode.attachChild(titleText);
        guiNode.attachChild(infoText);
        panelNode.attachChild(infoText);
        panelNode.attachChild(titleText);
    }

    public void updateInfo(Planet planet) {
        // 🎯 Met à jour le titre avec le nom de la planète
        titleText.setText(PlanetInfoFetcher.getPlanetName(planet.getName()));

        // 🔍 Récupère les informations depuis l’API
        String info = PlanetInfoFetcher.getPlanetInfo(planet.getName());

        // // 📝 Mise en forme des données avec des puces
        // String formattedInfo = info.replace("\n", "\n• "); // Ajoute des puces devant chaque ligne
        // formattedInfo = "• " + formattedInfo; // Ajoute la première puce

        infoText.setText(info);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            guiNode.attachChild(panelNode);  // ✅ Afficher le panneau
        } else {
            guiNode.detachChild(panelNode);  // ✅ Masquer le panneau
        }
    }

}
