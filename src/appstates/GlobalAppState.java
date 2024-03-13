/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import game.DoubleChaseCam;
import game.Epoch;
import game.GameRules;
import game.Main;
import game.SkyObjectFactory;
import states.States;

/**
 *
 * @author PlaneShaper
 */
public class GlobalAppState extends BaseAppState implements ActionListener {
    
    private Main app;
    private Node rootNode;
    private RenderManager renderManager;
    private Camera cam;
    private ViewPort viewPort;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    
    States previousState = States.INTRO;
    States programState = States.INTRO;
    
    float introTimeout = 5f;
    
    Node skyNode;
    Node worldNode;
    Node uiNode;
    
    DoubleChaseCam chaseCam;
    ViewPort worldView;
    ViewPort uiView;
    
    SkyAppState sas;
    WorldAppState was;
    UIAppState uas;
    
    GameRules gr;
    
    long health = 1000l;
    
    long hydrogen = 1000l;
    long helium = 0l;
    long metal = 0l;
    long organic = 0l;
    long water = 0l;
    long ion = 0l;
    long radio = 0l;
    
    
    long salt = 0l;
    long noble = 0l;
    
    long meson = 0l;
    long pion = 0l;
    long neutrino = 0l;
    long quark = 0l;
    long anti = 0l;
    long photon = 0l;
    long graviton = 0l;
    long tachyon = 0l;
    
    Epoch epoch = Epoch.BANG;
    
    public GlobalAppState() {
        
    }
    
    @Override
    protected void initialize(Application app) {
        this.app = (Main) app;
        this.rootNode = this.app.getRootNode();
        this.renderManager = this.app.getRenderManager();
        this.cam = this.app.getCamera();
        this.viewPort = this.app.getViewPort();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        
        this.inputManager.setCursorVisible(true);
        //this.inputManager.
        
        Box b3 = new Box(1, 1, 1);
        Geometry geom3 = new Geometry("Box", b3);
        
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", new ColorRGBA(0f,0f,0f,0f));
        mat3.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
        geom3.setMaterial(mat3);
        
        SkyObjectFactory sof = new SkyObjectFactory(this.app.getAssetManager());
        
        skyNode = new Node();
        skyNode.setName("skyNode");
        worldNode = new Node();
        worldNode.setName("worldNode");
        uiNode = new Node();
        uiNode.setName("uiNode");
        rootNode.attachChild(skyNode);
        rootNode.attachChild(worldNode);
        rootNode.attachChild(uiNode);
        
        skyNode.attachChild(geom3);
        skyNode.attachChild(sof.createSkyObject());
        
        setupViews();
        
        setupChaseCamera(geom3);
        
        setupAppStates();
        
        setupLights();
        
        setupKeys();
        
        gr = new GameRules(this);
    }
    
    @Override
    protected void onEnable() {
    }
    
    @Override
    public void update(float tpf) {
        switch(programState) {
            case INTRO:
                if (doEndIntro(tpf)) {
                    endIntro();
                }
                break;
            case TITLE:
                break;
            default:
                break;
        }
    }
    
    public boolean doEndIntro(float tpf) {
        introTimeout -= tpf;
        
        return introTimeout<0;
    }
    public void endIntro() {
        topLevelStateChanger(States.INTRO, States.TITLE);
    }
    
    public void topLevelStateChanger(States p, States s) {
        previousState = p;
        programState = s;
        if (p==States.INTRO && s==States.TITLE) {
            
        } else if (p==States.INTRO && s==States.PAUSE) {
            
        } else if (p==States.PAUSE && s==States.INTRO) {
            
        }
        //UIAppState
        //SkyAppState
        //WorldAppState
        sas.topLevelStateChanger(p, s);
        was.topLevelStateChanger(p, s);
        uas.topLevelStateChanger(p, s);
    }

    private void setupChaseCamera(Spatial model) {
        chaseCam = new DoubleChaseCam(cam, model, inputManager);
        chaseCam.setUpVector(Vector3f.UNIT_Y);
        //chaseCam.setDefaultHorizontalRotation(-FastMath.HALF_PI);
        //chaseCam.setSmoothMotion(false);
        //chaseCam.setLookAtOffset(new Vector3f(0f,3.7f,0f));
        //chaseCam.setLookAtOffset(new Vector3f(0f,0.37f,0f));
        //chaseCam.setLookAtOffset(new Vector3f(0f,0.925f,0f));
        chaseCam.setLookAtOffset(new Vector3f(10f,0f,0f));
        chaseCam.setDefaultVerticalRotation(0);
        chaseCam.setMinVerticalRotation(-FastMath.PI/2);
        //inputManager.deleteMapping("ChaseCamZoomIn");
        //inputManager.deleteMapping("ChaseCamZoomOut");
        //inputManager.deleteMapping("ChaseCamMoveLeft");
        //inputManager.deleteMapping("ChaseCamMoveRight");
//        ray = new Line(character.getPhysicsLocation(),cam.getDirection().negate());
//        ray.setDynamic();
        /*
        //Orthographic camera settings -- currently very broken
        chaseCam.cam.setParallelProjection(true);
        float aspect = (float) chaseCam.cam.getWidth() / chaseCam.cam.getHeight();
        float size = 30f;
        chaseCam.cam.setFrustum(-1000f, 1000f, -aspect * size, aspect * size, size, -size);
        */
        //model.rotate(0f, FastMath.HALF_PI+(5*FastMath.ONE_THIRD*FastMath.PI), 0f);
        
        //HeapsAnimationLocation
        /*Quaternion lookDirection = new Quaternion();
        lookDirection.set(0.0f, 0.99208313f, 0.0f, -0.61270964f);
        model.setLocalRotation(lookDirection);*/
        
        
        //chaseCam.setDefaultHorizontalRotation(0);
        //chaseCam.setDefaultVerticalRotation(1.5703125f);
        chaseCam.setEnabled(true);
        chaseCam.setDragToRotate(true);
    }
    
    public void setupViews() {
        cam.setViewPort(0f, 1f, 0f, 1f);
        cam.setName("Camera");
        viewPort.clearScenes();
        viewPort.attachScene(skyNode);
        System.out.println(viewPort.getCamera().getName() + ", " + cam.getName());
        viewPort.setEnabled(true);
        
        Camera cam2 = cam.clone();
        worldView = renderManager.createMainView("World ViewPort", cam2);
        worldView.setClearColor(false);
        worldView.setClearDepth(true);
        worldView.setClearStencil(true);
        worldView.attachScene(worldNode);
        worldView.setEnabled(true);
        
        Camera cam3 = cam.clone();
        uiView = renderManager.createMainView("UI ViewPort", cam3);
        uiView.setClearColor(false);
        uiView.setClearDepth(true);
        uiView.setClearStencil(true);
        uiView.attachScene(uiNode);
        uiView.setEnabled(true);
    }
    
    private void setupAppStates() {
        sas = new SkyAppState(viewPort, skyNode, programState, previousState);
        was = new WorldAppState(worldView, worldNode, programState, previousState, this);
        uas = new UIAppState(uiView, uiNode, programState, previousState, this);
        
        stateManager.attach(sas);
        stateManager.attach(was);
        stateManager.attach(uas);
    }
    
    public void setupLights() {
    }

    public void setupKeys() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Pause");
        inputManager.addListener(this, "Pick");
    }
    
    public States getState() {
        return programState;
    }
    public States getOldState() {
        return previousState;
    }
    public void setState(States s) {
        previousState = programState;
        programState = s;
    }
    
    public void requestTopLevelStateChange(States s) {
        switch (s) {
            case CLOSE:
                System.out.println("Close requested at GlobalAppState");
                this.app.stop();
                break;
            case GAME:
                if (programState==States.GAME) {
                    initializeGame();
                    was.reinitializeGame();
                    gr.reinitializeGame();
                    uas.reinitializeGame();
                } else {
                    uas.topLevelStateChanger(programState, s);
                    was.topLevelStateChanger(programState, s);
                    sas.topLevelStateChanger(programState, s);
                    setState(s);
                }
                break;
        }
    }
    
    public void initializeGame() {
        //Set global values to zero
        setHealth(1000l);
        setH(1000l);
        setHe(0l);
        setM(0l);
        setO(0l);
        setW(0l);
        setI(0l);
        setR(0l);
    }
    public GameRules getGameRules() {
        return gr;
    }
    public void requestGameOver() {
        uas.requestGameOver();
    }
    public void requestYouWin() {
        uas.requestYouWin();
    }
    
    public WorldAppState getWAS() {
        return was;
    }
    
    public long getHealth() {
        return health;
    }
    public void incrementHealth (long v) {
        health += v;
    }
    public void setHealth(long h) {
        health = h;
    }
    public long getH() {
        return hydrogen;
    }
    public void incrementH (long v) {
        hydrogen += v;
    }
    public void setH(long h) {
        hydrogen = h;
    }
    public long getHe() {
        return helium;
    }
    public void incrementHe (long v) {
        helium += v;
    }
    public void setHe(long he) {
        helium = he;
    }
    
    public long getS() {
        return salt;
    }
    public void incrementS (long v) {
        salt += v;
    }
    public void setS(long s) {
        salt = s;
    }
    
    public long getM() {
        return metal;
    }
    public void incrementM (long v) {
        metal += v;
    }
    public void setM(long m) {
        metal = m;
    }
    
    public long getO() {
        return organic;
    }
    public void incrementO (long v) {
        organic += v;
    }
    public void setO(long o) {
        organic = o;
    }
    
    public long getW() {
        return water;
    }
    public void incrementW (long v) {
        water += v;
    }
    public void setW(long o) {
        water = o;
    }
    
    public long getI() {
        return ion;
    }
    public void incrementI (long v) {
        ion += v;
    }
    public void setI(long o) {
        ion = o;
    }
    
    public long getR() {
        return radio;
    }
    public void incrementR (long v) {
        radio += v;
    }
    public void setR(long o) {
        radio = o;
    }
    
    public long getN() {
        return noble;
    }
    public void incrementN (long v) {
        noble += v;
    }
    public void setN(long n) {
        noble = n;
    }
    
    public long getM_() {
        return meson;
    }
    public void incrementM_ (long v) {
        meson += v;
    }
    public void setM_(long m) {
        meson = m;
    }
    
    public long getPi_() {
        return pion;
    }
    public void incrementPi_ (long v) {
        pion += v;
    }
    public void setPi_(long p) {
        pion = p;
    }
    
    public long getN_() {
        return neutrino;
    }
    public void incrementN_ (long v) {
        neutrino += v;
    }
    public void setN_(long n) {
        neutrino = n;
    }
    
    public long getQ_() {
        return quark;
    }
    public void incrementQ_ (long v) {
        quark += v;
    }
    public void setQ_(long q) {
        quark = q;
    }
    
    public long getA_() {
        return anti;
    }
    public void incrementA_ (long v) {
        anti += v;
    }
    public void setA_(long a) {
        anti = a;
    }
    
    public long getP_() {
        return photon;
    }
    public void incrementP_ (long v) {
        photon += v;
    }
    public void setP_(long p) {
        photon = p;
    }
    
    public long getG_() {
        return graviton;
    }
    public void incrementG_ (long v) {
        graviton += v;
    }
    public void setG_(long g) {
        graviton = g;
    }
    
    public long getT_() {
        return tachyon;
    }
    public void incrementT_ (long v) {
        tachyon += v;
    }
    public void setT_(long t) {
        tachyon = t;
    }
    
    public Epoch getEpoch() {
        return epoch;
    }
    public void incrementEpoch (int v) {
        ;
    }
    public void setEpoch(Epoch e) {
        epoch = e;
    }
    
    @Override
    protected void onDisable() {
    }
    
    @Override
    protected void cleanup(Application app) {
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Pause") && !isPressed) {
            programState = (programState.equals(States.PAUSE) ? previousState : States.PAUSE);
            
            sas.setState(programState);
            was.setState(programState);
            uas.setState(programState);
        } else if (binding.equals("Pick") && !isPressed) {
            if (!uas.checkClickableCollision()) {
                was.checkClickableCollision();
            }
        }
    }
}