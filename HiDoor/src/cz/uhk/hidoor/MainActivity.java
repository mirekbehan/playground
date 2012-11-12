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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_door:
                addDoor();
                return true;
            case R.id.list_door:
                listDoor();
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
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
    
    private void addDoor() {
    	setContentView(R.layout.pairing);
    	Button button = (Button)findViewById(R.id.saveAndPair);
                
        button.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                	TextView pswd = (TextView) findViewById(R.id.password);
                	TextView pswd2 = (TextView) findViewById(R.id.password2);
                	String p1 = pswd.getText().toString();
                	String p2 = pswd2.getText().toString();
                	TextView ulice = (TextView) findViewById(R.id.ulice);
                	String ul = ulice.getText().toString();
                	TextView name = (TextView) findViewById(R.id.name);
                	String na = name.getText().toString();
                	if (ul!="") {
	                	if (na!="") {
		                	if ((p1.equals(p2)) && (p1!="")) {
		                		try {
		                			Door door = new Door();
									db.addDoor(new Door(ul,na,0,door.getHash(p1),"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcu/EOmBWHMvnkORx68awtlTpANrs5p+gRooHR4xqS+4i8KWcIKX2/y12wsBO9QuroXPQQ879nC4fcFS0eo0F/L/DTIbS0gIQ+zM9bwgL/w7kjj7DNEpZwIIUlZVsbAATjaH8ZULhTw9xESbcGHw2+5gApZq6vGASGg+af8XVd0EZx1Ih+jMfXQB8AwgPemfT5nzHX46G7W+Shc4S4eJifzon2YE+fgCisAXJ8HZ6gIeB4lmqB9yo8QbcRXdHWUxVrwrkwCvNAkocYeupGd+f3OCxftnY8yAdG02O90LQM8DsATn/7/F7G0MsmrGaYNLaUb2lfR2Bbd4HENcCoQ1fwIDAQAB"));
									setContentView(R.layout.activity_main);
		                		} catch (NoSuchAlgorithmException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                	}
		                	else Toast.makeText(v.getContext(), "Hesla se neshodují", Toast.LENGTH_LONG).show();
		                	
	                	} else 	Toast.makeText(v.getContext(), "Není zadán název!", Toast.LENGTH_LONG).show();
                	} else Toast.makeText(v.getContext(), "Není zadána Ulice!", Toast.LENGTH_LONG).show();
        	}
        });
    }
    
    private void listDoor() {
    	Intent i = new Intent(this, ListDoorLoader.class);
    	startActivity(i);
    }

}
