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
        /*nastaveni apapteru*/
        this.adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters = new IntentFilter[] { tagDetected };
		
		TextView textView = (TextView) findViewById(R.id.textView2); 
        textView.setText(this.Zapnuto()); 
                
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
					this.tagWrite(value,mytag);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	} else {
    		this.tagRead(intent);
    	}
	}
    
    private void tagRead(Intent intent) {
    	if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			 Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			 NdefMessage msg = (NdefMessage) rawMsgs[0];
			 NdefRecord cardRecord = msg.getRecords()[0];
			 byte[] payload = cardRecord.getPayload();
			 
			//Get the Text Encoding
          String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
          
          //Get the Language Code
          int languageCodeLength = payload[0] & 0077;
          try {
				String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
          //Get the Text
           String text;
			try {
				text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				text = "Nepovedlo se";
			}
			Toast.makeText(this, text, Toast.LENGTH_LONG ).show();
			TextView textView = (TextView) findViewById(R.id.zprava); 
	        textView.setText(text);
    	}
    }

    private void tagWrite(String text, Tag tag) throws IOException, FormatException {
    	
    	NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
        Toast.makeText(this, "Zapsano", Toast.LENGTH_LONG ).show();
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
    
    public String Zapnuto() {
    	NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    	if (adapter.isEnabled()) {
    		return "NFC Adapter is enabled";
    	} else { return "NFC Adapter is disabled";} 
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
    
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

        //create the message in according with the standard
        String lang = "cs";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
  }
    
   
    
}
