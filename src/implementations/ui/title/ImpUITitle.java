/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.title;

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
import implementations.ui.title.buttons.ImpUITitleSplashButtons;
import java.util.ArrayList;
import spatials.ui.title.NodeUITitlePlay;
import spatials.ui.title.NodeUITitleScreen;
import spatials.ui.title.NodeUITitleSettings;
import states.States;
import states.statesui.StatesUITitle;

/**
 *
 * @author PlaneShaper
 */
public class ImpUITitle extends Node {
    
    Node currentNode;
    Node clickables;
    Node currentClickables;
    NodeUITitleScreen nuits = new NodeUITitleScreen();
    Node cuits = new Node();
    NodeUITitlePlay nuitp = new NodeUITitlePlay();
    Node cuitp = new Node();
    NodeUITitleSettings nuito = new NodeUITitleSettings();
    Node cuito = new Node();
    FontManager fm;
    
    UIAppState parentAppState;
    
    public ImpUITitle(FontManager fm, Node clickables, UIAppState parentAppState) {
        this.name = "ImpUISplash";
        this.clickables = clickables;
        this.currentNode = new Node();
        this.currentClickables = new Node();
        this.attachChild(currentNode);
        this.fm = fm;
        this.parentAppState = parentAppState;
    }
    
    public void init() {
        initNUITS();
        initNUITP();
        initNUITO();
    }
    
    public void detachCurrentNodeUI() {
        currentNode.removeFromParent();
        currentClickables.removeFromParent();
    }
    
    public void attachNodeUI(StatesUITitle s) {
        switch (s) {
            case TITLE:
                this.attachChild(nuits);
                currentNode = nuits;
                this.clickables.attachChild(cuits);
                currentClickables = cuits;
                break;
            case PLAY:
                this.attachChild(nuitp);
                currentNode = nuitp;
                this.clickables.attachChild(cuitp);
                currentClickables = cuitp;
                break;
            case SETTINGS:
                this.attachChild(nuito);
                currentNode = nuito;
                this.clickables.attachChild(cuito);
                currentClickables = cuito;
        }
    }
    
    public void initNUITS() {
        cuits.setUserData("UIInterface", this);
        
        String playString = "Play";
        String settingsString = "Help";
        String quitString = "Quit";
        
        Node supersplashs = fm.generateToonText(playString, "playString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node splashs = fm.generateToonText(settingsString, "settingsString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        Node subsplashs = fm.generateToonText(quitString, "quitString", new ColorRGBA(0.574f,0.535f,0.856f,0f));
        
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
        
        Spatial splashb = fm.generateTextBox(splashs);
        splashb.rotate(FastMath.PI/2, 0, 0);
        ImpUITitleSplashButtons splashb_b = new ImpUITitleSplashButtons();
        splashb_b.setButton(ImpUITitleSplashButtons.ImpUITitleSplashButtonEnum.SETTINGS);
        splashb.setUserData("button", splashb_b);
        cuits.attachChild(splashb);
        Spatial supersplashb = fm.generateTextBox(supersplashs);
        supersplashb.rotate(FastMath.PI/2, 0, 0);
        ImpUITitleSplashButtons supersplashb_b = new ImpUITitleSplashButtons();
        supersplashb_b.setButton(ImpUITitleSplashButtons.ImpUITitleSplashButtonEnum.PLAY);
        supersplashb.setUserData("button", supersplashb_b);
        cuits.attachChild(supersplashb);
        Spatial subsplashb = fm.generateTextBox(subsplashs);
        subsplashb.rotate(FastMath.PI/2, 0, 0);
        ImpUITitleSplashButtons subsplashb_b = new ImpUITitleSplashButtons();
        subsplashb_b.setButton(ImpUITitleSplashButtons.ImpUITitleSplashButtonEnum.QUIT);
        subsplashb.setUserData("button", subsplashb_b);
        cuits.attachChild(subsplashb);
    }
    
    public void initNUITO() {
        cuito.setUserData("UIInterface", this);
        
        /*
        */
        
        String line1 = "Hello!";
        String line2 = "This is a short game about defending a star system";
        String line3 = "In game, you will have some resources on the left:";
        String line4 = "Your health (<3), Hydrogen (H), Helium (He), Organic matter (O),";
        String line5 = "Water (W), Metals (M), Ions (I), and Radioactive Particles (R)";
        String line6 = "If any of these go below zero, the game ends in a loss!";
        String line7 = " ";
        String line8 = "During the Shop phase, you can purchase Orbits (you begin with 1)";
        String line9 = "You can also purchase Friendly Celestial Objects to fill those Orbits";
        String line10 = "These friends will appear in a column to the immediate right of your Star";
        String line11 = " ";
        String line12 = "After the Shop/Planning phase, you can click on one of your friends";
        String line13 = "This will open a channel to that friend, and you will gain resources while it's open";
        String line14 = "You can only have 1 channel; clicking on another friend will switch the channel";
        String line15 = " ";
        String line16 = "During the game, enemies will appear in a column to the immediate right of your friends";
        String line17 = "Enemies will drain your resources over time, depending on the type of enemy";
        String line18 = "Clicking on an enemy will assault that enemy, draining more resources, depending on the enemy";
        String line19 = "You can only assault 1 enemy at a time; clicking on another will change your assault";
        String line20 = " ";
        String line21 = "The goal is to figure out which kinds of friends boost which resources,";
        String line22 = "And which kind of enemies drain which resources";
        String line23 = "Each turn is approximately 7.5 seconds";
        String line24 = "If you survive 12 turns, you win!";
        String line25 = "Good Luck!!";
        
        ArrayList<String> tutorialLines = new ArrayList<>();
        
        tutorialLines.add(line1);
        tutorialLines.add(line2);
        tutorialLines.add(line3);
        tutorialLines.add(line4);
        tutorialLines.add(line5);
        tutorialLines.add(line6);
        tutorialLines.add(line7);
        tutorialLines.add(line8);
        tutorialLines.add(line9);
        tutorialLines.add(line10);
        tutorialLines.add(line11);
        tutorialLines.add(line12);
        tutorialLines.add(line13);
        tutorialLines.add(line14);
        tutorialLines.add(line15);
        tutorialLines.add(line16);
        tutorialLines.add(line17);
        tutorialLines.add(line18);
        tutorialLines.add(line19);
        tutorialLines.add(line20);
        tutorialLines.add(line21);
        tutorialLines.add(line22);
        tutorialLines.add(line23);
        tutorialLines.add(line24);
        tutorialLines.add(line25);
        
        for (int i=0; i<tutorialLines.size(); i++) {
            Node nodeTutorialString = fm.generateToonText(tutorialLines.get(i), ("tutorialString" + i), new ColorRGBA(0.574f,0.535f,0.856f,0f));
            nodeTutorialString.scale(0.3f);
            nodeTutorialString.setLocalTranslation(-(((BoundingBox) nodeTutorialString.getWorldBound()).getXExtent()), (3.5f-(0.3f*i)), 0);
            nodeTutorialString.rotate(FastMath.PI/2, 0, 0);
            nuito.attachChild(nodeTutorialString);
        }
        
        Spatial tutorialTextBox = fm.generateBox(new Vector3f(0,0,-0.1f), new Vector3f(5.1f, 3.7f, 0.1f));
        tutorialTextBox.rotate(FastMath.PI/2, 0, 0);
        nuito.attachChild(tutorialTextBox);
        
        /*
        */
        
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
        
        nuito.attachChild(nodeNewGame);
        nuito.attachChild(nodeQuitGame);
        
        Spatial buttonNewGame = fm.generateTextBox(nodeNewGame);
        buttonNewGame.rotate(FastMath.PI/2, 0, 0);
        ImpUITitleSplashButtons buttonStateNewGame = new ImpUITitleSplashButtons();
        buttonStateNewGame.setButton(ImpUITitleSplashButtons.ImpUITitleSplashButtonEnum.PLAY);
        buttonNewGame.setUserData("button", buttonStateNewGame);
        cuito.attachChild(buttonNewGame);
        
        Spatial buttonQuitGame = fm.generateTextBox(nodeQuitGame);
        buttonQuitGame.rotate(FastMath.PI/2, 0, 0);
        ImpUITitleSplashButtons buttonStateQuitGame = new ImpUITitleSplashButtons();
        buttonStateQuitGame.setButton(ImpUITitleSplashButtons.ImpUITitleSplashButtonEnum.QUIT);
        buttonQuitGame.setUserData("button", buttonStateQuitGame);
        cuito.attachChild(buttonQuitGame);
    }
    
    public void initNUITP() {
        String saveOne = "SAVE 1";
        String saveTwo = "SAVE 2";
        String saveThree = "SAVE 3";
        
        Node saveOneS = fm.generateToonText(saveOne, "saveOne", ColorRGBA.Green);
        Node saveTwoS = fm.generateToonText(saveTwo, "saveTwo", ColorRGBA.Green);
        Node saveThreeS = fm.generateToonText(saveThree, "saveThree", ColorRGBA.Green);
        
        //These represent the extents of the visible area of the UI viewport
        //Ensure that the extrema of all UI elements are within this space
        //ul.setLocalTranslation(-7.3f, 4.1f, 0f);
        //ur.setLocalTranslation(7.3f, 4.1f, 0f);
        //ll.setLocalTranslation(-7.3f, -4.1f, 0f);
        //lr.setLocalTranslation(7.3f, -4.1f, 0f);
        
        saveOneS.setLocalTranslation(-(((BoundingBox) saveOneS.getWorldBound()).getXExtent()), 1.5f, 0f);
        saveTwoS.setLocalTranslation(-(((BoundingBox) saveTwoS.getWorldBound()).getXExtent()), 0f, 0f);
        saveThreeS.setLocalTranslation(-(((BoundingBox) saveThreeS.getWorldBound()).getXExtent()), -1.5f, 0f);
        
        saveOneS.rotate(FastMath.PI/2, 0, 0);
        saveTwoS.rotate(FastMath.PI/2, 0, 0);
        saveThreeS.rotate(FastMath.PI/2, 0, 0);
        
        nuitp.attachChild(saveOneS);
        nuitp.attachChild(saveTwoS);
        nuitp.attachChild(saveThreeS);
    }
    
    public void buttonPressed(ImpUITitleSplashButtons b) {
        switch (b.getButton()) {
            case PLAY:
                playPressed();
                break;
            case SETTINGS:
                settingsPressed();
                break;
            case QUIT:
                System.out.println("Close button pressed");
                quitPressed();
                break;
            default:
                break;
        }
    }
    
    public void playPressed() {
        //UIAppState should be told that the Play button has been pressed
        //Save files NYI
        //Currently GlobalAppState is being told that the Play button has been pressed
        parentAppState.requestTopLevelStateChange(States.TITLE, States.GAME);
    }
    
    public void settingsPressed() {
        //UIAppState should be told that the Settings button has been pressed
        parentAppState.requestSecondLevelStateChange(States.TITLE, StatesUITitle.SETTINGS);
    }
    
    public void quitPressed() {
        //GlobalAppState should be told that the Quit button has been pressed
        System.out.println("Close requested at ImpUITitle");
        parentAppState.requestTopLevelStateChange(States.TITLE, States.CLOSE);
    }
}