package game;

import appstates.*;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import states.States;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    GlobalAppState gas;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        //settings.setFrameRate(60);
        settings.setVSync(true);
        settings.setFullscreen(true);
        settings.setResolution(1920, 1080);
        settings.setTitle("Star System Defense");
        settings.setSettingsDialogImage("Interface/SSDDisplaySplash.png");
        app.setSettings(settings);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        setDisplayFps(false);
        setDisplayStatView(false);
        this.flyCam.setEnabled(false);
        setupAppStates();
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
    }
    
    public void setupAppStates() {
        gas = new GlobalAppState();
        stateManager.attach(gas);
    }
    
    public GlobalAppState getGlobalAppState() {
        return gas;
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}