package ie.itcarlow.androidevents;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Button _clickBtn;
	private TextView _textView;
	private LinearLayout _layout;
	private EditText _editText; 
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
 
        _textView = (TextView) findViewById(R.id.textView1);
        _clickBtn = (Button) findViewById(R.id.button1);
        _clickBtn.setOnClickListener(this);  
        _editText = (EditText)findViewById(R.id.editText1);
        _editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            	 	
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {_textView.setText(" ");}
        }); 
    }
	
	
    public void onClick(View v) {
      
	  _textView.setText(_editText.getEditableText().toString().toUpperCase());
	 
	  
	  
    }
    
}



