package ie.itcarlow.EventsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Button _clickBtn;
	private TextView _textView;
	private LinearLayout _layout;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
 
        _textView = (TextView) findViewById(R.id.textview);
        _clickBtn = (Button) findViewById(R.id.button);
        
        _clickBtn.setOnClickListener(this);        
    }
	
    public void onClick(View v) {		
	  _textView.setText(this.getString( R.string.hello_world ));
    }
}


