/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Curve;
import com.jme3.texture.Texture;
import implementations.world.game.buttons.WorldGameObjectButtons;

/**
 *
 * @author PlaneShaper
 */
public class WorldObjectFactory {
    
    AssetManager assetManager;
    
    public WorldObjectFactory (AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    public Spatial createWorldObject() {
        Spatial worldObject = assetManager.loadModel("Models/sphere.j3o");
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
        worldObject.setMaterial(mat);
        
        return worldObject;
    }
    /*
    GOLDILOCK, ASTEROID, GAS, ROCKY, COMET, HELIOFLARE, HELIOWIND, HELIOPAUSE, COSMICRAY, GAMMARAYBURST, 
    BASIC, BACKGROUND, STAR, ORGANIC, SILICON, BOSS;
    */
    public Spatial createWorldObject(WorldGameObjectButtons wgob) {
        Spatial worldObject = assetManager.loadModel("Models/sphere.j3o");
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        switch (wgob) {
            case GOLDILOCK:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Green));
                break;
            case ASTEROID:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.DarkGray));
                break;
            case GAS:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
                break;
            case ROCKY:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Brown));
                break;
            case COMET:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Blue));
                break;
            case HELIOFLARE:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Yellow));
                break;
            case HELIOWIND:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.LightGray));
                break;
            case HELIOPAUSE:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.White));
                break;
            case COSMICRAY:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Pink));
                break;
            case GAMMARAYBURST:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Magenta));
                break;
            case BASIC:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.LightGray));
                break;
            case BACKGROUND:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.White));
                break;
            case STAR:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Yellow));
                break;
            case ORGANIC:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Green));
                break;
            case SILICON:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Cyan));
                break;
            case BOSS:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Red));
                break;
            default:
                mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
                break;
        }
        worldObject.setMaterial(mat);
        
        return worldObject;
    }
    
    public Spatial createWorldCurve(Vector3f startPosition, Vector3f endPosition, float curveLength, boolean isChannel) {
        Vector3f[] bezierControls = new Vector3f[4];
        bezierControls[0] = startPosition;
        bezierControls[1] = startPosition.add(curveLength, 0, 0);
        bezierControls[2] = endPosition.subtract(curveLength, 0, 0);
        bezierControls[3] = endPosition;
        
        Spline spline = new Spline(Spline.SplineType.Bezier, bezierControls, 20, false);
        Curve curve = new Curve(spline, 40);
        Geometry line = new Geometry("line", curve);
        Material mat = new Material(assetManager, "MatDefs/DashedMotileCurve.j3md");
        Texture tex = isChannel ? assetManager.loadTexture("Textures/white.png") : assetManager.loadTexture("Textures/lineargradientspectrum.png");
        mat.setTexture("ColorMap", tex);
        //mat.setColor("Color", new ColorRGBA(ColorRGBA.Cyan));
        line.setMaterial(mat);
        line.getMaterial().getAdditionalRenderState().setLineWidth(1f);
        
        return line;
    }
    
    public Node createWorldOrbit(Vector3f centerPosition, Vector3f orientation, float majorAxis, float minorAxis) {
        Node curves = new Node("curves");
        
        float halfMajor = majorAxis/2f;
        float twothirdsMajor = 2f*majorAxis/3f;
        float halfMinor = minorAxis/2f;
        
        //Spatial bezier1 = createWorldCurve
        
        return curves;
    }
}