/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spatials.spatialcontrols;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * A linear, non-asymptotic interpolation for a Spatial to undergo an increase, 
 * then equal decrease, in scale.
 * @see WaypointsControl.java
 * @author PlaneShaper
 */
public class BreathControl extends AbstractControl {
    
    boolean breathing = false;
    boolean breathIn = false;
    boolean breathOut = false;
    float scale = 1.75f;
    Vector3f breathScale = new Vector3f(1.3f, 1.3f, 1.3f);
    float totalDistance = 0f;
    float remDistance = 0f;

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (breathing) {
            if (breathIn) {
                float distance = scale*tpf;
                if (distance >= remDistance) {
                    spatial.setLocalScale(breathScale);
                    breathIn = false;
                    breathOut = true;
                    remDistance = Vector3f.UNIT_XYZ.distance(breathScale);
                } else {
                    remDistance -= distance;
                    Vector3f nextWP = breathScale;
                    Vector3f translation = spatial.getLocalScale();
                    translation.interpolateLocal(nextWP, distance/spatial.getLocalScale().distance(nextWP));
                    spatial.setLocalScale(translation);
                }
            } else if (breathOut) {
                float distance = scale*tpf;
                if (distance >= remDistance) {
                    spatial.setLocalScale(Vector3f.UNIT_XYZ);
                    breathOut = false;
                    breathing = false;
                    remDistance = 0f;
                } else {
                    remDistance -= distance;
                    Vector3f nextWP = Vector3f.UNIT_XYZ;
                    Vector3f translation = spatial.getLocalScale();
                    translation.interpolateLocal(nextWP, distance/spatial.getLocalScale().distance(nextWP));
                    spatial.setLocalScale(translation);
                }
            }
        }
    }
    
    public boolean isBreathing() {
        //System.out.println(breathing);
        return breathing;
    }
    
    public void breathe() {
        remDistance = breathScale.distance(Vector3f.UNIT_XYZ);
        breathing = true;
        breathIn = true;
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        final BreathControl control = new BreathControl();
        control.setSpatial(spatial);
        return control;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}