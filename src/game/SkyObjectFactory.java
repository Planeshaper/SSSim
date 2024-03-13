/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Curve;
import com.jme3.texture.Texture;

/**
 *
 * @author PlaneShaper
 */
public class SkyObjectFactory {
    
    AssetManager assetManager;
    
    public SkyObjectFactory (AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    public Spatial createSkyObject() {
        Spatial worldObject = assetManager.loadModel("Models/invertsphere.j3o");
        
        worldObject.scale(20.0f);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/constellation_figures_8k.png");
        mat.setTexture("ColorMap", tex);
        worldObject.setMaterial(mat);
        
        return worldObject;
    }
}