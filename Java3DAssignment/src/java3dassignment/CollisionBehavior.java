package java3dassignment;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Node;
import javax.media.j3d.WakeupOnCollisionEntry;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chrisoxley
 */
public class CollisionBehavior extends Behavior {

    private Node shape;
    int count = 0;
    private WakeupOnCollisionEntry wEnter;

    public CollisionBehavior(Node s) {
        shape = s;



    }

    public void initialize() {
        wEnter = new WakeupOnCollisionEntry(shape);
        wakeupOn(wEnter);
    }

    public void processStimulus(Enumeration criteria) {


        count++;

        // When the ball passes through the carousel, alternatively play audio

        if (count % 2 == 0) {
            
	try{
	    AudioClip ac = Applet.newAudioClip(new URL(
				     "file:/Users/chrisoxley/NetBeansProjects/Java3DAssignment/Java3DAssignment/src/java3dassignment/vehicle041.wav"));
	    ac.play();
	} catch(Exception e){}

        } else {


	try{
	    AudioClip ac = Applet.newAudioClip(new URL(
				     "file:/Users/chrisoxley/NetBeansProjects/Java3DAssignment/Java3DAssignment/src/java3dassignment/bottle-open.wav"));
	    ac.play();
	} catch(Exception e){}

        }


        


        System.out.println("hit");
        System.out.println("count: " + count);


        wakeupOn(wEnter);


    }
}
