/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Arrays;
import spatials.spatialcontrols.BlinkControl;
import spatials.spatialcontrols.BreathControl;
import spatials.spatialcontrols.ColorControl;

/**
 *
 * @author PlaneShaper
 */
public class FontManager extends Node {
    
    AssetManager assetManager;
    
    public FontManager(AssetManager am) {
        this.assetManager = am;
        this.setName("FontManager");
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyzαβγδεζηθικλμνξοπρσςτυφχψω";
        String numerals = "0123456789";
        ArrayList<ArrayList<String>> punctuation = new ArrayList<>();
        for (int i=0; i<upperCase.length(); i++) {
            Spatial charSpatial = assetManager.loadModel("Models/mlmodern_obj/upper" + upperCase.charAt(i) + ".j3o");
            charSpatial.setName(String.valueOf(upperCase.charAt(i)));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
            charSpatial.setMaterial(mat);
            this.attachChild(charSpatial);
        }
        for (int i=0; i<lowerCase.length(); i++) {
            Spatial charSpatial = assetManager.loadModel("Models/mlmodern_obj/lower" + lowerCase.charAt(i) + ".j3o");
            charSpatial.setName(String.valueOf(lowerCase.charAt(i)));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
            charSpatial.setMaterial(mat);
            this.attachChild(charSpatial);
        }
        for (int i=0; i<numerals.length(); i++) {
            Spatial charSpatial = assetManager.loadModel("Models/mlmodern_numerals_obj/" + numerals.charAt(i) + ".j3o");
            charSpatial.setName(String.valueOf(numerals.charAt(i)));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
            charSpatial.setMaterial(mat);
            this.attachChild(charSpatial);
        }
        
        punctuation.add(new ArrayList<>(Arrays.asList("ampersand","&")));
        punctuation.add(new ArrayList<>(Arrays.asList("apostrophe","'")));
        punctuation.add(new ArrayList<>(Arrays.asList("asterisk","*")));
        punctuation.add(new ArrayList<>(Arrays.asList("at","@")));
        punctuation.add(new ArrayList<>(Arrays.asList("backwardslash","\\")));
        punctuation.add(new ArrayList<>(Arrays.asList("carrot","^")));
        punctuation.add(new ArrayList<>(Arrays.asList("closecurlybracket","}")));
        punctuation.add(new ArrayList<>(Arrays.asList("closeparenthesis",")")));
        punctuation.add(new ArrayList<>(Arrays.asList("closesquarebracket","]")));
        punctuation.add(new ArrayList<>(Arrays.asList("colon",":")));
        punctuation.add(new ArrayList<>(Arrays.asList("comma",",")));
        punctuation.add(new ArrayList<>(Arrays.asList("dash","-")));
        punctuation.add(new ArrayList<>(Arrays.asList("dollar","$")));
        punctuation.add(new ArrayList<>(Arrays.asList("doubleapostrophe","\"")));
        punctuation.add(new ArrayList<>(Arrays.asList("emdash","—")));
        punctuation.add(new ArrayList<>(Arrays.asList("endash","–")));
        punctuation.add(new ArrayList<>(Arrays.asList("equals","=")));
        punctuation.add(new ArrayList<>(Arrays.asList("exclamation","!")));
        punctuation.add(new ArrayList<>(Arrays.asList("forwardslash","/")));
        punctuation.add(new ArrayList<>(Arrays.asList("grave","`")));
        punctuation.add(new ArrayList<>(Arrays.asList("greaterthan",">")));
        punctuation.add(new ArrayList<>(Arrays.asList("hyphen","–")));
        punctuation.add(new ArrayList<>(Arrays.asList("lessthan","<")));
        punctuation.add(new ArrayList<>(Arrays.asList("minus","−")));
        punctuation.add(new ArrayList<>(Arrays.asList("octothorpe","#")));
        punctuation.add(new ArrayList<>(Arrays.asList("opencurlybracket","{")));
        punctuation.add(new ArrayList<>(Arrays.asList("openparenthesis","(")));
        punctuation.add(new ArrayList<>(Arrays.asList("opensquarebracket","[")));
        punctuation.add(new ArrayList<>(Arrays.asList("percent","%")));
        punctuation.add(new ArrayList<>(Arrays.asList("period",".")));
        punctuation.add(new ArrayList<>(Arrays.asList("pipe","|")));
        punctuation.add(new ArrayList<>(Arrays.asList("plus","+")));
        punctuation.add(new ArrayList<>(Arrays.asList("question","?")));
        punctuation.add(new ArrayList<>(Arrays.asList("semicolon",";")));
        punctuation.add(new ArrayList<>(Arrays.asList("tilde","~")));
        punctuation.add(new ArrayList<>(Arrays.asList("underscore","_")));
        
        for (int i=0; i<punctuation.size(); i++) {
            Spatial charSpatial = assetManager.loadModel("Models/mlmodern_punctuation_obj/" + punctuation.get(i).get(0) + ".j3o");
            charSpatial.setName(punctuation.get(i).get(1));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
            charSpatial.setMaterial(mat);
            this.attachChild(charSpatial);
        }
        
        {
            Box spaceCharacter = new Box(0.05f, 0.01f, 0.5f);
            Spatial charSpatial = new Geometry(" ", spaceCharacter);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", new ColorRGBA(ColorRGBA.Orange));
            mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
            charSpatial.setMaterial(mat);
            this.attachChild(charSpatial);
        }
    }
    
    public Node generateText(String text, String name) {
        Node textNode = new Node(name);
        float stringLength = 0f;
        
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            Spatial nextCharacter = this.getChild(String.valueOf(c)).clone();
            Material nextCharacterMat = new Material(((Geometry) nextCharacter).getMaterial().getMaterialDef());
            nextCharacterMat.setColor("Color", new ColorRGBA(ColorRGBA.Red));
            ((ColorRGBA) nextCharacterMat.getParam("Color").getValue()).a = 1f;
            if (c==' ') {
                nextCharacterMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
            }
            nextCharacter.addControl(new BlinkControl());
            nextCharacter.addControl(new BreathControl());
            nextCharacter.addControl(new ColorControl());
            nextCharacterMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            nextCharacter.setQueueBucket(RenderQueue.Bucket.Translucent);
            nextCharacter.setMaterial(nextCharacterMat);
            nextCharacter.setLocalTranslation(stringLength, 0f, 0f);
            stringLength += 2*((BoundingBox) nextCharacter.getWorldBound()).getXExtent();
            stringLength += 0.1f;
            textNode.attachChild(nextCharacter);
            //System.out.print(c);
        }
        //System.out.println();
        
        return textNode;
    }
    
    public Node generateToonText(String text, String name, ColorRGBA diffuseColor) {
        Node textNode = new Node(name);
        float stringLength = 0f;
        
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            Spatial nextCharacter = this.getChild(String.valueOf(c)).clone();
            Material nextCharacterMat = assetManager.loadMaterial("Materials/Toon_Base_Testbed.j3m").clone();
            nextCharacterMat.setColor("Diffuse", new ColorRGBA(diffuseColor));
            if (c==' ') {
                nextCharacterMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
            }
            nextCharacter.setMaterial(nextCharacterMat);
            nextCharacter.setLocalTranslation(stringLength, 0f, 0f);
            stringLength += 2*((BoundingBox) nextCharacter.getWorldBound()).getXExtent();
            stringLength += 0.1f;
            textNode.attachChild(nextCharacter);
            //System.out.print(c);
        }
        //System.out.println();
        
        return textNode;
    }
    
    public Spatial generateChar(char c, String name) {
        Spatial nextCharacter = this.getChild(String.valueOf(c)).clone();
        
        nextCharacter.setName(name);
        
        Material nextCharacterMat = new Material(((Geometry) nextCharacter).getMaterial().getMaterialDef());
        nextCharacterMat.setColor("Color", new ColorRGBA(ColorRGBA.Black));
        if (c==' ') {
            nextCharacterMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);
        }
        nextCharacter.addControl(new BlinkControl());
        nextCharacter.addControl(new BreathControl());
        nextCharacter.addControl(new ColorControl());
        nextCharacterMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        nextCharacter.setQueueBucket(RenderQueue.Bucket.Translucent);
        nextCharacter.setMaterial(nextCharacterMat);
        
        return nextCharacter;
    }
    
    public Spatial generateTextBox(Spatial text) {
        Spatial textBox = assetManager.loadModel("Models/ui_element_rrect.j3o");
        textBox.setName(text.getName() + "_tb");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(ColorRGBA.DarkGray));
        textBox.setMaterial(mat);
        
        textBox.move(text.getLocalTranslation().getX()+((BoundingBox) text.getWorldBound()).getXExtent()+0.02f, 
                text.getLocalTranslation().getY()+((BoundingBox) text.getWorldBound()).getYExtent()/2+0.01f, 
                0);
        
        textBox.scale(((BoundingBox) text.getWorldBound()).getXExtent()+0.15f, 
                ((BoundingBox) text.getWorldBound()).getZExtent(), 
                ((BoundingBox) text.getWorldBound()).getYExtent()*2+0.3f);
        
        return textBox;
    }
    
    public Spatial generateBox(Vector3f position, Vector3f scale) {
        Spatial box = assetManager.loadModel("Models/ui_element_rrect.j3o");
        box.setName("_tb");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(ColorRGBA.DarkGray));
        box.setMaterial(mat);
        
        box.move(position);
        
        box.scale(scale.getX()+0.15f, 
                scale.getZ(), 
                scale.getY()*2+0.3f);
        
        return box;
    }
}