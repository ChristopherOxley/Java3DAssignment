/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dassignment;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

/**
 *
 * @author chrisoxley
 */
public class Carousel {


    public static TransformGroup createCarousel() {

        // Create Carousel
        TransformGroup carouselTG = new TransformGroup();



        // Top of carousel
        float radius = 2.0f;
        float height = 1.0f;
        int primFlags = Primitive.ENABLE_APPEARANCE_MODIFY;
        int xDivision = 10;
        int yDivision = 10;

        
        Appearance topApp = new Appearance();

        //topApp.setColoringAttributes (new ColoringAttributes (new Color3f (0.0f, 0.0f, 1.0f),1));


        Material mat = new Material();
        mat.setAmbientColor(new Color3f( 255.0f / 255.0f, 215.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setDiffuseColor(new Color3f( 255.0f / 255.0f, 225.0f/ 255.0f, 0.0f/ 255.0f));
        mat.setSpecularColor(new Color3f( 255.0f / 255.0f, 195.0f/ 255.0f, 0.0f/ 255.0f));

        topApp.setMaterial(mat);


        Cylinder top = new Cylinder();
        top.setAppearance(topApp);

        carouselTG.addChild(top);
        
        return carouselTG;
        

    }
}
