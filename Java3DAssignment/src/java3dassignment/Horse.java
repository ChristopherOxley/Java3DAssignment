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

/**
 *
 * @author chrisoxley
 */
public class Horse {
    
    
    public static TransformGroup createHorse(){
        
        // Create Horse
        TransformGroup horseTG = new TransformGroup();

        Box body = new Box(0.3f, 0.15f, 0.1f, new Appearance());

        horseTG.addChild(body);
        
        Box legFL = new Box(0.02f, 0.5f, 0.02f, new Appearance());
        legFL.setAppearance(Horse.getWoodTexture());
        horseTG.addChild(legFL);
        
        
        //Box legRR = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box legBL = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box legBR = new Box(0.3f, 0s.15f, 0.1f, new Appearance());
        // Box neck = new Box(0.3f, 0.15f, 0.1f, new Appearance());
        // Box head = new Box(0.3f, 0.15f, 0.1f, new Appearance());
  
        return horseTG;
        
    }
    
    
     public static Appearance getWoodTexture(){
    
        Appearance app = new Appearance();

        // Set up the material
        Material mat = new Material();
        mat.setAmbientColor(new Color3f( 255.0f / 255.0f, 215.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setDiffuseColor(new Color3f( 255.0f / 255.0f, 225.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setSpecularColor(new Color3f( 255.0f / 255.0f, 195.0f/ 255.0f, 0.0f/ 255.0f));

        // Set up the texture
        TextureLoader loader = new TextureLoader("./src/java3dassignment/wood.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        
        
        // Update the appearance with the material and texture
        app.setMaterial(mat);
        app.setTexture(texture);
        app.setTextureAttributes(texAttr);
        
        return app;
        
    }
    
}
