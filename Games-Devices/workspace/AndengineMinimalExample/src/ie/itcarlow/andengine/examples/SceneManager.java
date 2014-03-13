package ie.itcarlow.andengine.examples;


import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class SceneManager {
	private static final int CAMERA_WIDTH = 500;
	private static final int CAMERA_HEIGHT = 500;
	private AllScenes mCurrentScene;
	private BaseGameActivity mActivity;
	private Engine mEngine;
	private Camera mCamera;
	private BitmapTextureAtlas mBackground;
	private BitmapTextureAtlas mSplashTA;
	private BitmapTextureAtlas mMenuTA;
	private ITextureRegion mbackgroundTR;
	private ITextureRegion mSplashTR;
	private ITextureRegion mMenuOneTR;
	private ITextureRegion mMenuTwoTR;
	private Scene mSplashScene, mGameScene, mMenuScene;
	private ITiledTextureRegion mFaceTextureRegion;
	private ITiledTextureRegion mBasketTextureRegion;
	// Game scene related fields.
	private BitmapTextureAtlas mTexture;
	private BitmapTextureAtlas mBasketTexture;
	AnimatedSprite face;
	private Text mText;
	private Font mFont;
	private Sound mSound; 	
	PhysicsWorld mPhysicsWorld;
	Body faceBody;
	Body CollisionBody;
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
		mBackground = new BitmapTextureAtlas(this.mActivity.getTextureManager(),
				879, 500);
		mbackgroundTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBackground, this.mActivity, "forest.jpg", 0, 0);
		
		mSplashTA = new BitmapTextureAtlas(this.mActivity.getTextureManager(),
				400, 498);
		mSplashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSplashTA, this.mActivity, "fruit2.jpg", 0, 0);
		mTexture = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 285, 56);
		mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mTexture, this.mActivity.getAssets(), "dancingbanana.png", 0, 0,8, 1 );
		mTexture.load();
		mSplashTA.load();
		mBackground.load();
	  
		
	}

	public void loadGameResources() {
		// Load font resources etc. here
		// Setup texture for the font.
		try
		{
			  this.mSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this.mActivity, "buzzer.mp3");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		mTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);
		mTexture.load();
		mFont = new Font(mEngine.getFontManager(), mTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);			
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
		mMenuScene.setBackground(new Background(225, 59, 0));

		Sprite firstItem = new Sprite(0, 0, mMenuOneTR, mEngine.getVertexBufferObjectManager()) {			
			@Override
    		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {				
				setGameScene("Play");		
				return true;
			}				
		};
										
		firstItem.setPosition((mCamera.getWidth() - firstItem.getWidth()) / 2,
			(mCamera.getHeight() - firstItem.getHeight()) / 2);
		
		mMenuScene.attachChild(firstItem);
		
		//Sprite secondItem = new Sprite(0, 0, mMenuTwoTR, mEngine.getVertexBufferObjectManager()) {			
	//		@Override
    	//public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					
			//	return true;
			//}				
	  //   };
				
	//	secondItem.setPosition((mCamera.getWidth() - secondItem.getWidth()) / 2, firstItem.getY() + firstItem.getHeight() + 10  );
		
		//mMenuScene.attachChild(secondItem);
		mMenuScene.registerTouchArea(firstItem);
		//mMenuScene.registerTouchArea(secondItem);
		
		return mMenuScene;

	}

	public Scene createGameScene(String pChoice) {
		mGameScene = new Scene();
		mGameScene.setBackground(new Background(0,0,0));
		
		final Vector2 v = Vector2Pool.obtain(0, 0);
		  
		// Establish world with no gravity, second param 
		// (false) means PhysicsWorld cannot sleep
		mPhysicsWorld = new PhysicsWorld(v, false); 
		Vector2Pool.recycle(v);
		mGameScene.registerUpdateHandler(mPhysicsWorld);
		
		
		final float centerX =
				(CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
				final float centerY =
				(CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
				
				Sprite background = new Sprite(0,0,mbackgroundTR, this.mEngine.getVertexBufferObjectManager());
				
				// Create the face and add it to the scene	
			     Sprite collisionFace = new Sprite(centerX + 50, centerY,mFaceTextureRegion, this.mEngine.getVertexBufferObjectManager());	
				face = new AnimatedSprite(centerX, centerY,mFaceTextureRegion, this.mEngine.getVertexBufferObjectManager() ) 
				{
			        @Override
			        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, 
			        final float pTouchAreaLocalY) 
			        {
			        	if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE)
			        	{	
			        		//this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			        		//this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			        		//return false;
			        		final Body body = (Body)this.getUserData();
			        		final float angle = body.getAngle(); // keeps the body angle
			        		final Vector2 v2 = Vector2Pool.obtain((pSceneTouchEvent.getX() - this.getWidth() / 2) /PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
			        	    (pSceneTouchEvent.getY() - this.getHeight() / 2) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			        		body.setTransform(v2, angle);
			        		Vector2Pool.recycle(v2); 
			        		
			        	}
			        	return true;        // here you can use the code	
			        }
				
			    };
			        
			    final FixtureDef PLAYER_FIX = PhysicsFactory.createFixtureDef(1.5f,0.45f, 0.3f);	
			    faceBody = PhysicsFactory.createBoxBody(mPhysicsWorld, face, BodyType.DynamicBody, PLAYER_FIX);
			    CollisionBody = PhysicsFactory.createBoxBody(mPhysicsWorld, collisionFace, BodyType.DynamicBody, PLAYER_FIX);
				mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(face, faceBody, true, true));
				mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(collisionFace, CollisionBody, true, true));
				Vector2 velocity = Vector2Pool.obtain(2, 0);
				CollisionBody.setLinearVelocity(velocity);	
				Vector2Pool.recycle(velocity);
				face.setUserData(faceBody); 
				collisionFace.setUserData(faceBody);
				CollisionBody.setLinearDamping(1f);
				mGameScene.attachChild(background);
				mGameScene.attachChild(collisionFace);
				mGameScene.attachChild(face);
				mGameScene.registerTouchArea(face);
				mGameScene.setTouchAreaBindingOnActionDownEnabled(true);
				face.animate(100, true);  
				//CollisionHandler handler = new CollisionHandler(new CollisionManager(), face, collisionFace);	    
				//mEngine.registerUpdateHandler(handler);	    
//				Create a physics handler so we can apply a velocity along the x and y axis for one sprite.
				/*mPhysicsHandler = new PhysicsHandler(collisionFace);
				this.mPhysicsHandler.setVelocityX(3.0f);
				this.mPhysicsHandler.setVelocityY(3.0f);
				mEngine.registerUpdateHandler(mPhysicsHandler);*/
				//mScene.setTouchAreaBindingEnabled(true);
//				mPhysicsWorld.setContactListener(contact);
				mPhysicsWorld.setContactListener(contact);
				mGameScene.registerUpdateHandler(mPhysicsWorld);
		
		
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
	
	final ContactListener contact = new ContactListener() {
		 
 		@Override
 		public void postSolve(Contact contact, ContactImpulse impulse)
 		{
          
 		}
                @Override
               public void beginContact(Contact contact)
                {
                	mSound.play();
                	face.setColor(Color.PINK);

                }

               @Override
                public void endContact(Contact contact)
                {
                       
                }

                @Override
                public void preSolve(Contact contact, Manifold oldManifold)
                {
                       
                }

              
  };

}