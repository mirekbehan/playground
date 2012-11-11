package cz.uhk.hidoor;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	/*Intent intent;
	NfcAdapter adapter;
	PendingIntent pendingIntent;
	IntentFilter writeTagFilters[];*/
	DatabaseHandler db = new DatabaseHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button1);
        
        
        
        
        button.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                try {
					open();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }	
        });
        
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
        		Door d = new Door();
                db.addDoor(d);
        	}
        });
        /*nastaveni apapteru */
       /* this.adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };
		
		TextView textView = (TextView) findViewById(R.id.textView2);
		//prompt if return disabled
        textView.setText(NfcController.Zapnuto(this)); 
        */        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
   
    
    @Override
    public void onResume(){
    	super.onResume();
    	//this.adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    
    private void open() throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	TextView pswd = (TextView) findViewById(R.id.pswd);
    	Door door = new Door();
    	String password = door.getHash(pswd.getText().toString());
    	door = db.getDoorByPassword(password);
    	if (door!=null) {
    		Toast.makeText(this, "Pro otevøení " + door.getId() + " pøiložte k zámku!", Toast.LENGTH_LONG).show();
    	} else {
    	Toast.makeText(this, "Pro Vámi zadané heslo neexistují dveøe!", Toast.LENGTH_LONG).show();
    	}
    }
    
}
