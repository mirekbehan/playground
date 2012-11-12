package com.example.studentspubguide;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddPubActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pub);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_pub, menu);
        return true;
    }
}
