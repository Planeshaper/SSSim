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
import implementations.ui.game.BuyablesFactory;
import implementations.ui.game.ImpUIGame;
import implementations.ui.game.buttons.ImpUIGameButtons;
import implementations.ui.intro.ImpUIIntro;
import implementations.ui.title.ImpUITitlePlay;
import implementations.ui.title.ImpUITitleSettings;
import implementations.ui.title.ImpUITitle;
import implementations.ui.title.buttons.ImpUITitleSplashButtons;
import java.util.Random;
import states.States;
import states.statesui.*;
import states.statesworld.StatesWorldGame;

/**
 *
 * @author PlaneShaper
 */
public class UIAppState extends BaseAppState {
    
    private Main app;
    GlobalAppState parentAppState;
    
    ViewPort view;
    
    Node uiNode;
    Node clickables;
    
    States previousState;
    States programState;
    
    StatesUIIntro sui;
    StatesUITitle sut;
    StatesUIGame sug;
    StatesUIPause sup;
    StatesUISave sus;
    StatesUIClose suc;
    
    Random r;
    FontManager fm;
    BuyablesFactory bf;
    
    ImpUIIntro iuis;
    ImpUITitle iuit;
    ImpUITitlePlay iuip;
    ImpUITitleSettings iuio;
    
    ImpUIGame iuig;
    
    public UIAppState(ViewPort view, Node uiNode, States previousState, States state, GlobalAppState parentAppState) {
        this.view = view;
        
        this.uiNode = uiNode;
        this.clickables = new Node("clickables");
        this.uiNode.attachChild(this.clickables);
        
        this.previousState = previousState;
        this.programState = state;
        
        sui = StatesUIIntro.DEFAULT;
        sut = StatesUITitle.DEFAULT;
        sug = StatesUIGame.DEFAULT;
        sup = StatesUIPause.DEFAULT;
        sus = StatesUISave.DEFAULT;
        suc = StatesUIClose.DEFAULT;
        
        this.parentAppState = parentAppState;
    }

    @Override
    public void update(float tpf) {
        if (programState==States.GAME && sug==StatesUIGame.ACTIVE) {
            iuig.incrementActiveTimer(-1f);
            if (iuig.getActiveTimer()<0) {
                secondLevelStateChanger(sug, StatesUIGame.SHOP);
                iuig.setActiveTimer(iuig.getDefaultActiveTimer());
            }
            parentAppState.getGameRules().calculateTick();
            iuig.updateGameStatValue("healthValueString", parentAppState.getHealth());
            iuig.updateGameStatValue("hydrogenValueString", parentAppState.getH());
            iuig.updateGameStatValue("heliumValueString", parentAppState.getHe());
            iuig.updateGameStatValue("organicValueString", parentAppState.getO());
            iuig.updateGameStatValue("waterValueString", parentAppState.getW());
            iuig.updateGameStatValue("metalValueString", parentAppState.getM());
            iuig.updateGameStatValue("ionValueString", parentAppState.getI());
            iuig.updateGameStatValue("radioValueString", parentAppState.getR());
        }
        if (programState==States.GAME && sug==StatesUIGame.SHOP) {
            iuig.updateGameStatValue("healthValueString", parentAppState.getHealth());
            iuig.updateGameStatValue("hydrogenValueString", parentAppState.getH());
            iuig.updateGameStatValue("heliumValueString", parentAppState.getHe());
            iuig.updateGameStatValue("organicValueString", parentAppState.getO());
            iuig.updateGameStatValue("waterValueString", parentAppState.getW());
            iuig.updateGameStatValue("metalValueString", parentAppState.getM());
            iuig.updateGameStatValue("ionValueString", parentAppState.getI());
            iuig.updateGameStatValue("radioValueString", parentAppState.getR());
            if (parentAppState.getGameRules().getTurn()>12) {
                parentAppState.requestYouWin();
            }
        }
        if (programState==States.GAME) {
            iuig.updateGameStatValue("maxOrbitsValueString", parentAppState.getGameRules().getNumOrbitsAllowed());
            iuig.updateGameStatValue("occOrbitsValueString", parentAppState.getGameRules().getNumOrbits());
            iuig.updateGameStatValue("maxChannelsValueString", parentAppState.getGameRules().getNumChannelsAllowed());
            iuig.updateGameStatValue("maxAssaultsValueString", parentAppState.getGameRules().getNumAssaultsAllowed());
        }
    }
    
    @Override
    protected void initialize(Application app) {
        this.app = (Main) app;
        fm = new FontManager(this.app.getAssetManager());
        fm.setLocalTranslation(0, -10f, 0);
        r = new Random();
        bf = new BuyablesFactory(this.app.getAssetManager(), fm, r);
        uiNode.attachChild(fm);
    }
    
    @Override
    protected void onEnable() {
        iuis = new ImpUIIntro(fm);
        iuis.init();
        uiNode.attachChild(iuis);
        
        iuit = new ImpUITitle(fm, clickables, this);
        iuit.init();
        
        iuig = new ImpUIGame(fm, bf, clickables, this);
        iuig.init();
        
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
        
        uiNode.addLight(al);
        
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(new ColorRGBA(ColorRGBA.White));
        dl.setName("LightDirectUI");
        dl.setDirection(new Vector3f(0,1,1));
        dl.setEnabled(true);
        
        uiNode.addLight(dl);
    }
    
    public void reinitializeGame() {
        secondLevelStateChanger(StatesUIGame.DEFAULT, StatesUIGame.SHOP);
        iuig.reinitializeGame();
        iuig.updateGameStatValue("healthValueString", parentAppState.getHealth());
        iuig.updateGameStatValue("hydrogenValueString", parentAppState.getH());
        iuig.updateGameStatValue("heliumValueString", parentAppState.getHe());
        iuig.updateGameStatValue("organicValueString", parentAppState.getO());
        iuig.updateGameStatValue("waterValueString", parentAppState.getW());
        iuig.updateGameStatValue("metalValueString", parentAppState.getM());
        iuig.updateGameStatValue("ionValueString", parentAppState.getI());
        iuig.updateGameStatValue("radioValueString", parentAppState.getR());
    }
    
    public void topLevelStateChanger(States p, States s) {
        if (p==States.INTRO && s==States.TITLE) {
            previousState = p;
            programState = s;
            //UIAppState behavior
            //Update the substates of sui and sut
            //Disable the splash implementation
            iuis.removeFromParent();
            secondLevelStateChanger(sut, StatesUITitle.TITLE);
            uiNode.attachChild(iuit);
            //Enable the title menu implementation
        } else if (p==States.INTRO && s==States.PAUSE) {
            
        } else if (p==States.PAUSE && s==States.INTRO) {
            
        } else if (p==States.TITLE && s==States.GAME) {
            previousState = p;
            programState = s;
            secondLevelStateChanger(sut, StatesUITitle.TITLE);
            iuit.detachCurrentNodeUI();
            iuit.removeFromParent();
            secondLevelStateChanger(sug, StatesUIGame.SHOP);
            uiNode.attachChild(iuig);
        }
    }

    public void secondLevelStateChanger(StatesUISecondLevel p, StatesUISecondLevel s) {
        if (p==s) {
            //States already match
        } else if (p.getClass().equals(s.getClass())) {
            if (s instanceof StatesUIIntro && programState==States.INTRO) {
                
            } else if (s instanceof StatesUITitle && programState==States.TITLE) {
                StatesUITitle p2 = (StatesUITitle) p;
                StatesUITitle s2 = (StatesUITitle) s;
                switch (s2) {
                    case TITLE:
                        iuit.detachCurrentNodeUI();
                        iuit.attachNodeUI(s2);
                        sut = StatesUITitle.TITLE;
                        break;
                    case SETTINGS:
                        iuit.detachCurrentNodeUI();
                        iuit.attachNodeUI(s2);
                        sut = StatesUITitle.SETTINGS;
                        break;
                    case PLAY:
                        //detach current ImpUITitle Node
                        //attach NodeUITitlePlay to ImpUITitle
                        iuit.detachCurrentNodeUI();
                        iuit.attachNodeUI(s2);
                        sut = StatesUITitle.PLAY;
                        break;
                }
            } else if (s instanceof StatesUIGame && programState==States.GAME) {
                StatesUIGame p2 = (StatesUIGame) p;
                StatesUIGame s2 = (StatesUIGame) s;
                switch (s2) {
                    case ACTIVE:
                        iuig.detachCurrentNodeUI();
                        iuig.attachNodeUI(s2);
                        sug = StatesUIGame.ACTIVE;
                        parentAppState.getWAS().secondLevelStateChanger(parentAppState.getWAS().getSWG(), StatesWorldGame.ACTIVE);
                        parentAppState.getWAS().generateEnemyByTurnRequest(bf, parentAppState.getGameRules().getTurn());
                        break;
                    case SHOP:
                        iuig.detachCurrentNodeUI();
                        iuig.refreshShop();
                        iuig.attachNodeUI(s2);
                        parentAppState.getGameRules().incrTurn();
                        sug = StatesUIGame.SHOP;
                        parentAppState.getWAS().secondLevelStateChanger(parentAppState.getWAS().getSWG(), StatesWorldGame.SHOP);
                        break;
                    case TURN:
                        iuig.detachCurrentNodeUI();
                        iuig.attachNodeUI(s2);
                        sug = StatesUIGame.TURN;
                        parentAppState.getWAS().secondLevelStateChanger(parentAppState.getWAS().getSWG(), StatesWorldGame.TURN);
                        break;
                    case EXTRA:
                        iuig.detachCurrentNodeUI();
                        iuig.attachNodeUI(s2);
                        sug = StatesUIGame.EXTRA;
                        parentAppState.getWAS().secondLevelStateChanger(parentAppState.getWAS().getSWG(), StatesWorldGame.EXTRA);
                }
            } else {
                //State desync
            }
        } else {
            //unequal second level states
        }
    }
    
    public void requestYouWin() {
        requestSecondLevelStateChange(States.GAME, StatesUIGame.EXTRA);
        iuig.youWin();
    }
    public void requestGameOver() {
        requestSecondLevelStateChange(States.GAME, StatesUIGame.EXTRA);
        iuig.gameOver();
    }
    
    public void requestTopLevelStateChange(States r, States s) {
        System.out.println("Top Level State Change requested");
        if (programState.compareTo(r)==0) {
                parentAppState.requestTopLevelStateChange(s);
        } else {
            //requester is mismatched from current state
        }
    }

    public void requestSecondLevelStateChange(States r, StatesUISecondLevel s) {
        if (programState.compareTo(r)==0) {
            switch (r) {
                case INTRO:
                    break;
                case TITLE:
                    secondLevelStateChanger(sut, s);
                    break;
                case GAME:
                    secondLevelStateChanger(sug, s);
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
    
    public boolean checkClickableCollision() {
        CollisionResults results = new CollisionResults();
        Vector2f click2d = this.app.getInputManager().getCursorPosition();
        Vector3f click3d = this.view.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = this.view.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        
        Ray ray = new Ray(click3d, dir);
        clickables.collideWith(ray, results);
        
        if (results.size()>0) {
            System.out.println(results.getClosestCollision().getGeometry().getParent().getUserData("UIInterface").getClass().toString());
            switch (programState) {
                case TITLE:
                    ((ImpUITitle) results.getClosestCollision().getGeometry().getParent().getUserData("UIInterface")).
                            buttonPressed((ImpUITitleSplashButtons) results.getClosestCollision().getGeometry().getUserData("button"));
                    break;
                case GAME:
                    ((ImpUIGame) results.getClosestCollision().getGeometry().getParent().getUserData("UIInterface")).
                            buttonPressed(sug, (ImpUIGameButtons) results.getClosestCollision().getGeometry().getUserData("button"));
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
    
    private void setPause() {
        sup = StatesUIPause.PAUSE;
    }
    
    
    @Override
    protected void onDisable() {
    }
    
    @Override
    protected void cleanup(Application app) {
    }
}