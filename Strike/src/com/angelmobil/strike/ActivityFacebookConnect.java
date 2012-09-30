package com.angelmobil.strike;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.google.gson.Gson;

import abcom.network.Message;
import abcom.social.FacebookPerson;
import abcom.social.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityFacebookConnect extends Activity {


	Facebook facebook;
	private SharedPreferences mPrefs;

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  //setContentView(R.layout.person);

		  Log.i("FacebookConnect", "Activity: onCreate()");

		  facebook = new Facebook("266685773343425");
		  mPrefs = getPreferences(MODE_PRIVATE);
		  String access_token = mPrefs.getString("access_token", null);
		  long expires = mPrefs.getLong("access_expires", 0);
		  if(access_token != null) {
			  facebook.setAccessToken(access_token);
	      }
		  if(expires != 0) {
			  facebook.setAccessExpires(expires);
	      }

		  if(!facebook.isSessionValid()) {
			  facebookAuthorize();
		  } else {
			  frequest();
              //FacebookCrawl f = new FacebookCrawl();
              //f.start();
		  }
	  }

	  public void frequest() {
		  AsyncFacebookRunner as = new AsyncFacebookRunner(facebook);
		  as.request("me", new RequestListener() {
			  public void onMalformedURLException(MalformedURLException e, Object state) {
			  }

			  public void onIOException(IOException e, Object state) {
			  }

			  public void onFileNotFoundException(FileNotFoundException e, Object state) {
			  }

			  public void onFacebookError(FacebookError e, Object state) {
			  }

			  public void onComplete(String response, Object state) {
				  Log.i("facebook.com", response);

				final FacebookPerson fb = new FacebookPerson();

				try {
					JSONObject jso = new JSONObject(response);
					for (Field f : fb.getClass().getFields()) {
						try {
							Object obj = jso.get(f.getName());
							if (obj instanceof JSONObject) {
								JSONObject o = (JSONObject)jso.get(f.getName());
								o.toString();
							} else {
								Object s = jso.get(f.getName());
								try {
									f.set(fb, s.toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}  catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


				 // Gson gson = new Gson();
				 // final FacebookPerson fb = gson.fromJson(response, FacebookPerson.class);

				  fb.access_token = facebook.getAccessToken();
				  User user = Storage.getInstance(getApplicationContext()).getUser();
				  user.clientLogin(fb);
				  Message msg = new Message();
				  msg.setType(Message.TYPE.USER_LOGIN);
				  msg.setContent(user.shortClone());
				  Connector.getInstance(getApplicationContext()).post(msg);
				  //App.getInstance(getApplicationContext()).login(user);
				  finish();
			  }
		  });
	  }


	  public void facebookAuthorize() {
//		  facebook.authorize(this, new String[] { "email", "user_location", "friends_location" }, new DialogListener() {
		  facebook.authorize(this, new DialogListener() {
	            public void onComplete(Bundle values) {
	            	SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                    frequest();
                    //FacebookCrawl f = new FacebookCrawl();
                    //f.start();
	            }

	            public void onFacebookError(FacebookError error) {
	            	Log.d("", error.getMessage());
	            }

	            public void onError(DialogError e) {
	            	Log.d("", e.getMessage());
	            }

	            public void onCancel() {
	            	Log.d("", "Canceled");
	            }
	        });
	  }

	  @Override
	  protected void onResume() {
		super.onResume();
		facebook.extendAccessTokenIfNeeded(this, null);
	  }




}
