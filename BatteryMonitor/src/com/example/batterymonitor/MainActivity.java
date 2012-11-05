package com.example.batterymonitor;

import powerusage.CurrentReaderFactory;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        registerReceiver(this.mBatInfoReceiver,  
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){  
    	 
        @Override  
        public void onReceive(Context arg0, Intent intent) {  
       
        	
        	intent.putExtra(BatteryManager.EXTRA_TEMPERATURE, 80);
        	intent.putExtra(BatteryManager.EXTRA_VOLTAGE, 8000);
        	
        	
        	int  rawlevel = intent.getIntExtra("level", -1);
        	int  scale = intent.getIntExtra("scale", -1);
        	int level = -1;
            if (rawlevel >= 0 && scale > 0) {
                level = (rawlevel * 100) / scale;
            }
        	
        	int  voltage = intent.getIntExtra("voltage", -1); 
        	int  temperature = intent.getIntExtra("temperature", 0);
        	
        	
        	
        	/*int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
        	
        	int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
        	boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
        	
        	int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
        	String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        	*/
        	
        	
        	
        	Long usage = CurrentReaderFactory.getValue();
              
            TextView tvLevel = (TextView)findViewById(R.id.level);
            tvLevel.setText(String.valueOf(level) + "%");
            
            //obr�zek stavu nabit�
            if (level > 75){
            	ImageView imgBat = (ImageView)findViewById(R.id.battImg);
            	imgBat.setImageResource(R.drawable.batteryfull);
            } else if (level > 15){
            	ImageView imgBat = (ImageView)findViewById(R.id.battImg);
            	imgBat.setImageResource(R.drawable.batterymedium);
            } else {
            	ImageView imgBat = (ImageView)findViewById(R.id.battImg);
            	imgBat.setImageResource(R.drawable.criticalenergy);
            }
            
            TextView tvScale = (TextView)findViewById(R.id.scale);
            tvScale.setText(String.valueOf(100 - scale) + "%");
            
            TextView tvTemperature = (TextView)findViewById(R.id.temperature);
            tvTemperature.setText(String.valueOf(temperature/(float)10) + "�C");
            
            TextView tvVoltage = (TextView)findViewById(R.id.voltage);
            tvVoltage.setText(String.valueOf(voltage) + "mV");
            
            TextView tvUsage = (TextView)findViewById(R.id.usage);
            tvUsage.setText(String.valueOf(usage) + "mA");
            
        } 
      };  
}
