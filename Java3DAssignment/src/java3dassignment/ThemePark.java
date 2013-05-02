package java3dassignment;

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
import java.util.Random;

class ThemePark extends JApplet {

    // Set the bounding sphere for the application
    static public final BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);

    // My Picture
    public TransformGroup createPicture() {

        // Create a main transofrm group
        TransformGroup mainTG = new TransformGroup();

        // Set the number of horses
        TransformGroup carousel = Carousel.createCarousel(18, bounds);


        // Add a floor
        Material grassMaterial = new Material();
        grassMaterial.setAmbientColor(new Color3f(0.0f / 255.0f, 200.0f / 255.0f, 0.0f / 255.0f));
        grassMaterial.setDiffuseColor(new Color3f(0.0f / 255.0f, 225.0f / 255.0f, 0.0f / 255.0f));
        grassMaterial.setSpecularColor(new Color3f(0.0f / 255.0f, 195.0f / 255.0f, 0.0f / 255.0f));

        TextureLoader loader = new TextureLoader("./src/java3dassignment/grass3.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance floorAppearance = new Appearance();
        floorAppearance.setMaterial(grassMaterial);
        floorAppearance.setTexture(texture);
        floorAppearance.setTextureAttributes(texAttr);




        Cylinder floor = new Cylinder(10, 0.0000f, Cylinder.GENERATE_NORMALS + Cylinder.GENERATE_TEXTURE_COORDS, floorAppearance);
        floor.setAppearance(floorAppearance);


        mainTG.addChild(floor);

        // Repeat every 10 seconds
        Transform3D rot3D = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.DECREASING_ENABLE,
                0, 0,
                0, 0, 0,
                10000, 0, 0);
        //t.rotX(2* Math.PI /4); 

        carousel.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, carousel, rot3D, 0.0f, (float) Math.PI * 2.0f);
        rotator.setSchedulingBounds(bounds);

        mainTG.addChild(rotator);
        // Return Transform Group
        mainTG.addChild(carousel);


        // create a ball for collision detection
        TransformGroup ballTG = new TransformGroup();
        Transform3D translateBall = new Transform3D();
        translateBall.setTranslation(new Vector3f(5.0f, 0.5f, 0.0f));
        Sphere ball = new Sphere(0.25f);
        ballTG.addChild(ball);
        mainTG.addChild(ballTG);
        ballTG.setTransform(translateBall);



        // Move the ball
        ballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


        Vector3f translateVeritcal = new Vector3f();
        Transform3D ballTG3D = new Transform3D();


        ballTG3D.setTranslation(translateVeritcal);
        ballTG.setTransform(ballTG3D);

        // add vertical motion trasform to the bl

        ballTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);




        Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0, 4000, 0, 0, 4000, 0, 0);
        PositionInterpolator pi = new PositionInterpolator(alpha, ballTG);
        ballTG3D.rotZ(Math.PI / 2.0f);

        Vector3f animateTrans = new Vector3f();
        Transform3D anim3D = new Transform3D();

        animateTrans.set(0, 10, 0);
        ballTG3D.setTranslation(animateTrans);
        ballTG.setTransform(anim3D);

        pi.setStartPosition(5);
        pi.setEndPosition(-1);
        pi.setTransformAxis(ballTG3D);


        pi.setSchedulingBounds(bounds);

        ballTG.addChild(pi);


        

        ball.setCapability(ball.ENABLE_APPEARANCE_MODIFY);

        
        // Add collision detection to the ball
        CollisionBehavior coll = new CollisionBehavior(ball);
        coll.setSchedulingBounds(bounds);
        mainTG.addChild(coll);




        return mainTG;
    }

    public BranchGroup createSceneGraph(SimpleUniverse su) {

        // creating the universe
        BranchGroup objRoot = new BranchGroup();

        // Set up the global lights
        Color3f lColor1 = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f lDir1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Color3f alColor = new Color3f(1.0f, 1.0f, 1.0f);


        AmbientLight aLgt = new AmbientLight(alColor);
        aLgt.setInfluencingBounds(bounds);
        DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
        lgt1.setInfluencingBounds(bounds);
        objRoot.addChild(aLgt);
        objRoot.addChild(lgt1);



        SpotLight spotLight = new SpotLight();
        spotLight.setEnable(true);
        spotLight.setSpreadAngle((float) Math.toRadians(45));
        spotLight.setColor(new Color3f(0.0f, 1.0f, 0.0f));
        spotLight.setPosition(new Point3f(0.0f, 1.0f, 3.0f));
        spotLight.setAttenuation(new Point3f(0.1f, 0.0f, 0.0f));
        spotLight.setDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        spotLight.setConcentration(50.0f);
        spotLight.setInfluencingBounds(bounds);
        objRoot.addChild(spotLight);







        // adding the TG of the picture to the universe
        objRoot.addChild(createPicture());

        // adding mouse and keyboard controls
        addControls(su, objRoot);

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
        translate.set(0.0f, 0.3f, 0.0f);
        T3D.setTranslation(translate);
        cameraTG.setTransform(T3D);

        // Create the key behavior node
        KeyNavigatorBehavior keyBehavior = new KeyNavigatorBehavior(cameraTG);
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

    ThemePark() {
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