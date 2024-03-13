/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.game;

import implementations.ui.title.*;
import appstates.UIAppState;
import implementations.ui.intro.*;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.FontManager;
import implementations.ui.game.buttons.ImpUIGameActiveButtons;
import implementations.ui.game.buttons.ImpUIGameButtons;
import implementations.ui.game.buttons.ImpUIGameExtraButtons;
import implementations.ui.game.buttons.ImpUIGameShopButtons;
import implementations.ui.game.buttons.ImpUIGameTurnButtons;
import implementations.ui.title.buttons.ImpUITitleSplashButtons;
import java.util.LinkedList;
import spatials.ui.game.NodeUIGameActive;
import spatials.ui.game.NodeUIGameExtra;
import spatials.ui.game.NodeUIGameShop;
import spatials.ui.game.NodeUIGameTurn;
import spatials.ui.title.NodeUITitlePlay;
import spatials.ui.title.NodeUITitleScreen;
import spatials.ui.title.NodeUITitleSettings;
import states.States;
import states.statesui.StatesUIGame;
import states.statesui.StatesUITitle;

/**
 *
 * @author PlaneShaper
 */
public class ImpUIGame extends Node {
    
    final float DEFAULT_ACTIVE_TIMER = 450f;
    
    Node currentNode;
    Node clickables;
    Node currentClickables;
    Node gameStats;
    
    NodeUIGameActive nuiga = new NodeUIGameActive();
    Node cuiga = new Node();
    NodeUIGameTurn nuigt = new NodeUIGameTurn();
    Node cuigt = new Node();
    NodeUIGameShop nuigs = new NodeUIGameShop();
    Node cuigs = new Node();
    NodeUIGameExtra nuige = new NodeUIGameExtra();
    Node cuige = new Node();
    
    Spatial youWinSpatial;
    Spatial youWinTextBox;
    Spatial gameOverSpatial;
    Spatial gameOverTextBox;
    
    FontManager fm;
    BuyablesFactory bf;
    
    UIAppState parentAppState;
    
    float activeTimer = DEFAULT_ACTIVE_TIMER;
    
    //LinkedLists of buyableTexts, featTexts, and eventTexts
    LinkedList<Spatial> buyableTexts = new LinkedList<>();
    LinkedList<Spatial> featTexts = new LinkedList<>();
    LinkedList<Spatial> eventTexts = new LinkedList<>();
    //LinkedLists of buyableButtons, featButtons, and eventButtons
    LinkedList<Spatial> buyableButtons = new LinkedList<>();
    LinkedList<Spatial> featButtons = new LinkedList<>();
    LinkedList<Spatial> eventButtons = new LinkedList<>();
    
    public ImpUIGame(FontManager fm, BuyablesFactory bf, Node clickables, UIAppState parentAppState) {
        this.name = "ImpUIGame";
        this.clickables = clickables;
        this.currentNode = new Node();
        this.currentClickables = new Node();
        this.attachChild(currentNode);
        this.fm = fm;
        this.parentAppState = parentAppState;
        this.gameStats = new Node();
        this.bf = bf;
    }
    
    public void init() {
        //initialize graphic elements to show values (e.g. hydrogen, score, etc.)
        initStats();
        initNUIGS();
        initNUIGT();
        initNUIGA();
        initNUIGE();
    }
    
    public void detachCurrentNodeUI() {
        currentNode.removeFromParent();
        currentClickables.removeFromParent();
    }
    
    public void attachNodeUI(StatesUIGame s) {
        switch (s) {
            case SHOP:
                this.attachChild(nuigs);
                currentNode = nuigs;
                this.clickables.attachChild(cuigs);
                currentClickables = cuigs;
                break;
            case TURN:
                this.attachChild(nuigt);
                currentNode = nuigt;
                this.clickables.attachChild(cuigt);
                currentClickables = cuigt;
                break;
            case ACTIVE:
                this.attachChild(nuiga);
                currentNode = nuiga;
                this.clickables.attachChild(cuiga);
                currentClickables = cuiga;
                break;
            case EXTRA:
                this.attachChild(nuige);
                currentNode = nuige;
                this.clickables.attachChild(cuige);
                currentClickables = cuige;
                break;
        }
    }
    
    public void initStats() {
        String statLabelString = "M:                                       ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "metalLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 0.0f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        initHealthStat(statLabel);
        initHydrogenStat(statLabel);
        initHeliumStat(statLabel);
        initOrganicStat(statLabel);
        initWaterStat(statLabel);
        initMetalStat(statLabel);
        initIonStat(statLabel);
        initRadioStat(statLabel);
        
        initMaxOrbits(statLabel);
        initOccOrbits(statLabel);
        initMaxChannels(statLabel);
        initMaxAssaults(statLabel);
    }
    
    public void updateGameStatValue(String s, long l) {
        String parsedVal = String.valueOf(l);
        
        Node newReference = fm.generateToonText(parsedVal, s, new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        newReference.scale(0.3f);
        newReference.rotate(FastMath.PI/2, 0, 0);
        
        newReference.setLocalTranslation(gameStats.getChild(s).getLocalTranslation().getX()+
                (((BoundingBox) gameStats.getChild(s).getWorldBound()).getXExtent()*2)-
                (((BoundingBox) newReference.getWorldBound()).getXExtent()*2), 
                gameStats.getChild(s).getLocalTranslation().getY(), 
                0);
        
        gameStats.detachChildNamed(s);
        gameStats.attachChild(newReference);
    }
    
    public float getDefaultActiveTimer() {
        return DEFAULT_ACTIVE_TIMER;
    }
    
    //There is a 30 second timer while the game is in the ACTIVE state, when it ends, the game moves into the SHOP state
    public void initNUIGA() {
        cuiga.setUserData("UIInterface", this);
        
        //3 Buttons while Active
        //PAUSE (This is also the timer), RESTART, QUIT
        
        String playString = "New Game";
        String quitString = "Quit Game";
        
        Node nodeNewGame = fm.generateToonText(playString, "playString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node nodeQuitGame = fm.generateToonText(quitString, "quitString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        nodeNewGame.scale(0.3f);
        nodeQuitGame.scale(0.3f);
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        nodeNewGame.setLocalTranslation(-(((BoundingBox) nodeNewGame.getWorldBound()).getXExtent())+6.0f, -3.0f, 0f);
        nodeQuitGame.setLocalTranslation(-(((BoundingBox) nodeQuitGame.getWorldBound()).getXExtent())+6.0f, -3.5f, 0f);
        
        nodeNewGame.rotate(FastMath.PI/2, 0, 0);
        nodeQuitGame.rotate(FastMath.PI/2, 0, 0);
        
        nuiga.attachChild(nodeNewGame);
        nuiga.attachChild(nodeQuitGame);
        
        Spatial buttonNewGame = fm.generateTextBox(nodeNewGame);
        buttonNewGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameActiveButtons buttonStateNewGame = new ImpUIGameActiveButtons();
        buttonStateNewGame.setButton(ImpUIGameActiveButtons.ImpUIGameActiveButtonEnum.RESTART);
        buttonNewGame.setUserData("button", buttonStateNewGame);
        cuiga.attachChild(buttonNewGame);
        
        Spatial buttonQuitGame = fm.generateTextBox(nodeQuitGame);
        buttonQuitGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameActiveButtons buttonStateQuitGame = new ImpUIGameActiveButtons();
        buttonStateQuitGame.setButton(ImpUIGameActiveButtons.ImpUIGameActiveButtonEnum.QUIT);
        buttonQuitGame.setUserData("button", buttonStateQuitGame);
        cuiga.attachChild(buttonQuitGame);
        
        Vector3f buttonTimerPosition = new Vector3f(6.3f,3.5f,0f);
        Vector3f buttonTimerScale = new Vector3f(0.05f, 0.2f, 1f);
        Spatial buttonTimer = fm.generateBox(buttonTimerPosition, buttonTimerScale);
        buttonTimer.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameActiveButtons buttonStatePause = new ImpUIGameActiveButtons();
        buttonStatePause.setButton(ImpUIGameActiveButtons.ImpUIGameActiveButtonEnum.TIMER);
        buttonTimer.setUserData("button", buttonStatePause);
        cuiga.attachChild(buttonTimer);
    }
    
    public void initNUIGS() {
        cuigs.setUserData("UIInterface", this);
        
        //14 Buttons in the Shop
        //BUY1, BUY2, BUY3, BUY4, BUY5, FEAT1, FEAT2, FEAT3, EVENT1, EVENT2, EVENT3, ENDSHOP, RESTART, QUIT
        
        String leaveShopString = "Leave Shop";
        
        Node leaveShopSpatial = fm.generateToonText(leaveShopString, "leaveShopString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        leaveShopSpatial.scale(0.75f);
        leaveShopSpatial.setLocalTranslation(-(((BoundingBox) leaveShopSpatial.getWorldBound()).getXExtent()), -2.5f, 0f);
        
        leaveShopSpatial.rotate(FastMath.PI/2, 0, 0);
        
        nuigs.attachChild(leaveShopSpatial);
        
        Spatial leaveShopButton = fm.generateTextBox(leaveShopSpatial);
        leaveShopButton.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameShopButtons leaveShopButtonEnum = new ImpUIGameShopButtons();
        leaveShopButtonEnum.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.ENDSHOP);
        leaveShopButton.setUserData("button", leaveShopButtonEnum);
        cuigs.attachChild(leaveShopButton);
        
        
        String playString = "New Game";
        String quitString = "Quit Game";
        
        Node nodeNewGame = fm.generateToonText(playString, "playString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node nodeQuitGame = fm.generateToonText(quitString, "quitString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        nodeNewGame.scale(0.3f);
        nodeQuitGame.scale(0.3f);
        
        nodeNewGame.setLocalTranslation(-(((BoundingBox) nodeNewGame.getWorldBound()).getXExtent())+6.0f, -3.0f, 0f);
        nodeQuitGame.setLocalTranslation(-(((BoundingBox) nodeQuitGame.getWorldBound()).getXExtent())+6.0f, -3.5f, 0f);
        
        nodeNewGame.rotate(FastMath.PI/2, 0, 0);
        nodeQuitGame.rotate(FastMath.PI/2, 0, 0);
        
        nuigs.attachChild(nodeNewGame);
        nuigs.attachChild(nodeQuitGame);
        
        Spatial buttonNewGame = fm.generateTextBox(nodeNewGame);
        buttonNewGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameShopButtons buttonStateNewGame = new ImpUIGameShopButtons();
        buttonStateNewGame.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.RESTART);
        buttonNewGame.setUserData("button", buttonStateNewGame);
        cuigs.attachChild(buttonNewGame);
        
        Spatial buttonQuitGame = fm.generateTextBox(nodeQuitGame);
        buttonQuitGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameShopButtons buttonStateQuitGame = new ImpUIGameShopButtons();
        buttonStateQuitGame.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.QUIT);
        buttonQuitGame.setUserData("button", buttonStateQuitGame);
        cuigs.attachChild(buttonQuitGame);
        
        this.createShopBuyables();
    }
    
    public void initNUIGT() {
        cuigt.setUserData("UIInterface", this);
        
        //29 Buttons in the Turn
        /*
        DRAWORBIT, ZONEGOLDILOCKS, ZONEASTEROIDS, ZONEGAS, ZONEROCKY, COMET, FLARE, WIND, HELIOPULSE, COSMICRAY, GAMMARAYBURST, 
        ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, OPHIUCHUS, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES, CETUS, 
        URANOMETRIA, ENDTURN, RESTART, QUIT;
        */
        
        String leaveTurnString = "Leave Planner";
        
        Node leaveTurnSpatial = fm.generateToonText(leaveTurnString, "leaveTurnString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        leaveTurnSpatial.scale(0.75f);
        leaveTurnSpatial.setLocalTranslation(-(((BoundingBox) leaveTurnSpatial.getWorldBound()).getXExtent()), -2.5f, 0f);
        
        leaveTurnSpatial.rotate(FastMath.PI/2, 0, 0);
        
        nuigt.attachChild(leaveTurnSpatial);
        
        Spatial leaveTurnButton = fm.generateTextBox(leaveTurnSpatial);
        leaveTurnButton.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameTurnButtons leaveTurnButtonEnum = new ImpUIGameTurnButtons();
        leaveTurnButtonEnum.setButton(ImpUIGameTurnButtons.ImpUIGameTurnButtonEnum.ENDTURN);
        leaveTurnButton.setUserData("button", leaveTurnButtonEnum);
        cuigt.attachChild(leaveTurnButton);
        
        String playString = "New Game";
        String quitString = "Quit Game";
        
        Node nodeNewGame = fm.generateToonText(playString, "playString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node nodeQuitGame = fm.generateToonText(quitString, "quitString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        nodeNewGame.scale(0.3f);
        nodeQuitGame.scale(0.3f);
        
        nodeNewGame.setLocalTranslation(-(((BoundingBox) nodeNewGame.getWorldBound()).getXExtent())+6.0f, -3.0f, 0f);
        nodeQuitGame.setLocalTranslation(-(((BoundingBox) nodeQuitGame.getWorldBound()).getXExtent())+6.0f, -3.5f, 0f);
        
        nodeNewGame.rotate(FastMath.PI/2, 0, 0);
        nodeQuitGame.rotate(FastMath.PI/2, 0, 0);
        
        nuigt.attachChild(nodeNewGame);
        nuigt.attachChild(nodeQuitGame);
        
        Spatial buttonNewGame = fm.generateTextBox(nodeNewGame);
        buttonNewGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameTurnButtons buttonStateNewGame = new ImpUIGameTurnButtons();
        buttonStateNewGame.setButton(ImpUIGameTurnButtons.ImpUIGameTurnButtonEnum.RESTART);
        buttonNewGame.setUserData("button", buttonStateNewGame);
        cuigt.attachChild(buttonNewGame);
        
        Spatial buttonQuitGame = fm.generateTextBox(nodeQuitGame);
        buttonQuitGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameTurnButtons buttonStateQuitGame = new ImpUIGameTurnButtons();
        buttonStateQuitGame.setButton(ImpUIGameTurnButtons.ImpUIGameTurnButtonEnum.QUIT);
        buttonQuitGame.setUserData("button", buttonStateQuitGame);
        cuigt.attachChild(buttonQuitGame);
    }
    
    public void initNUIGE() {
        cuige.setUserData("UIInterface", this);
        
        //29 Buttons in the Turn
        /*
        DRAWORBIT, ZONEGOLDILOCKS, ZONEASTEROIDS, ZONEGAS, ZONEROCKY, COMET, FLARE, WIND, HELIOPULSE, COSMICRAY, GAMMARAYBURST, 
        ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, OPHIUCHUS, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES, CETUS, 
        URANOMETRIA, ENDTURN, RESTART, QUIT;
        */
        
        String youWinString = "Congratulations - You WIN!!";
        
        youWinSpatial = fm.generateToonText(youWinString, "youWinString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        youWinSpatial.scale(0.75f);
        youWinSpatial.setLocalTranslation(-(((BoundingBox) youWinSpatial.getWorldBound()).getXExtent()), -2.5f, 0f);
        
        youWinSpatial.rotate(FastMath.PI/2, 0, 0);
        
        youWinTextBox = fm.generateTextBox(youWinSpatial);
        youWinTextBox.rotate(FastMath.PI/2, 0, 0);
        
        String gameOverString = "Game Over - Maybe Next Time :)";
        
        gameOverSpatial = fm.generateToonText(gameOverString, "gameOverString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        gameOverSpatial.scale(0.75f);
        gameOverSpatial.setLocalTranslation(-(((BoundingBox) gameOverSpatial.getWorldBound()).getXExtent()), -2.5f, 0f);
        
        gameOverSpatial.rotate(FastMath.PI/2, 0, 0);
        
        gameOverTextBox = fm.generateTextBox(gameOverSpatial);
        gameOverTextBox.rotate(FastMath.PI/2, 0, 0);
        
        String playString = "New Game";
        String quitString = "Quit Game";
        
        Node nodeNewGame = fm.generateToonText(playString, "playString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node nodeQuitGame = fm.generateToonText(quitString, "quitString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
        nodeNewGame.scale(0.3f);
        nodeQuitGame.scale(0.3f);
        
        nodeNewGame.setLocalTranslation(-(((BoundingBox) nodeNewGame.getWorldBound()).getXExtent())+6.0f, -3.0f, 0f);
        nodeQuitGame.setLocalTranslation(-(((BoundingBox) nodeQuitGame.getWorldBound()).getXExtent())+6.0f, -3.5f, 0f);
        
        nodeNewGame.rotate(FastMath.PI/2, 0, 0);
        nodeQuitGame.rotate(FastMath.PI/2, 0, 0);
        
        nuige.attachChild(nodeNewGame);
        nuige.attachChild(nodeQuitGame);
        
        Spatial buttonNewGame = fm.generateTextBox(nodeNewGame);
        buttonNewGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameExtraButtons buttonStateNewGame = new ImpUIGameExtraButtons();
        buttonStateNewGame.setButton(ImpUIGameExtraButtons.ImpUIGameExtraButtonEnum.RESTART);
        buttonNewGame.setUserData("button", buttonStateNewGame);
        cuige.attachChild(buttonNewGame);
        
        Spatial buttonQuitGame = fm.generateTextBox(nodeQuitGame);
        buttonQuitGame.rotate(FastMath.PI/2, 0, 0);
        ImpUIGameExtraButtons buttonStateQuitGame = new ImpUIGameExtraButtons();
        buttonStateQuitGame.setButton(ImpUIGameExtraButtons.ImpUIGameExtraButtonEnum.QUIT);
        buttonQuitGame.setUserData("button", buttonStateQuitGame);
        cuige.attachChild(buttonQuitGame);
    }
    public void youWin() {
        nuige.attachChild(youWinSpatial);
        nuige.attachChild(youWinTextBox);
    }
    public void gameOver() {
        nuige.attachChild(gameOverSpatial);
        nuige.attachChild(gameOverTextBox);
    }
    
    public void buttonPressed(StatesUIGame s, ImpUIGameButtons b) {
        System.out.println("ImpUIGame has been informed a button was pressed");
        switch (s) {
            case SHOP:
                switch (((ImpUIGameShopButtons) b).getButton()) {
                    case BUY1:
                        buy1Pressed();
                        break;
                    case BUY2:
                        buy2Pressed();
                        break;
                    case BUY3:
                        buy3Pressed();
                        break;
                    case BUY4:
                        buy4Pressed();
                        break;
                    case BUY5:
                        buy5Pressed();
                        break;
                    case ENDSHOP:
                        endshopPressed();
                        break;
                    case RESTART:
                        restartPressed();
                        break;
                    case QUIT:
                        quitPressed();
                        break;
                    default:
                        break;
                }
                break;
            case TURN:
                switch (((ImpUIGameTurnButtons) b).getButton()) {
                    case ENDTURN:
                        endturnPressed();
                        break;
                    case RESTART:
                        restartPressed();
                        break;
                    case QUIT:
                        quitPressed();
                        break;
                    default:
                        break;
                }
                break;
            case ACTIVE:
                switch (((ImpUIGameActiveButtons) b).getButton()) {
                    case TIMER:
                        timerPressed();
                        break;
                    case RESTART:
                        restartPressed();
                        break;
                    case QUIT:
                        quitPressed();
                        break;
                    default:
                        break;
                }
                break;
            case EXTRA:
                switch (((ImpUIGameExtraButtons) b).getButton()) {
                    case RESTART:
                        restartPressed();
                        break;
                    case QUIT:
                        quitPressed();
                        break;
                    default:
                        break;
                }
                break;
        }
    }
    
    public float getActiveTimer() {
        return activeTimer;
    }
    public void setActiveTimer(float v) {
        activeTimer = v;
    }
    public void incrementActiveTimer(float v) {
        activeTimer += v;
    }
    
    public void buy1Pressed() {
        //Check if player can afford Buy 1 option, and purchase if true
        System.out.println("BUY1 PRESSED!");
        Spatial buy1Button = buyableButtons.get(0);
        Buyable buy1Buyable = (Buyable) buy1Button.getUserData("buyable");
        
        if(parentAppState.getParentAppState().getGameRules().checkIfPurchasable(buy1Buyable)) {
            removeShopBuyable(0);
            parentAppState.getParentAppState().getGameRules().purchaseFromShop(buy1Buyable);
        }
    }
    public void buy2Pressed() {
        //Check if player can afford Buy 1 option, and purchase if true
        System.out.println("BUY2 PRESSED!");
        Spatial buy2Button = buyableButtons.get(1);
        Buyable buy2Buyable = (Buyable) buy2Button.getUserData("buyable");
        
        if(parentAppState.getParentAppState().getGameRules().checkIfPurchasable(buy2Buyable)) {
            removeShopBuyable(1);
            parentAppState.getParentAppState().getGameRules().purchaseFromShop(buy2Buyable);
        }
    }
    public void buy3Pressed() {
        //Check if player can afford Buy 1 option, and purchase if true
        System.out.println("BUY3 PRESSED!");
        Spatial buy3Button = buyableButtons.get(2);
        Buyable buy3Buyable = (Buyable) buy3Button.getUserData("buyable");
        
        if(parentAppState.getParentAppState().getGameRules().checkIfPurchasable(buy3Buyable)) {
            removeShopBuyable(2);
            parentAppState.getParentAppState().getGameRules().purchaseFromShop(buy3Buyable);
        }
    }
    public void buy4Pressed() {
        //Check if player can afford Buy 1 option, and purchase if true
        System.out.println("BUY4 PRESSED!");
        Spatial buy4Button = buyableButtons.get(3);
        Buyable buy4Buyable = (Buyable) buy4Button.getUserData("buyable");
        
        if(parentAppState.getParentAppState().getGameRules().checkIfPurchasable(buy4Buyable)) {
            removeShopBuyable(3);
            parentAppState.getParentAppState().getGameRules().purchaseFromShop(buy4Buyable);
        }
    }
    public void buy5Pressed() {
        //Check if player can afford Buy 1 option, and purchase if true
        System.out.println("BUY5 PRESSED!");
        Spatial buy5Button = buyableButtons.get(4);
        Buyable buy5Buyable = (Buyable) buy5Button.getUserData("buyable");
        
        if(parentAppState.getParentAppState().getGameRules().checkIfPurchasable(buy5Buyable)) {
            removeShopBuyable(4);
            parentAppState.getParentAppState().getGameRules().purchaseFromShop(buy5Buyable);
        }
    }
    
    public void endshopPressed() {
        System.out.println("End Shop Button Pressed");
        parentAppState.requestSecondLevelStateChange(States.GAME, StatesUIGame.TURN);
    }
    
    public void endturnPressed() {
        parentAppState.requestSecondLevelStateChange(States.GAME, StatesUIGame.ACTIVE);
    }
    
    public void timerPressed() {
    }
    
    public void restartPressed() {
        //GlobalAppState should be told that the Restart button has been pressed
        System.out.println("New Game requested at ImpUIGame");
        parentAppState.requestTopLevelStateChange(States.GAME, States.GAME);
    }
    
    public void reinitializeGame() {
        setActiveTimer(DEFAULT_ACTIVE_TIMER);
        youWinSpatial.removeFromParent();
        youWinTextBox.removeFromParent();
        gameOverSpatial.removeFromParent();
        gameOverTextBox.removeFromParent();
    }
    
    public void quitPressed() {
        //GlobalAppState should be told that the Quit button has been pressed
        System.out.println("Close requested at ImpUIGame");
        parentAppState.requestTopLevelStateChange(States.GAME, States.CLOSE);
    }
    
    public void initHealthStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "<3:                                     ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "healthLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 3.3f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 3.3f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getHealth());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "healthValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                3.3f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initHydrogenStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "H:                                       ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "hydrogenLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 2.8f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 2.8f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getH());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "hydrogenValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                2.8f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initHeliumStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "He:                                     ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "heliumLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 2.3f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 2.3f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getHe());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "heliumValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                2.3f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initOrganicStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "O:                                       ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "organicLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 1.8f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 1.8f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getO());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "organicValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                1.8f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initWaterStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "W:                                      ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "waterLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 1.3f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 1.3f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getW());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "waterValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                1.3f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initMetalStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "M:                                       ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "metalLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 0.8f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 0.8f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getM());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "metalValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                0.8f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initIonStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "I:                                        ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "ionLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, 0.3f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, 0.3f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getI());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "ionValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                0.3f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    public void initRadioStat(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "R:                                       ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "radioLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, -0.2f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, -0.2f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getR());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "radioValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                -0.2f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    
    public void refreshShop() {
        removeAllShopBuyables();
        createShopBuyables();
    }
    
    public void createShopBuyables() {
        {
            Node buyable = bf.getSpecificBuyable(Buyable.BuyableEnum.BUY, new Vector3f(-2.0f, 3.3f, 0f), new Vector3f(0.3f, 0.3f, 0.3f), 0);
            buyableTexts.add(buyable.getChild("buyableDisplayText"));
            nuigs.attachChild(buyable.getChild("buyableDisplayText"));
            ImpUIGameShopButtons buttonStateShop = new ImpUIGameShopButtons();
            buttonStateShop.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.BUY1);
            buyable.getChild("buyableButton").setUserData("button", buttonStateShop);
            buyableButtons.add(buyable.getChild("buyableButton"));
            cuigs.attachChild(buyable.getChild("buyableButton"));
        }
        {
            Node buyable = bf.getRandomBuyable(Buyable.BuyableEnum.BUY, new Vector3f(-2.0f, 2.8f, 0f), new Vector3f(0.3f, 0.3f, 0.3f));
            buyableTexts.add(buyable.getChild("buyableDisplayText"));
            nuigs.attachChild(buyable.getChild("buyableDisplayText"));
            ImpUIGameShopButtons buttonStateShop = new ImpUIGameShopButtons();
            buttonStateShop.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.BUY2);
            buyable.getChild("buyableButton").setUserData("button", buttonStateShop);
            buyableButtons.add(buyable.getChild("buyableButton"));
            cuigs.attachChild(buyable.getChild("buyableButton"));
        }
        {
            Node buyable = bf.getRandomBuyable(Buyable.BuyableEnum.BUY, new Vector3f(-2.0f, 2.3f, 0f), new Vector3f(0.3f, 0.3f, 0.3f));
            buyableTexts.add(buyable.getChild("buyableDisplayText"));
            nuigs.attachChild(buyable.getChild("buyableDisplayText"));
            ImpUIGameShopButtons buttonStateShop = new ImpUIGameShopButtons();
            buttonStateShop.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.BUY3);
            buyable.getChild("buyableButton").setUserData("button", buttonStateShop);
            buyableButtons.add(buyable.getChild("buyableButton"));
            cuigs.attachChild(buyable.getChild("buyableButton"));
        }
        {
            Node buyable = bf.getRandomBuyable(Buyable.BuyableEnum.BUY, new Vector3f(-2.0f, 1.8f, 0f), new Vector3f(0.3f, 0.3f, 0.3f));
            buyableTexts.add(buyable.getChild("buyableDisplayText"));
            nuigs.attachChild(buyable.getChild("buyableDisplayText"));
            ImpUIGameShopButtons buttonStateShop = new ImpUIGameShopButtons();
            buttonStateShop.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.BUY4);
            buyable.getChild("buyableButton").setUserData("button", buttonStateShop);
            buyableButtons.add(buyable.getChild("buyableButton"));
            cuigs.attachChild(buyable.getChild("buyableButton"));
        }
        {
            Node buyable = bf.getRandomBuyable(Buyable.BuyableEnum.BUY, new Vector3f(-2.0f, 1.3f, 0f), new Vector3f(0.3f, 0.3f, 0.3f));
            buyableTexts.add(buyable.getChild("buyableDisplayText"));
            nuigs.attachChild(buyable.getChild("buyableDisplayText"));
            ImpUIGameShopButtons buttonStateShop = new ImpUIGameShopButtons();
            buttonStateShop.setButton(ImpUIGameShopButtons.ImpUIGameShopButtonEnum.BUY5);
            buyable.getChild("buyableButton").setUserData("button", buttonStateShop);
            buyableButtons.add(buyable.getChild("buyableButton"));
            cuigs.attachChild(buyable.getChild("buyableButton"));
        }
    }
    public void removeShopBuyable(int index) {
        buyableTexts.get(index).removeFromParent();
        buyableButtons.get(index).removeFromParent();
    }
    
    public void removeAllShopBuyables() {
        for (int i=0; i<buyableTexts.size(); i++) {
            buyableTexts.get(i).removeFromParent();
        }
        for (int i=0; i<buyableTexts.size(); i++) {
            buyableButtons.get(i).removeFromParent();
        }
        buyableTexts.clear();
        buyableButtons.clear();
    }
    
    public void createTurnBuyables() {
        
    }
    public void removeTurnBuyables() {
        
    }
    
    public void initMaxOrbits(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "Max Orbits:                                ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "maxOrbitsLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, -1.2f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, -1.2f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getGameRules().getNumOrbitsAllowed());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "maxOrbitsValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                -1.2f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    
    public void initOccOrbits(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "Used Orbits:                             ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "occOrbitsLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, -1.7f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, -1.7f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getGameRules().getNumOrbits());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "occOrbitsValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                -1.7f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    
    public void initMaxChannels(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "Max Channels:                              ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "maxChannelsLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, -2.2f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, -2.2f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getGameRules().getNumChannelsAllowed());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "maxChannelsValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                -2.2f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    
    public void initMaxAssaults(Spatial rightJustify) {
        //Modify this line
        String statLabelString = "Max Assaults:                               ";
        //Modify this line
        Node statLabel = fm.generateToonText(statLabelString, "maxAssaultsLabelString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statLabel.scale(0.3f);
        //Modify this line
        statLabel.setLocalTranslation(-7.0f, -2.7f, 0f);
        statLabel.rotate(FastMath.PI/2, 0, 0);
        
        Spatial statTextBox = fm.generateTextBox(rightJustify);
        //Modify this line
        statTextBox.move(0.0f, -2.7f, 0f);
        statTextBox.rotate(FastMath.PI/2, 0, 0);
        
        gameStats.attachChild(statTextBox);
        gameStats.attachChild(statLabel);
        
        //Modify this line
        String statValueString = String.valueOf(parentAppState.getParentAppState().getGameRules().getNumAssaultsAllowed());
        //Modify this line
        Node statValue = fm.generateToonText(statValueString, "maxAssaultsValueString", new ColorRGBA(0.856f, 0.674f, 0.535f, 0f));
        
        statValue.scale(0.3f);
        statValue.rotate(FastMath.PI/2, 0, 0);
        //Modify this line
        statValue.setLocalTranslation(
                statTextBox.getLocalTranslation().getX()+
                        ((BoundingBox) statTextBox.getWorldBound()).getXExtent()-
                        0.15f-
                        (((BoundingBox) statValue.getWorldBound()).getXExtent()*2), 
                -2.7f, 
                0);
        gameStats.attachChild(statValue);
        
        this.attachChild(gameStats);
    }
    
}