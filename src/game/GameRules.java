/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import appstates.GlobalAppState;
import com.jme3.scene.Spatial;
import implementations.ui.game.Buyable;
import implementations.ui.game.Buyable.BuyableEnum;
import implementations.world.game.buttons.WorldGameObjectButtons;
import java.util.LinkedList;

/**
 *
 * @author PlaneShaper
 */
public class GameRules {
    
    GlobalAppState gas;
    
    int numOrbitsAllowed = 1;
    int numOrbitsOccupied = 0;
    int numGoldilocksAllowed = 1;
    int numRockyAllowed = 1;
    int numGasAllowed = 1;
    int numAsteroidAllowed = 1;
    int numCometAllowed = 1;
    int numHelioflareAllowed = 1;
    int numHeliowindAllowed = 1;
    int numHeliopauseAllowed = 0;
    int numCosmicrayAllowed = 0;
    int numGammarayburstAllowed = 0;
    
    //The game generates Health
    long multHealth = 0l;
    long addHealth = 20l;
    //The background generates Hydrogen
    long multH = 0l;
    long addH = 20l;
    //The Star generates Helium
    long multHe = 0l;
    long addHe = 10l;
    //Goldilocks channels generate Organics
    long multO = 0l;
    long addO = 0l;
    //Rocky channels generate Metals
    long multM = 0l;
    long addM = 0l;
    //Icy channels generate Water
    long multW = 0l;
    long addW = 0l;
    //Ion channels generate Ions
    long multI = 0l;
    long addI = 0l;
    //Radio channels generate Radiation
    long multR = 0l;
    long addR = 0l;
    
    //Basic enemies reduce Health
    long numBasicEnemies = 0l;
    //Background enemies reduce Hydrogen
    long numBackgroundEnemies = 0l;
    //Star enemies reduce Helium
    long numStarEnemies = 0l;
    //Organic enemies reduce Metals and Water (consume water, destroy metals)
    long numOrganicEnemies = 0l;
    //Silicon enemies reduce Organics and Ions (consume ions, destroy organics)
    long numSiliconEnemies = 0l;
    //Boss enemies reduce Health, Hydrogen, and Radiation
    long numBossEnemies = 0l;
    
    int numAssaults = 0;
    int numChannels = 0;
    
    int numAssaultsAllowed = 1;
    int numChannelsAllowed = 1;
    
    LinkedList<Long> enemyHealthList = new LinkedList<>();
    LinkedList<WorldGameObjectButtons> enemyEnumList = new LinkedList<>();
    LinkedList<Integer> assaultIndexList = new LinkedList<>();
    LinkedList<Integer> enemyRemovalQueueList = new LinkedList<>();
    LinkedList<Integer> assaultRemovalQueueList = new LinkedList<>();
    
    int turn = 0;
    
    public GameRules(GlobalAppState gas) {
        this.gas = gas;
    }
    
    public void calculateTick() {
        
        gas.setHealth((gas.getHealth())+((1l*multH)+addH)-((0l*numBasicEnemies)+(0l*numBossEnemies)));
        gas.setH((gas.getH())+((1l*multH)+addH)-((0l*numBackgroundEnemies)+(0l*numBossEnemies)));
        gas.setHe((gas.getHe())+((1l*multHe)+addHe)-((0l*numBackgroundEnemies)+(0l*numBossEnemies)));
        gas.setO((gas.getO())+((1l*multO)+addO)-((0l*numSiliconEnemies)));
        gas.setM((gas.getM())+((1l*multM)+addM)-((0l*numOrganicEnemies)));
        gas.setW((gas.getW())+((1l*multW)+addW)-((0l*numOrganicEnemies)));
        gas.setI((gas.getI())+((1l*multI)+addI)-((0l*numSiliconEnemies)));
        gas.setR((gas.getR())+((1l*multR)+addR)-((0l*numBossEnemies)));
        
        for (int i=0; i<assaultIndexList.size(); i++) {
            assaultEnemy(assaultIndexList.get(i));
            if (enemyHealthList.get(assaultIndexList.get(i))<=0l) {
                enemyRemovalQueueList.add(assaultIndexList.get(i));
                assaultRemovalQueueList.add(i);
            }
        }
        for (int i=0; i<enemyRemovalQueueList.size(); i++) {
            int index = enemyRemovalQueueList.get(i);
            int index2 = assaultRemovalQueueList.get(i);
            enemyHealthList.remove(index);
            enemyEnumList.remove(index);
            gas.getWAS().removeEnemyRequest(index, index2);
        }
        enemyRemovalQueueList.clear();
        assaultRemovalQueueList.clear();
        
        //Game Over check
        if (gas.getHealth()<0l || gas.getH()<0l || gas.getHe()<0l || gas.getO()<0l || gas.getM()<0l || 
                gas.getW()<0l || gas.getI()<0l || gas.getR()<0l) {
            gas.requestGameOver();
        }
    }
    
    /*
    GOLDILOCK, ASTEROID, GAS, ROCKY, COMET, HELIOFLARE, HELIOWIND, HELIOPAUSE, COSMICRAY, GAMMARAYBURST, 
    BASIC, BACKGROUND, STAR, ORGANIC, SILICON, BOSS;
    */
    
    public void addChannel(WorldGameObjectButtons wgob) {
        switch (wgob) {
            case GOLDILOCK:
                multO += 10;
                addW += 10;
                break;
            case ASTEROID:
                addM += 10;
                break;
            case GAS:
                addH += 10;
                addI += 10;
                break;
            case ROCKY:
                multM += 10;
                addO += 10;
                break;
            case COMET:
                multW += 10;
                break;
            case HELIOFLARE:
                multI += 10;
                break;
            case HELIOWIND:
                addI += 10;
                addR += 5;
                break;
            case HELIOPAUSE:
                addHe += 10;
                break;
            case COSMICRAY:
                multR += 10;
                break;
            case GAMMARAYBURST:
                multR += 20;
                break;
            default:
                break;
        }
        numChannels += 1;
    }
    public void removeChannel(WorldGameObjectButtons wgob) {
        switch (wgob) {
            case GOLDILOCK:
                multO -= 10;
                addW -= 10;
                break;
            case ASTEROID:
                addM -= 10;
                break;
            case GAS:
                addH -= 10;
                addI -= 10;
                break;
            case ROCKY:
                multM -= 10;
                addO -= 10;
                break;
            case COMET:
                multW -= 10;
                break;
            case HELIOFLARE:
                multI -= 10;
                break;
            case HELIOWIND:
                addI -= 10;
                addR -= 5;
                break;
            case HELIOPAUSE:
                addHe -= 10;
                break;
            case COSMICRAY:
                multR -= 10;
                break;
            case GAMMARAYBURST:
                multR -= 20;
                break;
            default:
                break;
        }
        numChannels -= 1;
    }
    
    public void addAssault(WorldGameObjectButtons wgob, int index) {
        switch (wgob) {
            case BASIC:
                addH -= 10;
                break;
            case BACKGROUND:
                addH -= 5;
                addHe -= 10;
                break;
            case STAR:
                addH -= 10;
                addO -= 5;
                addM -= 5;
                break;
            case ORGANIC:
                addM -= 10;
                multI -= 1;
                break;
            case SILICON:
                addO -= 10;
                multW -= 1;
                break;
            case BOSS:
                multHealth -= 2;
                multH -= 1;
                multHe -= 1;
                multO -= 1;
                multM -= 1;
                addR -= 2;
                break;
            default:
                break;
        }
        numAssaults += 1;
        assaultIndexList.add(index);
    }
    
    public void removeAssault(WorldGameObjectButtons wgob) {
        switch (wgob) {
            case BASIC:
                addH += 10;
                break;
            case BACKGROUND:
                addH += 5;
                addHe += 10;
                break;
            case STAR:
                addH += 10;
                addO += 5;
                addM += 5;
                break;
            case ORGANIC:
                addM += 10;
                multI += 1;
                break;
            case SILICON:
                addO += 10;
                multW += 1;
                break;
            case BOSS:
                multHealth += 2;
                multH += 1;
                multHe += 1;
                multO += 1;
                multM += 1;
                addR += 2;
                break;
            default:
                break;
        }
        numAssaults -= 1;
        assaultIndexList.remove(0);
    }
    
    public void removeAssault(WorldGameObjectButtons wgob, int index2) {
        switch (wgob) {
            case BASIC:
                addH += 10;
                break;
            case BACKGROUND:
                addH += 5;
                addHe += 10;
                break;
            case STAR:
                addH += 10;
                addO += 5;
                addM += 5;
                break;
            case ORGANIC:
                addM += 10;
                multI += 1;
                break;
            case SILICON:
                addO += 10;
                multW += 1;
                break;
            case BOSS:
                multHealth += 2;
                multH += 1;
                multHe += 1;
                multO += 1;
                multM += 1;
                addR += 2;
                break;
            default:
                break;
        }
        numAssaults -= 1;
        assaultIndexList.remove(index2);
    }
    
    public void addEnemy(WorldGameObjectButtons wgob, long spawnHealth) {
        switch (wgob) {
            case BASIC:
                addHealth -= 30;
                break;
            case BACKGROUND:
                addH -= 30;
                break;
            case STAR:
                multH -= 1;
                addHe -= 20;
                break;
            case ORGANIC:
                addM -= 20;
                addI -= 10;
                break;
            case SILICON:
                addO -= 20;
                addW -= 10;
                break;
            case BOSS:
                multHealth -= 20;
                addH -= 15;
                addHe -= 15;
                addO -= 20;
                addM -= 10;
                multR -= 15;
                break;
            default:
                break;
        }
        enemyHealthList.add(spawnHealth);
        enemyEnumList.add(wgob);
    }
    
    public void assaultEnemy(int i) {
        switch (enemyEnumList.get(i)) {
            case BASIC:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            case BACKGROUND:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            case STAR:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            case ORGANIC:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            case SILICON:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            case BOSS:
                enemyHealthList.set(i, enemyHealthList.get(i)-20l);
                break;
            default:
                break;
        }
    }
    
    public void removeEnemy(WorldGameObjectButtons wgob) {
        switch (wgob) {
            case BASIC:
                addHealth += 30;
                break;
            case BACKGROUND:
                addH += 30;
                break;
            case STAR:
                multH += 1;
                addHe += 20;
                break;
            case ORGANIC:
                addM += 20;
                addI += 10;
                break;
            case SILICON:
                addO += 20;
                addW += 10;
                break;
            case BOSS:
                multHealth += 20;
                addH += 15;
                addHe += 15;
                addO += 20;
                addM += 10;
                multR += 15;
                break;
            default:
                break;
        }
    }
    
    public int getTurn() {
        return turn;
    }
    public void setTurn(int t) {
        this.turn = t;
    }
    public void incrTurn() {
        this.turn += 1;
    }
    
    public int getNumOrbitsAllowed() {
        return numOrbitsAllowed;
    }
    public void setNumOrbitsAllowed(int i) {
        numOrbitsAllowed = i;
    }
    public int getNumOrbits() {
        return numOrbitsOccupied;
    }
    public void setNumOrbits(int i) {
        numOrbitsOccupied = i;
    }
    
    public int getNumAssaults() {
        return numAssaults;
    }
    public int getNumAssaultsAllowed() {
        return numAssaultsAllowed;
    }
    public int getNumChannels() {
        return numChannels;
    }
    public int getNumChannelsAllowed() {
        return numChannelsAllowed;
    }
    
    public boolean checkIfPurchasable(Buyable buyable) {
        if (buyable.getEnum()==BuyableEnum.BUY) {
            if (buyable.getVal()>0) {
                if (buyable.getCost() <= gas.getH()) {
                    return this.getNumOrbits()<this.getNumOrbitsAllowed();
                }
            }
        }
        
        return buyable.getCost() <= gas.getH();
    }
    public void purchaseFromShop(Buyable buyable) {
        gas.setH(gas.getH()-buyable.getCost());
        switch(buyable.getEnum()) {
            case BUY:
                switch (buyable.getVal()) {
                    case 0:
                        this.setNumOrbitsAllowed(this.getNumOrbitsAllowed()+1);
                        break;
                    default:
                        this.setNumOrbits(this.getNumOrbits()+1);
                        //call WorldAppState to create an Object in ImpWorldGame
                        gas.getWAS().createWorldGameObject(buyable.getWGOB());
                        break;
                }
                break;
            case FEAT:
                break;
            case EVENT:
                break;
            default:
                break;
        }
    }
    
    public void reinitializeGame() {
        numOrbitsAllowed = 1;
        numOrbitsOccupied = 0;
        numGoldilocksAllowed = 1;
        numRockyAllowed = 1;
        numGasAllowed = 1;
        numAsteroidAllowed = 1;
        numCometAllowed = 1;
        numHelioflareAllowed = 1;
        numHeliowindAllowed = 1;
        numHeliopauseAllowed = 0;
        numCosmicrayAllowed = 0;
        numGammarayburstAllowed = 0;
        
        //The game generates Health
        multHealth = 0l;
        addHealth = 20l;
        //The background generates Hydrogen
        multH = 0l;
        addH = 20l;
        //The Star generates Helium
        multHe = 0l;
        addHe = 10l;
        //Goldilocks channels generate Organics
        multO = 0l;
        addO = 0l;
        //Rocky channels generate Metals
        multM = 0l;
        addM = 0l;
        //Icy channels generate Water
        multW = 0l;
        addW = 0l;
        //Ion channels generate Ions
        multI = 0l;
        addI = 0l;
        //Radio channels generate Radiation
        multR = 0l;
        addR = 0l;
        
        //Basic enemies reduce Health
        numBasicEnemies = 0l;
        //Background enemies reduce Hydrogen
        numBackgroundEnemies = 0l;
        //Star enemies reduce Helium
        numStarEnemies = 0l;
        //Organic enemies reduce Metals and Water (consume water, destroy metals)
        numOrganicEnemies = 0l;
        //Silicon enemies reduce Organics and Ions (consume ions, destroy organics)
        numSiliconEnemies = 0l;
        //Boss enemies reduce Health, Hydrogen, and Radiation
        numBossEnemies = 0l;
        
        numAssaults = 0;
        numChannels = 0;
        
        numAssaultsAllowed = 1;
        numChannelsAllowed = 1;
        
        enemyHealthList = new LinkedList<>();
        enemyEnumList = new LinkedList<>();
        assaultIndexList = new LinkedList<>();
        enemyRemovalQueueList = new LinkedList<>();
        assaultRemovalQueueList = new LinkedList<>();
        
        turn = 0;
    }
}