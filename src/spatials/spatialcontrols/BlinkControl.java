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
public class BlinkControl extends AbstractControl {
    
    boolean blinking = false;
    boolean blinkIn = false;
    boolean blinkOut = false;
    boolean blinkOnce = true;
    float scale = 1.75f;
    float blinkValue = 0.25f;
    float blinkValue2 = 1.0f;
    ColorRGBA currentShade;
    
    boolean fade = false;
    boolean fadeIn = false;
    boolean fadeOut = false;
    float fadeScale = 1.75f;
    float fadeValue = 1.0f;
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (blinking) {
            currentShade = ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue());
            if (blinkIn) {
                currentShade.a = Math.min(currentShade.a+(scale*tpf), blinkValue2);
                
                ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a = currentShade.a;
                
                if (currentShade.a == blinkValue2) {
                    blinkIn = false;
                    blinkOut = true;
                }
                
                /*
                ((Geometry) spatial).getMaterial().setColor("Color", new ColorRGBA(
                        //((Arrays.asList(String.valueOf(((Geometry) spatial).getMaterial().getParam("Color")).split(" "))).stream().map(Float::valueOf).collect(Collectors.toList())).subList(4, 7), 
                        //Red Value
                        ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getRed(), 
                        //Green Value
                        ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getGreen(), 
                        //Blue Value
                        ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getBlue(), 
                        //Alpha Value
                        //Float.parseFloat((new ArrayDeque<>(Arrays.asList(String.valueOf(((Geometry) spatial).getMaterial().getParam("Color")).split(" ")))).getLast())
                        ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getAlpha()
                        +(scale*tpf)
                ));
                */
            } else if (blinkOut) {
                currentShade.a = Math.max(currentShade.a-(scale*tpf), blinkValue);
                
                ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a = currentShade.a;
                
                if (currentShade.a == blinkValue) {
                    blinkOut = false;
                    blinkIn = true;
                }
            } else if (fade) {
                if (fadeIn) {
                    currentShade.a = Math.min(currentShade.a+(fadeScale*tpf), fadeValue);
                    
                    ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a = currentShade.a;
                    
                    if (currentShade.a == fadeValue) {
                        fadeIn = false;
                        fade = false;
                        blinking = false;
                        fadeScale = 1.75f;
                        fadeValue = 1.0f;
                    }
                } else if (fadeOut) {
                    currentShade.a = Math.max(currentShade.a-(fadeScale*tpf), fadeValue);
                    
                    ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a = currentShade.a;
                    
                    if (currentShade.a == fadeValue) {
                        fadeOut = false;
                        fade = false;
                        blinking = false;
                        fadeScale = 1.75f;
                        fadeValue = 1.0f;
                    }
                } else {
                    fadeIn = false;
                    fadeOut = false;
                    fade = false;
                    blinking = false;
                    fadeScale = 1.75f;
                    fadeValue = 1.0f;
                }
            }
        }
    }
    
    public boolean isBlinking() {
        //System.out.println(blinking);
        return blinking;
    }
    
    public void blink() {
        if (blinking) {
            return;
        }
        blinkValue2 = ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getAlpha();
        blinking = true;
        blinkIn = true;
        blinkOnce = true;
    }
    
    public void stopBlinking() {
        ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a = blinkValue2;
        blinking = false;
        blinkIn = false;
        blinkOut = false;
        blinkOnce = true;
    }
    
    public void keepBlinking() {
        if (blinking) {
            return;
        }
        blinkValue2 = ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getAlpha();
        blinking = true;
        blinkIn = true;
        blinkOnce = false;
    }
    
    public void fade(float scale, float destA) {
        if (blinking) {
            System.out.println("Already Blinking");
            return;
        }
        blinking = true;
        fade = true;
        fadeScale = scale;
        
        if (destA > 1.0f) {
            fadeValue = 1.0f;
        } else if (destA < 0.0f) {
            fadeValue = 0.0f;
        } else {
            fadeValue = destA;
        }
        
        System.out.println(fadeScale + " " + fadeValue + " " + ((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).getAlpha());
        
        if (((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a > fadeValue) {
            fadeOut = true;
        } else if (((ColorRGBA) ((Geometry) spatial).getMaterial().getParam("Color").getValue()).a < fadeValue) {
            fadeIn = true;
            System.out.println(fadeIn);
        } else {
            blinking = false;
            fade = false;
            fadeScale = 1.75f;
            fadeValue = 1.0f;
        }
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final BlinkControl control = new BlinkControl();
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}