/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.world.title;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import implementations.world.game.*;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import spatials.world.game.NodeWorldGame;
import spatials.world.title.NodeWorldTitle;

/**
 *
 * @author PlaneShaper
 */
public class ImpWorldTitle extends Node {
    
    Node analogClickables;
    NodeWorldTitle nwt = new NodeWorldTitle();
    Node cwt = new Node();
    
    public ImpWorldTitle() {
        this.name = "ImpWorldTitle";
        this.attachChild(nwt);
    }
    
    public void init(AssetManager assetManager) {
        /*
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(ColorRGBA.Yellow));
        geom.setMaterial(mat);
        geom.move(0.5f, 0.5f, 0.5f);
        
        nwt.attachChild(geom);
        */
    }
}