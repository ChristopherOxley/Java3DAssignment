/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dassignment;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.Appearance;
import javax.media.j3d.TransformGroup;

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
        
        
        return horseTG;
        
    }
    
    
}
