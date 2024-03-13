/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.world.game;

import appstates.WorldAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.FontManager;
import game.WorldObjectFactory;
import implementations.ui.game.Buyable;
import implementations.ui.game.BuyablesFactory;
import implementations.ui.game.Spawnable;
import implementations.world.game.buttons.WorldGameObjectButtons;
import java.util.ArrayList;
import java.util.LinkedList;
import spatials.world.game.NodeWorldGame;

/**
 *
 * @author PlaneShaper
 */
public class ImpWorldGame extends Node {
    
    Node currentNode;
    Node clickables;
    Node currentClickables;
    Node gameStats;
    Node analogClickables;
    
    NodeWorldGame nwg = new NodeWorldGame();
    Node cwg = new Node();
    
    FontManager fm;
    WorldObjectFactory wof;
    
    WorldAppState parentAppState;
    
    Spatial sol;
    
    LinkedList<Spatial> channelCurves = new LinkedList<>();
    LinkedList<Spatial> channelTargets = new LinkedList<>();
    LinkedList<Spatial> assaultCurves = new LinkedList<>();
    LinkedList<Spatial> assaultTargets = new LinkedList<>();
    LinkedList<Spatial> enemyCurves = new LinkedList<>();
    LinkedList<Spatial> enemyTargets = new LinkedList<>();
    
    ArrayList<Spatial> enemies = new ArrayList<>();
    ArrayList<Spatial> friends = new ArrayList<>();
    
    public ImpWorldGame(FontManager fm, Node clickables, WorldAppState parentAppState, WorldObjectFactory wof) {
        this.name = "ImpWorldGame";
        this.clickables = clickables;
        this.currentNode = new Node();
        this.currentClickables = new Node();
        clickables.attachChild(currentClickables);
        this.attachChild(currentNode);
        this.fm = fm;
        this.wof = wof;
        this.parentAppState = parentAppState;
        this.gameStats = new Node();
    }
    
    public void init() {
        sol = wof.createWorldObject();
        currentNode.attachChild(sol);
        
        currentClickables.setUserData("ImpWorldGame", this);
        
        //createWorldGameObject(WorldGameObjectButtons.GOLDILOCK, new Vector3f(2, 2, 0));
        //createWorldGameObject(WorldGameObjectButtons.ROCKY, new Vector3f(2, 3, 0));
        //createWorldGameObject(WorldGameObjectButtons.BASIC, new Vector3f(3, 2, 0));
        //createWorldGameObject(WorldGameObjectButtons.STAR, new Vector3f(3, 3, 0));
    }
    
    public void reinitializeGame() {
        while (channelTargets.size()>0) {
            destroyChannel(0);
        }
        while (assaultTargets.size()>0) {
            destroyAssault(0);
        }
        while (enemies.size()>0) {
            destroyEnemy(0, enemies.get(0));
        }
        for (int i=0; i<friends.size(); i++) {
            friends.get(i).removeFromParent();
        }
        friends.clear();
    }
    
    public void generateRandomEnemy(BuyablesFactory bf) {
        Spawnable spawn = bf.getRandomSpawnable(Spawnable.SpawnableEnum.ENEMY);
        long spawnHealth = spawn.getHealth();
        WorldGameObjectButtons wgob = spawn.getWGOB();
        
        //Add the enemy enum type to the game rules
        parentAppState.getParentAppState().getGameRules().addEnemy(wgob, spawnHealth);
        
        //create the WorldGameObject
        createWorldGameObject(wgob);
    }
    
    public void generateEnemyByTurn(BuyablesFactory bf, int turn) {
        ArrayList<Integer> enemiesToGenerate = new ArrayList<>();
        
        switch (turn) {
            case 1:
                enemiesToGenerate.add(0);
                break;
            case 2:
                enemiesToGenerate.add(1);
                break;
            case 3:
                enemiesToGenerate.add(2);
                break;
            case 4:
                enemiesToGenerate.add(0);
                enemiesToGenerate.add(1);
                break;
            case 5:
                enemiesToGenerate.add(0);
                enemiesToGenerate.add(0);
                enemiesToGenerate.add(1);
                enemiesToGenerate.add(2);
                break;
            case 6:
                enemiesToGenerate.add(2);
                enemiesToGenerate.add(3);
                break;
            case 7:
                enemiesToGenerate.add(2);
                enemiesToGenerate.add(4);
                break;
            case 8:
                enemiesToGenerate.add(2);
                enemiesToGenerate.add(1);
                enemiesToGenerate.add(1);
                enemiesToGenerate.add(2);
                break;
            case 9:
                enemiesToGenerate.add(3);
                enemiesToGenerate.add(0);
                enemiesToGenerate.add(1);
                enemiesToGenerate.add(0);
                enemiesToGenerate.add(4);
                break;
            case 10:
                enemiesToGenerate.add(2);
                enemiesToGenerate.add(2);
                break;
            case 11:
                enemiesToGenerate.add(5);
                break;
            default:
                enemiesToGenerate.add(0);
                break;
        }
        
        for (int i=0; i<enemiesToGenerate.size(); i++) {
            Spawnable spawn = bf.getSpecificSpawnable(Spawnable.SpawnableEnum.ENEMY, enemiesToGenerate.get(i));

            long spawnHealth = spawn.getHealth();
            WorldGameObjectButtons wgob = spawn.getWGOB();

            //Add the enemy enum type to the game rules
            parentAppState.getParentAppState().getGameRules().addEnemy(wgob, spawnHealth);

            //create the WorldGameObject
            createWorldGameObject(wgob);
        }
    }
    
    public void removeEnemy(int index, int index2) {
        destroyEnemy(index, enemies.get(index));
        destroyAssault(index2);
    }
    
    public void destroyEnemy(int index, Spatial s) {
        s.removeFromParent();
        for (int i=index; i<enemies.size(); i++) {
            enemies.get(i).move(0, 0.5f, 0);
        }
        enemies.remove(index);
    }
    
    public void destroyAssault(int index2) {
        //Remove the oldest assault
        Spatial oldAssaultCurve = assaultCurves.get(index2);
        Spatial oldAssaultTarget = assaultTargets.get(index2);
        WorldGameObjectButtons oldwgob = ((WorldGameObject) oldAssaultTarget.getUserData("WorldGameObject")).wgob;
        oldAssaultCurve.removeFromParent();
        assaultCurves.remove(index2);
        //Tell the Game Rules what kind of WorldGameObject is no longer being assaulted
        parentAppState.getParentAppState().getGameRules().removeAssault(oldwgob, index2);
        parentAppState.getParentAppState().getGameRules().removeEnemy(oldwgob);
        assaultTargets.remove(index2);
    }
    
    public void destroyChannel(int index2) {
        //Remove the oldest channel
        Spatial oldChannelCurve = channelCurves.get(index2);
        Spatial oldChannelTarget = channelTargets.get(index2);
        WorldGameObjectButtons oldwgob = ((WorldGameObject) oldChannelTarget.getUserData("WorldGameObject")).wgob;
        oldChannelCurve.removeFromParent();
        channelCurves.remove(index2);
        //Tell the Game Rules what kind of WorldGameObject is no longer being assaulted
        parentAppState.getParentAppState().getGameRules().removeChannel(oldwgob);
        channelTargets.remove(index2);
    }
    
    public void createWorldGameObject(WorldGameObjectButtons wgob) {
        Spatial sphere2 = wof.createWorldObject(wgob);
        currentClickables.attachChild(sphere2);
        
        WorldGameObject wgo = new WorldGameObject(this, 50l, wgob);
        
        sphere2.setUserData("WorldGameObject", wgo);
        
        if (wgob.compareTo(WorldGameObjectButtons.BASIC)>=0) {
            if (enemies.size()>0) {
                sphere2.move(enemies.get(enemies.size()-1).getLocalTranslation().add(0f, -0.5f, 0f));
            } else {
                sphere2.move(new Vector3f(3, 2, 0));
            }
            sphere2.scale(0.2f);
            enemies.add(sphere2);
        } else {
            if (friends.size()>0) {
                sphere2.move(friends.get(friends.size()-1).getLocalTranslation().add(0f, -0.5f, 0f));
            } else {
                sphere2.move(new Vector3f(2, 2, 0));
            }
            sphere2.scale(0.2f);
            friends.add(sphere2);
        }
    }
    
    public void createWorldGameObject(WorldGameObjectButtons wgob, Vector3f pos) {
        Spatial sphere2 = wof.createWorldObject(wgob);
        sphere2.move(pos);
        sphere2.scale(0.2f);
        currentClickables.attachChild(sphere2);
        
        WorldGameObject wgo = new WorldGameObject(this, 50l, wgob);
        
        sphere2.setUserData("WorldGameObject", wgo);
        
        if (wgob.compareTo(WorldGameObjectButtons.BASIC)>=0) {
            enemies.add(sphere2);
        } else {
            friends.add(sphere2);
        }
    }
    
    public void buttonPressed(Spatial s) {
        System.out.println("ImpWorldGame has been informed a button has been pressed");
        
        //We need to know what kind of object was pressed and specifically which object was pressed
        //Kinds of objects:
        //GOLDILOCKS, ASTEROID, GAS, ROCKY, COMET, HELIOFLARE, HELIOWIND, HELIOPAUSE, COSMICRAY, GAMMARAYBURST
        //Kinds of enemies:
        //BASIC, BACKGROUND, STAR, ORGANIC, SILICON, BOSS
        
        WorldGameObject wgo = ((WorldGameObject) s.getUserData("WorldGameObject"));
        WorldGameObjectButtons wgob = wgo.wgob;
        
        //Ask the GameRules if we can create a channel/assault
        if (wgob.compareTo(WorldGameObjectButtons.BASIC)>=0) {
            //An enemy was selected, ask if we can create a new assault without removing an old one
            if (parentAppState.getParentAppState().getGameRules().getNumAssaults() < 
                    parentAppState.getParentAppState().getGameRules().getNumAssaultsAllowed()) {
                //Add a new assault
                Spatial assault = wof.createWorldCurve(sol.getLocalTranslation(), s.getLocalTranslation(), 1.5f, false);
                assaultCurves.add(assault);
                currentNode.attachChild(assault);
                assaultTargets.add(s);
                
                //Tell the Game Rules what kind of WorldGameObject is now being assaulted
                parentAppState.getParentAppState().getGameRules().addAssault(wgob, enemies.indexOf(s));
            } else {
                //Remove the oldest assault
                Spatial oldAssaultCurve = assaultCurves.getFirst();
                Spatial oldAssaultTarget = assaultTargets.getFirst();
                WorldGameObjectButtons oldwgob = ((WorldGameObject) oldAssaultTarget.getUserData("WorldGameObject")).wgob;
                oldAssaultCurve.removeFromParent();
                assaultCurves.removeFirst();
                //Tell the Game Rules what kind of WorldGameObject is no longer being assaulted
                parentAppState.getParentAppState().getGameRules().removeAssault(oldwgob);
                assaultTargets.removeFirst();
                
                //Add a new assault
                Spatial channel = wof.createWorldCurve(sol.getLocalTranslation(), s.getLocalTranslation(), 1.5f, false);
                assaultCurves.add(channel);
                currentNode.attachChild(channel);
                assaultTargets.add(s);
                
                //Tell the Game Rules what kind of WorldGameObject is now being assaulted
                parentAppState.getParentAppState().getGameRules().addAssault(wgob, enemies.indexOf(s));
            }
        } else {
            //A friend was selected, ask if we can create a new channel without removing an old one
            if (parentAppState.getParentAppState().getGameRules().getNumChannels() < 
                    parentAppState.getParentAppState().getGameRules().getNumChannelsAllowed()) {
                //Add a new channel
                Spatial channel = wof.createWorldCurve(sol.getLocalTranslation(), s.getLocalTranslation(), 1.5f, true);
                channelCurves.add(channel);
                currentNode.attachChild(channel);
                channelTargets.add(s);
                
                //Tell the Game Rules what kind of WorldGameObject is now being channeled
                parentAppState.getParentAppState().getGameRules().addChannel(wgob);
            } else {
                //Remove the oldest channel
                Spatial oldChannelCurve = channelCurves.getFirst();
                Spatial oldChannelTarget = channelTargets.getFirst();
                WorldGameObjectButtons oldwgob = ((WorldGameObject) oldChannelTarget.getUserData("WorldGameObject")).wgob;
                oldChannelCurve.removeFromParent();
                channelCurves.removeFirst();
                //Tell the Game Rules what kind of WorldGameObject is no longer being channeled
                parentAppState.getParentAppState().getGameRules().removeChannel(oldwgob);
                channelTargets.removeFirst();
                
                //Add a new channel
                Spatial channel = wof.createWorldCurve(sol.getLocalTranslation(), s.getLocalTranslation(), 1.5f, true);
                channelCurves.add(channel);
                currentNode.attachChild(channel);
                channelTargets.add(s);
                
                //Tell the Game Rules what kind of WorldGameObject is now being channeled
                parentAppState.getParentAppState().getGameRules().addChannel(wgob);
            }
        }
    }
}