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
public class Spawnable implements Savable {
    
    long health = 100l;
    String displayText = "";
    SpawnableEnum se;
    int val;
    WorldGameObjectButtons wgob;
    
    public Spawnable(SpawnableEnum se, int val) {
        this.se = se;
        this.val = val;
        switch (se) {
            case ENEMY:
                switch (val) {
                    case 0:
                        health *= 10l;
                        displayText = "Basic";
                        wgob = WorldGameObjectButtons.BASIC;
                        break;
                    case 1:
                        health *= 13l;
                        displayText = "Background";
                        wgob = WorldGameObjectButtons.BACKGROUND;
                        break;
                    case 2:
                        health *= 17l;
                        displayText = "Star";
                        wgob = WorldGameObjectButtons.STAR;
                        break;
                    case 3:
                        health *= 25l;
                        displayText = "Organic";
                        wgob = WorldGameObjectButtons.ORGANIC;
                        break;
                    case 4:
                        health *= 25l;
                        displayText = "Silicon";
                        wgob = WorldGameObjectButtons.SILICON;
                        break;
                    case 5:
                        health *= 40l;
                        displayText = "Boss";
                        wgob = WorldGameObjectButtons.BOSS;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
    
    public long getHealth() {
        return health;
    }
    public String getDisplayText() {
        return displayText;
    }
    public SpawnableEnum getEnum() {
        return se;
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
    
    public enum SpawnableEnum {
        ENEMY;
    }
}