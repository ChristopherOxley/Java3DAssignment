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


        TransformGroup tg_bottom = new TransformGroup();
        Cylinder platform = Carousel.createDisc(2, 0.3f);
        tg_bottom.addChild(platform);
        carouselTG.addChild(tg_bottom);
        
        
        TransformGroup tg_top = new TransformGroup();
        Cylinder roof = Carousel.createDisc(2, 0.5f);
        tg_top.addChild(roof);
        carouselTG.addChild(tg_top);

        
        // Move top up
        
        
        Vector3f translate = new Vector3f();
	Transform3D T3D = new Transform3D();
	translate.set( 0.0f, 2.0f, 0.0f);
        T3D.setTranslation(translate);
	tg_top.setTransform(T3D);
        
        
        TransformGroup polesTG = new TransformGroup();
       
        int numberOfHorses = 16;
        
        for(int i=1; i <= numberOfHorses; i++){
            
            TransformGroup poleTG = new TransformGroup();
            Cylinder pole = createDisc(0.02f, 2);
            poleTG.addChild(pole);
            polesTG.addChild(poleTG);
            
            float radius;
            if(i%2==0){
                radius = 1.4f;
            }else{
                radius = 1.7f;
            }
            
            Point2D p = Carousel.calculateNewPoint(0, 0, (float)Math.toRadians(360/numberOfHorses * i), new Point2D.Float(radius, 0f));
            
            Vector3f translateOut = new Vector3f();
            Transform3D polesT3D = new Transform3D();
            translateOut.set( (float)p.getX(), 1.0f, (float)p.getY());
            polesT3D.setTranslation(translateOut);
            poleTG.setTransform(polesT3D);
            
            poleTG.addChild(Horse.createHorse());
            
        }
        
        carouselTG.addChild(polesTG);
        
        return carouselTG;
    }
    
    
    
    private static Cylinder createDisc(float radius, float height){
        
        
                // Top of carousel

        int xDivision = 10000;
        int yDivision = 1;

        
        Appearance topApp = new Appearance();

        //topApp.setColoringAttributes (new ColoringAttributes (new Color3f (0.0f, 0.0f, 1.0f),1));


        Material mat = new Material();
        mat.setAmbientColor(new Color3f( 255.0f / 255.0f, 215.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setDiffuseColor(new Color3f( 255.0f / 255.0f, 225.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setSpecularColor(new Color3f( 255.0f / 255.0f, 195.0f/ 255.0f, 0.0f/ 255.0f));

        
        TextureLoader loader = new TextureLoader("./src/java3dassignment/MetallicPaint.jpg", "LUMINANCE", new Container());

        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);

        texture.setBoundaryModeT(Texture.WRAP);

       // texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
        
        TextureAttributes texAttr = new TextureAttributes();

        texAttr.setTextureMode(TextureAttributes.MODULATE);
        
        
        
        topApp.setMaterial(mat);
        topApp.setTexture(texture);
        topApp.setTextureAttributes(texAttr);

      //  Cylinder top = new Cylinder();
        
        
        Cylinder top = new Cylinder(radius, height, Cylinder.GENERATE_NORMALS+ Cylinder.GENERATE_TEXTURE_COORDS, xDivision, yDivision, topApp);

        return top;
        
    }
    
    
    public static Point2D calculateNewPoint(float cx, float cy, float angle, Point2D p){
        
        
        try{
        
        double s = Math.sin(angle);
        double c = Math.cos(angle);

        // translate point back to origin:

        p.setLocation(p.getX() - cx, p.getY() - cy); 
        
  

        // rotate point
        double xnew = p.getX() * c - p.getY() * s;
        double ynew = p.getX() * s + p.getY() * c;

        // translate point back:
        p.setLocation(xnew, ynew);
        }catch(Exception e){
            
        }
        


        
        
        return p;
        
    }
    
    
    
    
}
