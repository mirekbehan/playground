package com.example.studentspubguide;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HlavniActivity extends Activity {
	private Button mapa;
	private Button pridej;
	private Button app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlavni);
        
        // button 1
        final Intent intent1 = new Intent(getApplicationContext(), MapaActivity.class);
        mapa = (Button) findViewById(R.id.button1);
        mapa.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(intent1);
				
			}
		});
        // button 2
        final Intent intent2 = new Intent(getApplicationContext(), AddPubActivity.class);
        pridej = (Button) findViewById(R.id.button3);
        pridej.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(intent2);
				
			}
		});
        app = (Button) findViewById(R.id.button4);
        app.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
				dialog.setTitle("O aplikaci");
				dialog.setMessage("Aplikace Student's Pub Guide vznikla jako projekt pøedmìtu SMAP na Univerzitì Hradec Králové");
				dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
					}
				});
				dialog.show();
				
			}
		});
    }

    public Activity getActivity(){
    	return this;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hlavni, menu);
        return true;
    }
}
