/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.world.game;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Geometry;
import implementations.world.game.ImpWorldGame;
import implementations.world.game.buttons.WorldGameObjectButtons;
import java.io.IOException;

/**
 *
 * @author PlaneShaper
 */
public class WorldGameObject implements Savable {
    
    ImpWorldGame iwg;
    long health = 50l;
    WorldGameObjectButtons wgob;
    
    public WorldGameObject(ImpWorldGame parentGameImp, long health, WorldGameObjectButtons wgob) {
        this.iwg = parentGameImp;
        this.health = health;
        this.wgob = wgob;
    }
    
    public ImpWorldGame getIWG() {
        return iwg;
    }
    
    @Override
    public void write(JmeExporter je) throws IOException {
    }
    
    @Override
    public void read(JmeImporter ji) throws IOException {
    }
}