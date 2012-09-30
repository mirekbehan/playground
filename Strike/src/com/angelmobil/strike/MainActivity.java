package com.angelmobil.strike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LinearLayout surface = (LinearLayout)findViewById(R.id.layout);
        surface.addView(new GameView(this));
        
        
        photo = BitmapFactory.decodeFile("photo.jpg");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


	private void dispatchTakePictureIntent(int actionCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, actionCode);
	}
	
	
	static Bitmap photo;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	  if ((requestCode == 1) && (resultCode == Activity.RESULT_OK)) {
		  Bundle extras = intent.getExtras();
		  photo = (Bitmap) extras.get("data");
		  try {
			  FileOutputStream out = new FileOutputStream("photo.jpg");
			  photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	}

/*	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
*/	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        	case R.id.menu_photo:
        		dispatchTakePictureIntent(1);
				
				break;
        	case R.id.menu_settings:
			    startActivity(new Intent(this, SettingsActivity.class));
        		break;

        }
    	return true;
    }

    
}
