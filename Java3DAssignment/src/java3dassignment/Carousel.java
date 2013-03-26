/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dassignment;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author chrisoxley
 */
public class Carousel {


    public static TransformGroup createCarousel() {

        // Create Carousel
        TransformGroup carouselTG = new TransformGroup();

        // Create the bottom of the Carousel
        TransformGroup tg_bottom = new TransformGroup();
        Cylinder platform = Carousel.createDisc(2, 0.3f);
        tg_bottom.addChild(platform);
        carouselTG.addChild(tg_bottom);
        
        // Create the top of the carousel
        TransformGroup tg_top = new TransformGroup();
        Cylinder roof = Carousel.createDisc(2, 0.5f);
        tg_top.addChild(roof);
        carouselTG.addChild(tg_top);

        
        // Move the top up
        Vector3f translate = new Vector3f();
	Transform3D T3D = new Transform3D();
	translate.set( 0.0f, 2.0f, 0.0f);
        T3D.setTranslation(translate);
	tg_top.setTransform(T3D);
        
        
        // Create the poles & horses
        TransformGroup polesTG = new TransformGroup();
       
        // Dynamically set the number of horses required on the carousel
        int numberOfHorses = 20;
        
        for(int i=1; i <= numberOfHorses; i++){
            
            // Create a thin and tall pole
            TransformGroup poleTG = new TransformGroup();
            Cylinder pole = createDisc(0.02f, 2);
            poleTG.addChild(pole);
            polesTG.addChild(poleTG);
            
            
            // Every other horse in stepped inwards towards the center
            float radius;
            if(i%2==0){
                radius = 1.4f;
            }else{
                radius = 1.7f;
            }
            
            // Evenly space the poles out by.
            Point2D p = Carousel.calculateNewPoint((float)Math.toRadians(360/numberOfHorses * i), new Point2D.Float(radius, 0f));
            
            // Set the new point relative to the center of the carousel & raise
            // the pole up by half it's height
            Vector3f translateOut = new Vector3f();
            Transform3D polesT3D = new Transform3D();
            translateOut.set( (float)p.getX(), 1.0f, (float)p.getY());
            polesT3D.setTranslation(translateOut);
            poleTG.setTransform(polesT3D);
      
            // Create and add a horse to the pole
            poleTG.addChild(Horse.createHorse());
            
        }
        
        carouselTG.addChild(polesTG);
        
        return carouselTG;
    }
    
    
    
    private static Cylinder createDisc(float radius, float height){
        
        // Make sure the cylinder is smooth by increasing the number of faces.
        int xDivision = 10000;
        int yDivision = 1;

        
        Appearance topApp = new Appearance();

        // Set up the material
        Material mat = new Material();
        mat.setAmbientColor(new Color3f( 255.0f / 255.0f, 215.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setDiffuseColor(new Color3f( 255.0f / 255.0f, 225.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setSpecularColor(new Color3f( 255.0f / 255.0f, 195.0f/ 255.0f, 0.0f/ 255.0f));

        // Set up the texture
        TextureLoader loader = new TextureLoader("./src/java3dassignment/MetallicPaint.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        
        
        // Update the appearance with the material and texture
        topApp.setMaterial(mat);
        topApp.setTexture(texture);
        topApp.setTextureAttributes(texAttr);
        
        // Create a cylinder with the specified attributes.
        Cylinder top = new Cylinder(radius, height, Cylinder.GENERATE_NORMALS+ Cylinder.GENERATE_TEXTURE_COORDS, xDivision, yDivision, topApp);

        return top;
        
    }
    
    
    public static Point2D calculateNewPoint(float angle, Point2D p){
        
        //calculate new positions by "rotating" the point
        double newX = p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle);
        double newY = p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle);

        p.setLocation(newX, newY);
        return p;
    }
    
    
    
    
}
