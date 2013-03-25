import java.applet.Applet;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.io.*;
import java.awt.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.keyboard.*; 
import javax.swing.JApplet;
import com.sun.j3d.utils.image.TextureLoader;
import java.net.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.*;



class ThemePark extends JApplet{
	
	
	
		// Set the bounding sphere for the application
	    static public final BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);



		// My Picture
	    public TransformGroup createPicture() {
		
			// Create a main transofrm group
			TransformGroup mainTG = new TransformGroup();           
		
			// Temp color cube
			//ColorCube c = new ColorCube(.05f);
		//	mainTG.addChild(c);
			
			
			// Create Carousel
			
			// Top of carousel
			float radius = 2.0f;
			float height = 1.0f;
			int primFlags = Primitive.ENABLE_APPEARANCE_MODIFY;
			int xDivision = 10;
			int yDivision = 10;
			
			Appearance topApp = new Appearance();
			
			//topApp.setColoringAttributes (new ColoringAttributes (new Color3f (0.0f, 0.0f, 1.0f),1));

			
			Material mat = new Material();
			mat.setAmbientColor(new Color3f(0.0f,0.0f,1.0f));
			mat.setDiffuseColor(new Color3f(0.0f,0.0f,0.7f));
			mat.setSpecularColor(new Color3f(0.0f,0.0f,0.7f));
			
			topApp.setMaterial(mat);

			
			Cylinder top = new Cylinder(radius, height, primFlags, xDivision, yDivision, topApp);
			top.setAppearance(topApp);
			
			mainTG.addChild(top);
			
			return mainTG;		
		}
		
		
	    public BranchGroup createSceneGraph(SimpleUniverse su) {
		
			// creating the universe
			BranchGroup objRoot = new BranchGroup();

			// Set up the global lights
			Color3f lColor1 = new Color3f(0.7f, 0.7f, 0.7f);
			Vector3f lDir1  = new Vector3f(-1.0f, -1.0f, -1.0f);
			Color3f alColor = new Color3f(1.0f, 1.0f, 1.0f);

			
			AmbientLight aLgt = new AmbientLight(alColor);
			aLgt.setInfluencingBounds(bounds);
			DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
			lgt1.setInfluencingBounds(bounds);
			objRoot.addChild(aLgt);
			objRoot.addChild(lgt1);
			
			// adding the TG of the picture to the universe
			objRoot.addChild(createPicture());

			// adding mouse and keyboard controls
			addControls(su,objRoot);

			objRoot.compile();
			
			return objRoot;	
		}


		// Standard Controls
		public void addControls(SimpleUniverse su, BranchGroup bg) {
			TransformGroup cameraTG = su.getViewingPlatform().
			                            getViewPlatformTransform();

			// starting postion of the viewing platform
			Vector3f translate = new Vector3f();
			Transform3D T3D = new Transform3D();
			translate.set( 0.0f, 0.3f, 0.0f);
		    T3D.setTranslation(translate);
			cameraTG.setTransform(T3D);

			// Create the key behavior node
		    KeyNavigatorBehavior keyBehavior = new 
			KeyNavigatorBehavior(cameraTG);
			keyBehavior.setSchedulingBounds(bounds);
		    bg.addChild(keyBehavior);
			
		 	// Create the rotate behavior node
		 	MouseRotate behavior = new MouseRotate(MouseBehavior.INVERT_INPUT);
		 	behavior.setTransformGroup(cameraTG);
		 	behavior.setSchedulingBounds(bounds);
		 	bg.addChild(behavior);// 

			// Create the zoom behavior node
		 	MouseZoom behavior2 = new MouseZoom(MouseBehavior.INVERT_INPUT);
		 	behavior2.setTransformGroup(cameraTG);
		 	behavior2.setSchedulingBounds(bounds);
			bg.addChild(behavior2);
		 
			
		 	// Create the translate behavior node
		 	 MouseTranslate behavior3 = new MouseTranslate(MouseBehavior.INVERT_INPUT);
		 	behavior3.setTransformGroup(cameraTG);
		 	behavior3.setSchedulingBounds(bounds);
		 	bg.addChild(behavior3);	
		}

	ThemePark(){
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		template.setSceneAntialiasing(GraphicsConfigTemplate.REQUIRED);
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().
		getDefaultScreenDevice().getBestConfiguration(template);		
		Canvas3D c = new Canvas3D(config, false);
		cp.add("Center", c);
		SimpleUniverse u = new SimpleUniverse(c);
		BranchGroup scene = createSceneGraph(u);
		u.addBranchGraph(scene);
	}


}