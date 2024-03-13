/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.world.splash;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import implementations.world.game.*;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import game.FontManager;
import spatials.world.intro.NodeWorldIntroSplash;

/**
 *
 * @author PlaneShaper
 */
public class ImpWorldIntro extends Node {
    
    Node analogClickables;
    NodeWorldIntroSplash nwis = new NodeWorldIntroSplash();
    Node cwis = new Node();
    
    public ImpWorldIntro() {
        this.name = "ImpWorldIntro";
        this.attachChild(nwis);
    }
    
    public void init(AssetManager assetManager) {
        /*
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(ColorRGBA.Blue));
        geom.setMaterial(mat);
        geom.move(0.5f, 0.5f, 0.5f);
        
        nwis.attachChild(geom);
        */
    }
}