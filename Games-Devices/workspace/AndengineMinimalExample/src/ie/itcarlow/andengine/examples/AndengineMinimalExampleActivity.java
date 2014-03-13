package ie.itcarlow.andengine.examples;

import ie.itcarlow.andengine.examples.SceneManager.AllScenes;
import android.graphics.Camera;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//import org.andengine.engine.handler.physics.PhysicsHandler;
//import org.andengine.entity.shape.IShape;
//import org.andengine.engine.handler.collision.CollisionHandler;
//import org.andengine.engine.handler.collision.ICollisionCallback;
//mport org.andengine.engine.handler.physics.PhysicsHandler;

public class AndengineMinimalExampleActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final int CAMERA_WIDTH = 500;
	private static final int CAMERA_HEIGHT = 500;
	//private PhysicsHandler mPhysicsHandler;
	// ===========================================================
	// Fields
	// ===========================================================
	
	private Scene mScene;
	

	private SceneManager mSceneManager;
	private Camera mCamera;
	//boolean colliding = false; 
	//MediaPlayer sound = MediaPlayer.create(this, .raw.sound.wav);
	@Override
	public EngineOptions onCreateEngineOptions() {
    mCamera = new Camera(0, 0, CAMERA_WIDTH,CAMERA_HEIGHT);
	final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    engineOptions.getAudioOptions().setNeedsSound(true);
      return engineOptions;
	}
	
	@Override
	public void onCreateResources(
	OnCreateResourcesCallback pOnCreateResourcesCallback)
	throws Exception {
		mSceneManager = new SceneManager(this, mEngine, mCamera);
		mSceneManager.loadSplashResources();	
	loadGfx(); 
	  SoundFactory.setAssetBasePath("sfx/");
	     
    
	pOnCreateResourcesCallback.onCreateResourcesFinished();
	
	}
	private void loadGfx() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	// width and height power of 2^x
	

	}

	
	@Override
	public void onCreateScene(OnCreateSceneCallback
	pOnCreateSceneCallback)
	throws Exception {
	this.mScene = new Scene();
	this.mScene.setBackground(new Background(0, 125, 58));
	
    // mSound = SoundFactory.createSoundFromAsset(getSoundManager(), this, "sound.wav");
	//pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);
	pOnCreateSceneCallback.onCreateSceneFinished(mSceneManager.createSplashScene());
	
	}

	
	
	@Override
	public void onPopulateScene(Scene pScene,
	OnPopulateSceneCallback pOnPopulateSceneCallback)
	throws Exception {
	// Setup coordinates for the face, so its centered on the camera.
		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
					//	 TODO Auto-generated method stub
				mSceneManager.loadMenuResources();
						mSceneManager.loadGameResources();
						mSceneManager.createMenuScene();
						mSceneManager.setCurrentScene(AllScenes.MENU);
					}
				}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
}
	
	
	
  }

	
	    
	 
	
	
	
	            

	



