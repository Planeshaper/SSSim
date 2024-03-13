/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.game;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import implementations.world.game.buttons.WorldGameObjectButtons;
import java.io.IOException;

/**
 *
 * @author PlaneShaper
 */
public class Buyable implements Savable {
    
    long cost = 0l;
    String displayText = "";
    BuyableEnum be;
    int val;
    WorldGameObjectButtons wgob;
    
    public Buyable(BuyableEnum be, int val) {
        this.be = be;
        this.val = val;
        switch (be) {
            case BUY:
                switch (val) {
                    case 0:
                        cost = 100l;
                        displayText = "Buy Orbit - Cost 100 H";
                        break;
                    case 1:
                        cost = 800l;
                        displayText = "Buy Goldilocks Planet - Cost 800 H";
                        wgob = WorldGameObjectButtons.GOLDILOCK;
                        break;
                    case 2:
                        cost = 800l;
                        displayText = "Buy Rocky Planet - Cost 800 H";
                        wgob = WorldGameObjectButtons.ROCKY;
                        break;
                    case 3:
                        cost = 250l;
                        displayText = "Buy Gas Giant - Cost 250 H";
                        wgob = WorldGameObjectButtons.GAS;
                        break;
                    case 4:
                        cost = 350l;
                        displayText = "Buy Asteroid - Cost 350 H";
                        wgob = WorldGameObjectButtons.ASTEROID;
                        break;
                    case 5:
                        cost = 350l;
                        displayText = "Buy Comet - Cost 350 H";
                        wgob = WorldGameObjectButtons.COMET;
                        break;
                    case 6:
                        cost = 700l;
                        displayText = "Buy Helioflare - Cost 700 H";
                        wgob = WorldGameObjectButtons.HELIOFLARE;
                        break;
                    case 7:
                        cost = 1000l;
                        displayText = "Buy Heliowind - Cost 1000 H";
                        wgob = WorldGameObjectButtons.HELIOWIND;
                        break;
                    case 8:
                        cost = 1500l;
                        displayText = "Buy Heliopause - Cost 1500 H";
                        wgob = WorldGameObjectButtons.HELIOPAUSE;
                        break;
                    case 9:
                        cost = 2500l;
                        displayText = "Buy Cosmic Ray - Cost 2500 H";
                        wgob = WorldGameObjectButtons.COSMICRAY;
                        break;
                    case 10:
                        cost = 5000l;
                        displayText = "Buy Gamma Ray Burst - Cost 5000 H";
                        wgob = WorldGameObjectButtons.GAMMARAYBURST;
                        break;
                    default:
                        break;
                }
                break;
            case FEAT:
                switch (val) {
                    case 0:
                        displayText = "<3 Generation +50%";
                        break;
                    case 1:
                        displayText = "H Generation +50%";
                        break;
                    case 2:
                        displayText = "He Generation +10%";
                        break;
                    case 3:
                        displayText = "O Generation +10%";
                        break;
                    case 4:
                        displayText = "W Generation +10%";
                        break;
                    case 5:
                        displayText = "M Generation +10%";
                        break;
                    case 6:
                        displayText = "I Generation +10%";
                        break;
                    case 7:
                        displayText = "R Generation +10%";
                        break;
                    case 8:
                        displayText = "+1 Channel";
                        break;
                    case 9:
                        displayText = "+1 Assault";
                        break;
                    case 10:
                        displayText = "Enemies 10% weaker";
                        break;
                    default:
                        break;
                }
                break;
            case EVENT:
                switch (val) {
                    case 0:
                        displayText = "Aries";
                        break;
                    case 1:
                        displayText = "Taurus";
                        break;
                    case 2:
                        displayText = "Gemini";
                        break;
                    case 3:
                        displayText = "Cancer";
                        break;
                    case 4:
                        displayText = "Leo";
                        break;
                    case 5:
                        displayText = "Virgo";
                        break;
                    case 6:
                        displayText = "Libra";
                        break;
                    case 7:
                        displayText = "Scorpio";
                        break;
                    case 8:
                        displayText = "Ophiuchus";
                        break;
                    case 9:
                        displayText = "Saggitarius";
                        break;
                    case 10:
                        displayText = "Capricorn";
                        break;
                    case 11:
                        displayText = "Aquarius";
                        break;
                    case 12:
                        displayText = "Pisces";
                        break;
                    case 13:
                        displayText = "Cetus";
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
    
    public long getCost() {
        return cost;
    }
    public String getDisplayText() {
        return displayText;
    }
    public BuyableEnum getEnum() {
        return be;
    }
    public WorldGameObjectButtons getWGOB() {
        return wgob;
    }
    public int getVal() {
        return val;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
    public enum BuyableEnum {
        BUY, FEAT, EVENT;
    }
}