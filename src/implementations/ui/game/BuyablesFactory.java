/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.FontManager;
import implementations.ui.game.Buyable.BuyableEnum;
import implementations.ui.game.Spawnable.SpawnableEnum;
import java.util.Random;

/**
 *
 * @author PlaneShaper
 */
public class BuyablesFactory {
    
    AssetManager assetManager;
    FontManager fontManager;
    Random r;
    
    public BuyablesFactory(AssetManager assetManager, FontManager fontManager, Random r) {
        this.assetManager = assetManager;
        this.fontManager = fontManager;
        this.r = r;
    }
    
    public Node getRandomBuyable(BuyableEnum buyEnum, Vector3f position, Vector3f scale) {
        int key = r.nextInt(1, 11);
        
        Node buyableUIElement = new Node("buyableUIElement");
        
        Buyable buyable = new Buyable(buyEnum, key);
        
        Spatial buyableUIText = fontManager.generateToonText(buyable.getDisplayText(), "buyableDisplayText", new ColorRGBA(ColorRGBA.Yellow));
        buyableUIText.move(position);
        buyableUIText.scale(scale.getX(), scale.getY(), scale.getZ());
        buyableUIText.rotate(FastMath.PI/2, 0, 0);
        
        Spatial buyableSpatial = fontManager.generateTextBox(buyableUIText);
        buyableSpatial.setName("buyableButton");
        
        buyableSpatial.setUserData("buyable", buyable);
        buyableSpatial.rotate(FastMath.PI/2, 0, 0);
        
        buyableUIElement.attachChild(buyableUIText);
        buyableUIElement.attachChild(buyableSpatial);
        
        return buyableUIElement;
    }
    public Node getSpecificBuyable(BuyableEnum buyEnum, Vector3f position, Vector3f scale, int key) {
        Node buyableUIElement = new Node("buyableUIElement");
        
        Buyable buyable = new Buyable(buyEnum, key);
        
        Spatial buyableUIText = fontManager.generateToonText(buyable.getDisplayText(), "buyableDisplayText", new ColorRGBA(ColorRGBA.Yellow));
        buyableUIText.move(position);
        buyableUIText.scale(scale.getX(), scale.getY(), scale.getZ());
        buyableUIText.rotate(FastMath.PI/2, 0, 0);
        
        Spatial buyableSpatial = fontManager.generateTextBox(buyableUIText);
        buyableSpatial.setName("buyableButton");
        
        buyableSpatial.setUserData("buyable", buyable);
        buyableSpatial.rotate(FastMath.PI/2, 0, 0);
        
        buyableUIElement.attachChild(buyableUIText);
        buyableUIElement.attachChild(buyableSpatial);
        
        return buyableUIElement;
    }
    
    public Spawnable getRandomSpawnable(SpawnableEnum spawnEnum) {
        int key = r.nextInt(0, 6);
        
        Spawnable spawn = new Spawnable(spawnEnum, key);
        
        return spawn;
    }
    public Spawnable getSpecificSpawnable(SpawnableEnum spawnEnum, int key) {
        Spawnable spawn = new Spawnable(spawnEnum, key);
        
        return spawn;
    }
}