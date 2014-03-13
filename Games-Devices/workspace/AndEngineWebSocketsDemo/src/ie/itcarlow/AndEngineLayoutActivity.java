package ie.itcarlow;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.LayoutGameActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AndEngineLayoutActivity extends LayoutGameActivity implements TextWatcher {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
    
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private Scene mScene;
	
    // Text related fields.
    private Text mText;
    private Font mFont;
   
    // A standard edit text control. 
    private EditText mEditText;
    
    // A standard button
    private Button mButtonSendMsg;
    private Button mButtonLoad;
    
    private final String TAG = "AndengineActivity";
    private AndroidWebSocketClient mWebSocketClient;
  

    
	// ===========================================================
	// Constructors
	// ===========================================================
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	protected int getLayoutID() {
		return R.layout.xmllayoutexample;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.xmllayoutexample_rendersurfaceview;
	}

	@Override
	protected void onSetContentView() {
		super.onSetContentView();

		this.mEditText = (EditText)this.findViewById(R.id.xmllayoutexample_text);
		this.mEditText.addTextChangedListener(this);
		
		mButtonSendMsg = (Button)this.findViewById(R.id.buttonSendMsg);
		mButtonSendMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mWebSocketClient.sendMessage();
			}
		});
		
	}	
	
	private void showToast(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
	
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}
	
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
	
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24);
		this.mFont.load();
		
		
		// Create the AndroidWebSocketClient
		mWebSocketClient = new AndroidWebSocketClient();	
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		this.mScene = new Scene();
		this.mScene.setBackground(new Background(0, 125, 58));
		pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);
		
	}
	

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
	    
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		this.mText = new Text(50, 240, this.mFont, "", 1000, vertexBufferObjectManager);
		mScene.attachChild(this.mText);
	    
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	// TextWatcher methods
	@Override	
	public void afterTextChanged(final Editable pEditable) {
		updateText();
	}

	private void updateText() {
		final String string = this.mEditText.getText().toString();
		if ( this.mText != null ) {
			this.mText.setText(string);
		}
	}

	@Override
	public void beforeTextChanged(final CharSequence pCharSequence, final int pStart, final int pCount, final int pAfter) {
		/* Nothing. */
	}

	@Override
	public void onTextChanged(final CharSequence pCharSequence, final int pStart, final int pBefore, final int pCount) {
		/* Nothing. */
	}

	
}