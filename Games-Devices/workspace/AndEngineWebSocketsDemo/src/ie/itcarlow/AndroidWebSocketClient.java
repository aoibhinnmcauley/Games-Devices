package ie.itcarlow;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.codebutler.android_websockets.WebSocketClient;

public class AndroidWebSocketClient {
	
	private final String TAG = "AndroidWebSocketClient";
	private WebSocketClient mClient;
	
	public AndroidWebSocketClient() {
		
		List<BasicNameValuePair> extraHeaders = Arrays.asList(
			    new BasicNameValuePair("Cookie", "session=abcd")
		);
		
		
		mClient = new WebSocketClient(URI.create("ws://10.0.2.2:8080/wstest"), new WebSocketClient.Listener() {
		    @Override
		    public void onConnect() {
		       Log.d(TAG, "Connected!");
		     
		    }

		    @Override
		    public void onMessage(String message) {
		        Log.d(TAG, String.format("Got string message! %s", message));
		       
		    }

		    @Override
		    public void onMessage(byte[] data) {
		        //Log.d(TAG, String.format("Got binary message! %s", toHexString(data));
		    }

		    @Override
		    public void onDisconnect(int code, String reason) {
		        Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
		    }

		    @Override
		    public void onError(Exception error) {
		        Log.e(TAG, "Error!", error);
		    }
		}, extraHeaders);

		mClient.connect();
		
		
	}
	
	void sendMessage() {

	}
		
}
