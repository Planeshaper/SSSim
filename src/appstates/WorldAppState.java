/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import game.FontManager;
import game.Main;
import game.WorldObjectFactory;
import implementations.ui.game.BuyablesFactory;
import implementations.world.game.ImpWorldGame;
import implementations.world.game.WorldGameObject;
import implementations.world.game.buttons.WorldGameObjectButtons;
import implementations.world.splash.ImpWorldIntro;
import implementations.world.title.ImpWorldTitle;
import states.States;
import states.statesworld.*;

/**
 *
 * @author PlaneShaper
 */
public class WorldAppState extends BaseAppState {
    
    private Main app;
    GlobalAppState parentAppState;
    
    ViewPort view;
    
    Node worldNode;
    Node clickables;
    
    States previousState;
    States programState;
    
    StatesWorldIntro swi;
    StatesWorldTitle swt;
    StatesWorldGame swg;
    StatesWorldPause swp;
    StatesWorldSave sws;
    StatesWorldClose swc;
    
    FontManager fm;
    WorldObjectFactory wof;
    
    ImpWorldIntro iws;
    ImpWorldTitle iwt;
    ImpWorldGame iwg;
    
    public WorldAppState(ViewPort view, Node uiNode, States previousState, States state, GlobalAppState parentAppState) {
        this.view = view;
        
        this.worldNode = uiNode;
        this.clickables = new Node("clickables");
        this.worldNode.attachChild(this.clickables);
        
        this.previousState = previousState;
        this.programState = state;
        
        swi = StatesWorldIntro.DEFAULT;
        swt = StatesWorldTitle.DEFAULT;
        swg = StatesWorldGame.DEFAULT;
        swp = StatesWorldPause.DEFAULT;
        sws = StatesWorldSave.DEFAULT;
        swc = StatesWorldClose.DEFAULT;
        
        this.parentAppState = parentAppState;
    }
    
    @Override
    public void update(float tpf) {
    }
    
    @Override
    protected void initialize(Application app) {
        this.app = (Main) app;
        fm = new FontManager(this.app.getAssetManager());
        fm.setLocalTranslation(0, -10f, 0);
        worldNode.attachChild(fm);
        
        wof = new WorldObjectFactory(this.app.getAssetManager());
    }
    
    @Override
    protected void onEnable() {
        iws = new ImpWorldIntro();
        iws.init(this.app.getAssetManager());
        worldNode.attachChild(iws);
        
        iwt = new ImpWorldTitle();
        iwt.init(this.app.getAssetManager());
        
        iwg = new ImpWorldGame(fm, clickables, this, wof);
        iwg.init();
        
        setupLights();
    }
    
    public GlobalAppState getParentAppState() {
        return parentAppState;
    }
    
    private void setupLights() {
        AmbientLight al = new AmbientLight();
        al.setColor(new ColorRGBA(ColorRGBA.White));
        al.setName("LightAmbientUI");
        al.setEnabled(true);
        
        worldNode.addLight(al);
        
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(new ColorRGBA(ColorRGBA.White));
        dl.setName("LightDirectUI");
        dl.setDirection(new Vector3f(0,1,1));
        dl.setEnabled(true);
        
        worldNode.addLight(dl);
    }
    
    public void reinitializeGame() {
        iwg.reinitializeGame();
        /*
        secondLevelStateChanger(swg, StatesWorldGame.SHOP);
        iwg.reinitializeGame();
        iwg.updateGameStatValue("hydrogenValueString", parentAppState.getH());
        */
    }
    
    public void topLevelStateChanger(States p, States s) {
        if (p==States.INTRO && s==States.TITLE) {
            previousState = p;
            programState = s;
            //UIAppState behavior
            //Update the substates of swi and swt
            //Disable the splash implementation
            iws.removeFromParent();
            secondLevelStateChanger(swt, StatesWorldTitle.TITLE);
            worldNode.attachChild(iwt);
            //Enable the title menu implementation
        } else if (p==States.INTRO && s==States.PAUSE) {
            
        } else if (p==States.PAUSE && s==States.INTRO) {
            
        } else if (p==States.TITLE && s==States.GAME) {
            previousState = p;
            programState = s;
            secondLevelStateChanger(swt, StatesWorldTitle.TITLE);
            //iwt.detachCurrentNodeUI();
            iwt.removeFromParent();
            secondLevelStateChanger(swg, StatesWorldGame.SHOP);
            worldNode.attachChild(iwg);
        }
    }
    
    public void secondLevelStateChanger(StatesWorldSecondLevel p, StatesWorldSecondLevel s) {
        if (p==s) {
            //States already match
        } else if (p.getClass().equals(s.getClass())) {
            if (s instanceof StatesWorldIntro && programState==States.INTRO) {
                
            } else if (s instanceof StatesWorldGame && programState==States.GAME) {
                StatesWorldGame p2 = (StatesWorldGame) p;
                StatesWorldGame s2 = (StatesWorldGame) s;
                switch (s2) {
                    case ACTIVE:
                        swg = StatesWorldGame.ACTIVE;
                        break;
                    case SHOP:
                        swg = StatesWorldGame.SHOP;
                        break;
                    case TURN:
                        swg = StatesWorldGame.TURN;
                        break;
                    case EXTRA:
                        swg = StatesWorldGame.EXTRA;
                        break;
                }
            } else {
                //State desync
            }
        } else {
            //unequal second level states
        }
    }
    
    public StatesWorldGame getSWG() {
        return swg;
    }
    
    public void requestTopLevelStateChange(States r, States s) {
        System.out.println("Top Level State Change requested");
        if (programState.compareTo(r)==0) {
                parentAppState.requestTopLevelStateChange(s);
        } else {
            //requester is mismatched from current state
        }
    }
    
    public void requestSecondLevelStateChange(States r, StatesWorldSecondLevel s) {
        if (programState.compareTo(r)==0) {
            switch (r) {
                case INTRO:
                    break;
                case TITLE:
                    secondLevelStateChanger(swt, s);
                    break;
                case GAME:
                    secondLevelStateChanger(swg, s);
                    break;
            }
        } else {
            //requester is mismatched from current state
        }
    }
    
    public void setState(States s) {
        programState = s;
        
        switch (programState) {
            case PAUSE:
                setPause();
                break;
            default:
                break;
        }
    }
    
    public void checkClickableCollision() {
        System.out.println("WorldAppState is checking for mouse picking collisions");
        System.out.println("WorldAppState has the following States: " + programState.toString() + ", " + swg.toString());
        if (programState.compareTo(States.GAME)==0 && swg.compareTo(StatesWorldGame.ACTIVE)==0) {
            System.out.println("WorldAppState is in the correct State");
            CollisionResults results = new CollisionResults();
            Vector2f click2d = this.app.getInputManager().getCursorPosition();
            Vector3f click3d = this.view.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = this.view.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();

            Ray ray = new Ray(click3d, dir);
            clickables.collideWith(ray, results);
            
            if (results.size()>0) {
                System.out.println(results.getClosestCollision().getGeometry().getParent().getUserData("ImpWorldGame").getClass().toString());
                ((ImpWorldGame) results.getClosestCollision().getGeometry().getParent().getUserData("ImpWorldGame")).
                        buttonPressed(results.getClosestCollision().getGeometry());
            }
        }
    }
    
    private void setPause() {
        swp = StatesWorldPause.PAUSE;
    }
    
    public void createWorldGameObject(WorldGameObjectButtons wgob) {
        iwg.createWorldGameObject(wgob);
    }
    
    public void generateRandomEnemyRequest(BuyablesFactory bf) {
        iwg.generateRandomEnemy(bf);
    }
    
    public void generateEnemyByTurnRequest(BuyablesFactory bf, int turn) {
        iwg.generateEnemyByTurn(bf, turn);
    }
    
    public void removeEnemyRequest(int index, int index2) {
        iwg.removeEnemy(index, index2);
    }
    
    @Override
    protected void onDisable() {
    }
    
    @Override
    protected void cleanup(Application app) {
    }
}