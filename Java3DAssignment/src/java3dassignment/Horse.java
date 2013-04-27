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
public class Horse {
    
    
    public static TransformGroup createHorse( ){

        
        // Create Horse
        TransformGroup horseTG = new TransformGroup();

        Box body = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        body.setAppearance(Horse.horseColor());
        horseTG.addChild(body);
        
        Box legFL = new Box(0.02f, 0.5f, 0.02f, new Appearance());
        legFL.setAppearance(Horse.horseColor());
        horseTG.addChild(legFL);
        
        
        //Box legRR = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box legBL = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box legBR = new Box(0.3f, 0s.15f, 0.1f, new Appearance());
        // Box neck = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box head = new Box(0.3f, 0.15f, 0.1f, new Appearance());
  
        
                

        
        
        return horseTG;
        
    }
    
    
     public static Appearance horseColor(){
    

        // Add a floor
        Material grassMaterial = new Material();
        grassMaterial.setAmbientColor(new Color3f( 200.0f / 255.0f, 200.0f/ 255.0f, 200.0f/ 255.0f));
        grassMaterial.setDiffuseColor(new Color3f( 200.0f / 255.0f, 200.0f/ 255.0f, 200.0f/ 255.0f));
        grassMaterial.setSpecularColor(new Color3f( 200.0f / 255.0f, 200.0f/ 255.0f, 200.0f/ 255.0f));
        
        
        Appearance floorAppearance = new Appearance();
        floorAppearance.setMaterial(grassMaterial);
        return floorAppearance;
        
    }
    
}
