package com.example.studentspubguide;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HlavniActivity extends Activity {
	private Button mapa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlavni);
        final Intent intent1 = new Intent(getApplicationContext(), MapaActivity.class);
        mapa = (Button) findViewById(R.id.button1);
        mapa.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(intent1);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hlavni, menu);
        return true;
    }
}
