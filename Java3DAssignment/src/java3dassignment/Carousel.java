/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dassignment;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author chrisoxley
 */
public class Carousel {

    public static TransformGroup createCarousel(int numberOfHorses,Bounds bounds) {

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
        translate.set(0.0f, 2.0f, 0.0f);
        T3D.setTranslation(translate);
        tg_top.setTransform(T3D);


        // Create the poles & horses
        TransformGroup polesTG = new TransformGroup();


        for (int i = 1; i <= numberOfHorses; i++) {

            // Create a thin and tall pole
            TransformGroup poleTG = new TransformGroup();
            Cylinder pole = createDisc(0.02f, 2);
            poleTG.addChild(pole);
            polesTG.addChild(poleTG);
            
                        // Create and add a horse to the pole
            
            TransformGroup horseTG =  Horse.createHorse();
            poleTG.addChild(horseTG);

      
            

            // Every other horse in stepped inwards towards the center
            float radius;
            if (i % 2 == 0) {
                radius = 1.4f;
            } else {
                radius = 1.9f;
            }

            
            // Evenly space the poles out by.
            Point2D p = Carousel.calculateNewPoint((float) Math.toRadians(360 / numberOfHorses * i), new Point2D.Float(radius, 0f));




            // Set the new point relative to the center of the carousel & raise
            // the pole up by half it's height
            
            Vector3f translateOut = new Vector3f();
            Transform3D polesT3D = new Transform3D();
            translateOut.set((float) p.getX(), 1.0f, (float) p.getY());
            
            // Since the tangent of a circle intersects at 90 degrees.
            // We have calculated the angle around the circle.
            // We know the inside angles of a circle  = 180
            // we get 180-90-angle of rotation
            // simlpifies to 90 - angle of rotation.
            
            // Using this angle we can rotate the horses so that they face the correct 
            //direction.
            polesT3D.rotY( Math.toRadians(90 - (360 / numberOfHorses * i)));
            polesT3D.setTranslation(translateOut);
            poleTG.setTransform(polesT3D);



            //TODO: move the horse down to a random position
            
            Vector3f translateVeritcal = new Vector3f();
            Transform3D horseTG3D = new Transform3D();
            
            // Random number between -0.5 and 0.5
            Random randomNumber = new Random();
            float rn = randomNumber.nextFloat()  - 0.5f;
            
            translateVeritcal.set(0, rn, 0);
            horseTG3D.setTranslation(translateVeritcal);
            horseTG.setTransform(horseTG3D);
            
            // add vertical motion trasforms

            horseTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            
            

            
            Alpha alpha = new Alpha(-1,  Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE , 0, 0,4000, 0, 0, 4000, 0, 0);
            PositionInterpolator pi = new PositionInterpolator(alpha, horseTG);
            horseTG3D.rotZ(Math.PI/2.0f);
            
             Vector3f animateTrans = new Vector3f();
            Transform3D anim3D = new Transform3D();
            
            animateTrans.set(0, rn, 0);
            horseTG3D.setTranslation(animateTrans);
            horseTG.setTransform(anim3D);
            
            pi.setStartPosition(rn);
            pi.setEndPosition(-rn);
           pi.setTransformAxis(horseTG3D);
                   
                
           pi.setSchedulingBounds(bounds);
            
            horseTG.addChild(pi);
        }

        carouselTG.addChild(polesTG);

        
        TransformGroup capTG = Carousel.carouselCap(1.5f, 1.5f, 6);
        carouselTG.addChild(capTG);
        Vector3f translateUp = new Vector3f();
        Transform3D capT3D = new Transform3D();
        translateUp.set(0.0f, 2.2f,0.0f);
  
        capT3D.setTranslation(translateUp);
        capTG.setTransform(capT3D);
        
        return carouselTG;
    }

    
    // Conveniance method for creating a cylinder
    private static Cylinder createDisc(float radius, float height) {

        // Make sure the cylinder is smooth by increasing the number of faces.
        int xDivision = 10000;
        int yDivision = 1;


        Appearance topApp = new Appearance();

        // Set up the material
        Material mat = new Material();
        mat.setAmbientColor(new Color3f(255.0f / 255.0f, 215.0f / 255.0f, 0.0f / 255.0f));
        mat.setDiffuseColor(new Color3f(255.0f / 255.0f, 225.0f / 255.0f, 0.0f / 255.0f));
        mat.setSpecularColor(new Color3f(255.0f / 255.0f, 195.0f / 255.0f, 0.0f / 255.0f));

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
        Cylinder cyl = new Cylinder(radius, height, Cylinder.GENERATE_NORMALS + Cylinder.GENERATE_TEXTURE_COORDS, xDivision, yDivision, topApp);

        return cyl;

    }

    // Used to calculate the new position for a given angle around the origin.
    public static Point2D calculateNewPoint(float angle, Point2D p) {

        //calculate new positions by "rotating" the point
        double newX = p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle);
        double newY = p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle);
        p.setLocation(newX, newY);

        return p;
    }
    
    //CUSTOM SHAPE Method to create a pyramid (increase sides to create a cone.)
    public static TransformGroup carouselCap(float radius, float height, int sides){
        
        
        TransformGroup capTG = new TransformGroup();


        
        // size = number of sides to the cap * number of verticies per side * 2
        Point3f[] vertices = new Point3f[sides * 3 * 2];
        
        for(int i = 0; i < vertices.length/3/2; i++){
            
             float angle1 = (float)Math.toRadians(360.0f / sides * i) ;
             float angle2 = (float)Math.toRadians(360.0f / sides * (i+1));

            // Base Triangles
            
            Point2D base2d1 = Carousel.calculateNewPoint(angle1, new Point2D.Float(radius, 0f));
            Point2D base2d2 = Carousel.calculateNewPoint(angle2, new Point2D.Float(radius, 0f));
            
            Point3f base3f1 = new Point3f(0.0f, 0.0f, 0.0f);
            Point3f base3f2 = new Point3f((float)base2d1.getX(),0.0f, (float)base2d1.getY());
            Point3f base3f3 = new Point3f((float)base2d2.getX(),0.0f, (float)base2d2.getY());
            
            // Order important to ensure visability
            vertices[i * 3 * 2 + 0] = base3f1;
            vertices[i * 3 * 2 + 1] = base3f2;
            vertices[i * 3 * 2 + 2] = base3f3;

            
            // Taper Triangles
            
            Point3f taper3f1 = new Point3f(0.0f, height, 0.0f);
            Point3f taper3f2 = new Point3f((float)base2d1.getX(),0.0f, (float)base2d1.getY());
            Point3f taper3f3 = new Point3f((float)base2d2.getX(),0.0f, (float)base2d2.getY());
            
            // Order important to ensure visability
            vertices[i * 3 * 2 + 3] = taper3f3;
            vertices[i * 3 * 2 + 4] = taper3f2;
            vertices[i * 3 * 2 + 5] = taper3f1;

            
            
        }
        
        
        TriangleArray array = new TriangleArray(vertices.length, TriangleArray.COORDINATES | TriangleArray.NORMALS | TriangleArray.TEXTURE_COORDINATE_2);
        array.setCoordinates(0, vertices);
        array.setTextureCoordinates(0, vertices);

        
          // Add a floor
        Material mat = new Material();
        mat.setAmbientColor(new Color3f(128.0f / 255.0f, 0.0f / 255.0f, 128.0f / 255.0f));
        mat.setDiffuseColor(new Color3f(150.0f / 255.0f, 0.0f / 255.0f, 50.0f / 255.0f));
        mat.setSpecularColor(new Color3f(90.0f / 255.0f, 0.0f / 255.0f, 90.0f / 255.0f));
        
        TextureLoader loader = new TextureLoader("./src/java3dassignment/MetallicPaint.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance floorAppearance = new Appearance();
        floorAppearance.setMaterial(mat);
        floorAppearance.setTexture(texture);
        floorAppearance.setTextureAttributes(texAttr);
        
        
        Shape3D myShape = new Shape3D(array, floorAppearance);

        capTG.addChild(myShape);

        
        return capTG;
    }
    
    
}
