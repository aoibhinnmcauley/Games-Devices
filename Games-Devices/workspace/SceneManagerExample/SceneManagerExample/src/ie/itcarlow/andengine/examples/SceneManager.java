package ie.itcarlow.andengine.examples;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

public class SceneManager {
	private AllScenes mCurrentScene;
	private BaseGameActivity mActivity;
	private Engine mEngine;
	private Camera mCamera;
	private BitmapTextureAtlas mSplashTA;
	private BitmapTextureAtlas mMenuTA;	
	private ITextureRegion mSplashTR;
	private ITextureRegion mMenuOneTR;
	private ITextureRegion mMenuTwoTR;
	private Scene mSplashScene, mGameScene, mMenuScene;
	
	// Game scene related fields.
	private BitmapTextureAtlas mTexture;
	private Text mText;
	private Font mFont;

	public enum AllScenes {
		SPLASH, MENU, GAME
	}

	public SceneManager(BaseGameActivity pAct, Engine pEng, Camera pCam) {
		// TODO Auto-generated constructor stub
		this.mActivity = pAct;
		this.mEngine = pEng;
		this.mCamera = pCam;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
	}

	public void loadSplashResources() {		
		mSplashTA = new BitmapTextureAtlas(this.mActivity.getTextureManager(),
				512, 256);
		mSplashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSplashTA, this.mActivity, "Splash.jpg", 0, 0);
		mSplashTA.load();
	}

	public void loadGameResources() {
		// Load font resources etc. here
		// Setup texture for the font.
		mTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);
		mTexture.load();
		mFont = new Font(mEngine.getFontManager(), mTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.CYAN);			
		this.mEngine.getFontManager().loadFont(this.mFont);       
	}

	public void loadMenuResources() {
		mMenuTA = new BitmapTextureAtlas(this.mActivity.getTextureManager(),
				256, 256);
		mMenuOneTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, this.mActivity, "One.jpg", 0, 0);
		mMenuTA.load();
		
		mMenuTA = new BitmapTextureAtlas(this.mActivity.getTextureManager(),
				256, 256);
		mMenuTwoTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, this.mActivity, "Two.jpg", 0, 0);
		mMenuTA.load();		
	}

	public Scene createSplashScene() {
		mSplashScene = new Scene();
		mSplashScene.setBackground(new Background(0, 0, 0));

		Sprite icon = new Sprite(0, 0, mSplashTR, mEngine.getVertexBufferObjectManager());
		icon.setPosition((mCamera.getWidth() - icon.getWidth()) / 2, (mCamera.getHeight() - icon.getHeight()) / 2);
		mSplashScene.attachChild(icon);
		return mSplashScene;
	}

	public Scene createMenuScene() {
		mMenuScene = new Scene();
		mMenuScene.setBackground(new Background(0, 0, 0));

		Sprite firstItem = new Sprite(0, 0, mMenuOneTR, mEngine.getVertexBufferObjectManager()) {			
			@Override
    		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
				setGameScene("First choice");		
				return true;
			}				
		};
										
		firstItem.setPosition((mCamera.getWidth() - firstItem.getWidth()) / 2,
				(mCamera.getHeight() - firstItem.getHeight()) / 2);
		
		mMenuScene.attachChild(firstItem);
		
		Sprite secondItem = new Sprite(0, 0, mMenuTwoTR, mEngine.getVertexBufferObjectManager()) {			
			@Override
    		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				setGameScene("Second choice");			
				return true;
			}				
		};
				
		secondItem.setPosition((mCamera.getWidth() - secondItem.getWidth()) / 2, firstItem.getY() + firstItem.getHeight() + 10  );
		
		mMenuScene.attachChild(secondItem);
		
		mMenuScene.registerTouchArea(firstItem);
		mMenuScene.registerTouchArea(secondItem);
		
		return mMenuScene;

	}

	public Scene createGameScene(String pChoice) {
		mGameScene = new Scene();
		mGameScene.setBackground(new Background(0, 0, 0));
		
		mText = new Text(0, 0, mFont, "", 20, mEngine.getVertexBufferObjectManager());
		mText.setPosition(100, 100);
		mGameScene.attachChild(mText);
				
		mText.setText(pChoice);
		
		return mGameScene;
	}

	public AllScenes getCurrentScene() {
		return mCurrentScene;
	}

	public void setCurrentScene(AllScenes pCurrentScene) {
		this.mCurrentScene = pCurrentScene;
		switch (pCurrentScene) {
		case SPLASH:
			break;
		case MENU:
			mEngine.setScene(mMenuScene);
			break;
		case GAME:
			mEngine.setScene(mGameScene);
			break;

		default:
			break;
		}
	}
	
	private void setGameScene(String pChoice) {
		createGameScene(pChoice);
		setCurrentScene(AllScenes.GAME);
	}

}