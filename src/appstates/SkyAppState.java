/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import states.States;
import states.statessky.*;

/**
 *
 * @author PlaneShaper
 */
public class SkyAppState extends BaseAppState {
    
    ViewPort view;
    
    Node skyNode;
    
    States previousState;
    States programState;
    
    StatesSkyIntro ssi;
    StatesSkyTitle sst;
    StatesSkyGame ssg;
    StatesSkyPause ssp;
    StatesSkySave sss;
    StatesSkyClose ssc;
    
    Vector3f camLook = new Vector3f(0,0,0);
    Vector3f camUp = new Vector3f(0,0,0);
    
    public SkyAppState(ViewPort view, Node skyNode, States previousState, States state) {
        this.view = view;
        
        this.skyNode = skyNode;
        
        this.previousState = previousState;
        this.programState = state;
        
        ssi = StatesSkyIntro.DEFAULT;
        sst = StatesSkyTitle.DEFAULT;
        ssg = StatesSkyGame.DEFAULT;
        ssp = StatesSkyPause.DEFAULT;
        sss = StatesSkySave.DEFAULT;
        ssc = StatesSkyClose.DEFAULT;
    }
    
    @Override
    public void update(float tpf) {
        if (programState == States.PAUSE) {
            view.getCamera().lookAtDirection(camLook, camUp);
        } else {
            view.getCamera().getDirection(camLook);
            view.getCamera().getUp(camUp);
        }
    }
    
    @Override
    protected void initialize(Application app) {
    }
    
    @Override
    protected void onEnable() {
    }
    
    public void topLevelStateChanger(States p, States s) {
        if (p==States.INTRO && s==States.TITLE) {
            previousState = p;
            programState = s;
            //SkyAppState behavior
        } else if (p==States.INTRO && s==States.PAUSE) {
            
        } else if (p==States.PAUSE && s==States.INTRO) {
            
        }
    }

    public void setState(States s) {
        programState = s;
        System.out.println(programState);
        
        switch (programState) {
            case PAUSE:
                setPause();
                break;
            default:
                break;
        }
    }
    
    private void setPause() {
        ssp = StatesSkyPause.PAUSE;
    }
    
    
    @Override
    protected void onDisable() {
    }
    
    @Override
    protected void cleanup(Application app) {
    }
}