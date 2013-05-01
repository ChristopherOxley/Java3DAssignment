/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dassignment;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author chrisoxley
 */


// Basic class to create a simple horse made of boxes of different shapes, sizes, positions and orientations.
public class Horse {
    public static TransformGroup createHorse( ){

        
        // Create Horse
        TransformGroup horseTG = new TransformGroup();

        
        // Create a standard box for the body
        Box body = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        body.setAppearance(Horse.horseColor());
        horseTG.addChild(body);
        
        // Set the leg dimansions and position variables for easy tweaking
        float legWidth = 0.02f;
        float legHeight = 0.2f;
        float legDepth = 0.02f;
        float legX = 0.3f;
        float legY = 0.2f;
        float legZ = 0.08f;
        int legRot = 40;
        
        // Place the legs into the horse theme group
        TransformGroup legFLTG = Horse.leg(legWidth, legHeight, legDepth, -legX, -legY, legZ, -legRot);
        horseTG.addChild(legFLTG);
        TransformGroup legFRTG = Horse.leg(legWidth, legHeight, legDepth, -legX, -legY, -legZ, -legRot);
        horseTG.addChild(legFRTG);
        TransformGroup legBLTG = Horse.leg(legWidth, legHeight, legDepth, legX, -legY, legZ, legRot);
        horseTG.addChild(legBLTG);
        TransformGroup legBRTG = Horse.leg(legWidth, legHeight, legDepth, legX, -legY, -legZ, legRot);
        horseTG.addChild(legBRTG);
        
        TransformGroup tail = Horse.tail(0.015f, 0.1f, 0.015f, 0.39f, 0.11f, 0, 80);
        horseTG.addChild(tail);
        
        TransformGroup neck = Horse.neck(0.04f, 0.1f, 0.04f, -0.35f, 0.13f, 0, -125);
        horseTG.addChild(neck);
        
        TransformGroup head = Horse.head(0.06f, 0.09f, 0.06f, -0.45f, 0.16f, 0, -35);
        horseTG.addChild(head);        
        return horseTG;
        
    }
    
    
    // Added methods that appear to be just duplicates, but by declaring the methods seperate for head, neck etc. i can change the implementation of the 
    // at a later date with minimal impact on the rest of the code, For the purpose of this exercise, I am just exploting the static leg method.
    
    static TransformGroup head(float width, float height, float depth, float xpos, float ypos, float zpos, int rotZDegrees){
        return Horse.leg(width, height, depth, xpos, ypos, zpos, rotZDegrees);
    }
    
    public static TransformGroup neck(float width, float height, float depth, float xpos, float ypos, float zpos, int rotZDegrees){
        return Horse.leg(width, height, depth, xpos, ypos, zpos, rotZDegrees);
    }
    
    public static TransformGroup tail(float width, float height, float depth, float xpos, float ypos, float zpos, int rotZDegrees){
        return Horse.leg(width, height, depth, xpos, ypos, zpos, rotZDegrees);
    }
    
    
    public static TransformGroup leg(float width, float height, float depth, float xpos, float ypos, float zpos, int rotZDegrees){
        
        TransformGroup legTG = new TransformGroup();

        Box leg = new Box(width, height, depth, new Appearance());
        leg.setAppearance(Horse.horseColor());
        legTG.addChild(leg);
        Transform3D legT3D = new Transform3D();
        Vector3f legTranslate = new Vector3f(xpos, ypos, zpos);
        legT3D.rotZ(Math.toRadians(rotZDegrees));

        legT3D.setTranslation(legTranslate);
        legTG.setTransform(legT3D);
        
        return legTG;   
    }
    
     public static Appearance horseColor(){
    
        // Add a floor
        Material mat = new Material();
        mat.setAmbientColor(new Color3f( 194.0f / 255.0f, 178.0f/ 255.0f, 128.0f/ 255.0f));
        mat.setDiffuseColor(new Color3f( 194.0f / 255.0f, 178.0f/ 255.0f, 128.0f/ 255.0f));
        mat.setSpecularColor(new Color3f( 194.0f / 255.0f, 178.0f/ 255.0f, 128.0f/ 255.0f));
        
        
        Appearance app = new Appearance();
        app.setMaterial(mat);
        return app;
        
    }
    
}
