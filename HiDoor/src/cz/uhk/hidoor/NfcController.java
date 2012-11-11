package cz.uhk.hidoor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;

public class NfcController {

	/**
	 * @param args
	 */
	
	public static String Zapnuto(Context context) {
    	NfcAdapter adapter = NfcAdapter.getDefaultAdapter(context);
    	if (adapter.isEnabled()) {
    		return "NFC Adapter is enabled";
    	} else { return "NFC Adapter is disabled";} 
    }
	
	public static String tagRead(Intent intent) {
    	String text = null;
		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			 Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			 NdefMessage msg = (NdefMessage) rawMsgs[0];
			 NdefRecord cardRecord = msg.getRecords()[0];
			 byte[] payload = cardRecord.getPayload();
			 
			//Get the Text Encoding
          String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
          
          //Get the Language Code
          int languageCodeLength = payload[0] & 0077;
          /*try {
				String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
          
          //Get the Text
			try {
				text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				text = "Nepovedlo se";
			}
			
    	}
		return text;
    }
	
	public static void tagWrite(String text, Tag tag) throws IOException, FormatException {
    	NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();       
    }
	
	private static NdefRecord createRecord(String text) throws UnsupportedEncodingException {

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
