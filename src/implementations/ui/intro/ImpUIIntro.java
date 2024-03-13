/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.intro;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.FontManager;
import spatials.ui.intro.NodeUIIntroSplash;

/**
 *
 * @author PlaneShaper
 */
public class ImpUIIntro extends Node {
    
    NodeUIIntroSplash nuiis = new NodeUIIntroSplash();
    FontManager fm;
    
    public ImpUIIntro(FontManager fm) {
        this.name = "ImpUISplash";
        this.attachChild(nuiis);
        this.fm = fm;
    }
    
    public void init() {
        String supersplash = "Star";
        String splash = "System";
        String subsplash = "Defense";
        
        Node supersplashs = fm.generateToonText(supersplash, "supersplash", ColorRGBA.Green);
        Node splashs = fm.generateToonText(splash, "splash", ColorRGBA.Green);
        Node subsplashs = fm.generateToonText(subsplash, "subsplash", ColorRGBA.Green);
        
        /*
        Node ul = fm.generateToonText(supersplash, "supersplash", ColorRGBA.Red);
        Node ur = fm.generateToonText(supersplash, "splash", ColorRGBA.Red);
        Node ll = fm.generateToonText(supersplash, "subsplash", ColorRGBA.Red);
        Node lr = fm.generateToonText(supersplash, "subsplash", ColorRGBA.Red);
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        ur.setLocalTranslation(7.3f, 4.1f, 0f);
        ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        ul.rotate(FastMath.PI/2, 0, 0);
        ur.rotate(FastMath.PI/2, 0, 0);
        ll.rotate(FastMath.PI/2, 0, 0);
        lr.rotate(FastMath.PI/2, 0, 0);
        
        nuiis.attachChild(ul);
        nuiis.attachChild(ur);
        nuiis.attachChild(ll);
        nuiis.attachChild(lr);
        */
        
        supersplashs.setLocalTranslation(-(((BoundingBox) supersplashs.getWorldBound()).getXExtent()), 1.5f, 0f);
        splashs.setLocalTranslation(-(((BoundingBox) splashs.getWorldBound()).getXExtent()), 0f, 0f);
        subsplashs.setLocalTranslation(-(((BoundingBox) subsplashs.getWorldBound()).getXExtent()), -1.5f, 0f);
        
        supersplashs.rotate(FastMath.PI/2, 0, 0);
        splashs.rotate(FastMath.PI/2, 0, 0);
        subsplashs.rotate(FastMath.PI/2, 0, 0);
        
        nuiis.attachChild(supersplashs);
        nuiis.attachChild(splashs);
        nuiis.attachChild(subsplashs);
    }
}