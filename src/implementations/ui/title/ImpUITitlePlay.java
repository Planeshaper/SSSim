/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.title;

import implementations.ui.intro.*;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.FontManager;
import spatials.ui.title.NodeUITitlePlay;

/**
 *
 * @author PlaneShaper
 */
public class ImpUITitlePlay extends Node {
    
    NodeUITitlePlay nuits = new NodeUITitlePlay();
    
    public ImpUITitlePlay() {
        this.name = "ImpUISplash";
        this.attachChild(nuits);
    }
    
    public void init(FontManager fm) {
        String playString = "Play";
        String settingsString = "Settings";
        String quitString = "Quit";
        
        Node supersplashs = fm.generateToonText(playString, "playString", ColorRGBA.Green);
        Node splashs = fm.generateToonText(settingsString, "settingsString", ColorRGBA.Green);
        Node subsplashs = fm.generateToonText(quitString, "quitString", ColorRGBA.Green);
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        supersplashs.setLocalTranslation(-(((BoundingBox) supersplashs.getWorldBound()).getXExtent()), 1.5f, 0f);
        splashs.setLocalTranslation(-(((BoundingBox) splashs.getWorldBound()).getXExtent()), 0f, 0f);
        subsplashs.setLocalTranslation(-(((BoundingBox) subsplashs.getWorldBound()).getXExtent()), -1.5f, 0f);
        
        supersplashs.rotate(FastMath.PI/2, 0, 0);
        splashs.rotate(FastMath.PI/2, 0, 0);
        subsplashs.rotate(FastMath.PI/2, 0, 0);
        
        nuits.attachChild(supersplashs);
        nuits.attachChild(splashs);
        nuits.attachChild(subsplashs);
    }
}