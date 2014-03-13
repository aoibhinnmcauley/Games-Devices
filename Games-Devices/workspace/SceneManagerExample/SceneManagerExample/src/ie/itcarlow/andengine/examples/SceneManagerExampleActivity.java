package ie.itcarlow.andengine.examples;

import ie.itcarlow.andengine.examples.SceneManager.AllScenes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;


public class SceneManagerExampleActivity extends BaseGameActivity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	// ===========================================================
	// Fields
	// ===========================================================
		
	private SceneManager mSceneManager;
	private Camera mCamera;

	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}
	
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		mSceneManager = new SceneManager(this, mEngine, mCamera);
		mSceneManager.loadSplashResources();		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
		
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(mSceneManager.createSplashScene());
		
	}
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						// TODO Auto-generated method stub
						mSceneManager.loadMenuResources();
						mSceneManager.loadGameResources();
						mSceneManager.createMenuScene();
						mSceneManager.setCurrentScene(AllScenes.MENU);
					}
				}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
}