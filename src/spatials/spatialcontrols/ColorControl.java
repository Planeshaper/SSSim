/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spatials.spatialcontrols;

import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author PlaneShaper
 */
public class ColorControl extends AbstractControl {
    
    boolean changeColor = false;
    boolean changeChannel = false;
    ColorRGBA currentShade;
    ColorRGBA newShade;
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (changeColor) {
            ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).set(new ColorRGBA(newShade));
            changeColor = false;
        } else if (changeChannel) {
            ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).set(new ColorRGBA(newShade));
            changeChannel = false;
            System.out.println(((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getAlpha());
        }
    }
    
    public void changeColor(ColorRGBA color) {
        newShade = new ColorRGBA(color);
        changeColor = true;
    }
    
    public void changeChannel(float x, char c) {
        newShade = new ColorRGBA(((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()));
        switch (c) {
            case 'r':
                newShade.r = x;
                break;
            case 'g':
                newShade.g = x;
                break;
            case 'b':
                newShade.b = x;
                break;
            case 'a':
                newShade.a = x;
                break;
            default:
                return;
        }
        changeChannel = true;
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final ColorControl control = new ColorControl();
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}