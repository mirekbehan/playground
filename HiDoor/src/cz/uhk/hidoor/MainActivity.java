package cz.uhk.hidoor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	String tag;
	Intent intent;
	NfcAdapter adapter;
	PendingIntent pendingIntent;
	IntentFilter writeTagFilters[];
	Boolean onWriteMode = false;
	ToggleButton switchReadWrite;
	Tag mytag;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        /*nastaveni apapteru */
        this.adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };
		
		TextView textView = (TextView) findViewById(R.id.textView2);
		//prompt if return disabled
        textView.setText(NfcController.Zapnuto(this)); 
                
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    protected void onNewIntent(Intent intent){
    	if (this.onWriteMode) { 
    		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
	    		mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	    		EditText text = (EditText)findViewById(R.id.text); 
	        	String value = text.getText().toString();
	    		try {
	    			NfcController.tagWrite(value,mytag);
					Toast.makeText(this, "Zapsano", Toast.LENGTH_LONG ).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	} else {
    			String text = NfcController.tagRead(intent);
				Toast.makeText(this, text, Toast.LENGTH_LONG ).show();
				TextView textView = (TextView) findViewById(R.id.zprava); 
		        textView.setText(text);
    	}
	}
    

    
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    
    public void zapisHodnotu(View view){
        EditText text = (EditText)findViewById(R.id.text); 
    	String value = text.getText().toString();
        TextView textView = (TextView)findViewById(R.id.textView1); 
        textView.setText(value);
    }
    
    public void switchReadWrite() {
    	ToggleButton tb = (ToggleButton)findViewById(R.id.switchReadWrite); 
    	if(tb.isChecked()){
    		//write
    		this.onWriteMode = true;
    		 TextView textView = (TextView)findViewById(R.id.textView1); 
    	     textView.setText("zapis");
    	} else 
    	{
    		//read
    		this.onWriteMode = false;
    		 TextView textView = (TextView)findViewById(R.id.textView1); 
    	     textView.setText("cti");

    	}
    }
    
     
    public void addListenerOnButton() {
    	 
    	switchReadWrite = (ToggleButton) findViewById(R.id.switchReadWrite);
    	switchReadWrite.setOnClickListener(new OnClickListener() {
     
    		@Override
    		public void onClick(View v) {     
    			switchReadWrite();    
    		}
     
    	});
     
      }
    
}
