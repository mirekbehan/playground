package com.obzb.sensorlogger;

import java.util.ArrayList;

import com.obzb.sensorlogger.classes.ISensor;
import com.obzb.sensorlogger.classes.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity{
	public static Context CONTEXT;
	//GUI prvky
	TextView txtLog;
	Spinner spSensors;
	ArrayList<String> lSensors = new ArrayList<String>();
	SparseIntArray priSenzory = new SparseIntArray(); //pøiøazuje id typu senzoru k jeho poøadí ve spinneru, sparseIntArray má být výkonnìjší než HashMap
	
	// tøídy pro senzory
	public static SensorManager SMANAGER;
	private Sensor sensor;
	private Sensors sensors;
	private ISensor isensor;
	private SensorEventListener slistener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONTEXT = this;
        SMANAGER = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = new Sensors();
        vytvorSlistener();
        //GUI inicializace
        txtLog = (TextView) findViewById(R.id.txtLog);
        spSensors = (Spinner)findViewById(R.id.spSensors);
        
        naplnSpinner();
        ArrayAdapter<String> arSensors = new ArrayAdapter<String>(this, R.layout.row, lSensors);
        spSensors.setAdapter(arSensors);
        
        spSensors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pozice, long arg3) {
				SMANAGER.unregisterListener(slistener);
				isensor = sensors.getISensor(priSenzory.get(pozice));
				sensor = isensor.getSensor();
				String nazev = sensor.getName();
				txtLog.setText(nazev);
				SMANAGER.registerListener(slistener, sensor, SensorManager.SENSOR_DELAY_UI);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				txtLog.setText("Nic nevybráno");
			}
		});
        
    }
    
    private void vytvorSlistener() {
		slistener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				txtLog.setText(isensor.getValuesNames()[0][0]+String.valueOf(event.values[0])+isensor.getValuesNames()[0][1]+"\n"
						+isensor.getValuesNames()[1][0]+String.valueOf(event.values[1])+isensor.getValuesNames()[1][1]+"\n"
						+isensor.getValuesNames()[2][0]+String.valueOf(event.values[2])+isensor.getValuesNames()[2][1]);
				
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};
		
	}

	/**
     * Plní spinner existujícími senzory a vytváøí hashmapu k identifikaci položek
     */
    private void naplnSpinner() { 
		int pozice = 0;
		if (sensors.checkSensor(1)) {
			lSensors.add(sensors.vratJmenoSenzoru(1));
			priSenzory.put(pozice, 1);
			pozice++;};
		if (sensors.checkSensor(9)) {
			lSensors.add(sensors.vratJmenoSenzoru(9));
			priSenzory.put(pozice, 9);
			pozice++;};
		if (sensors.checkSensor(4)) {
			lSensors.add(sensors.vratJmenoSenzoru(4));
			priSenzory.put(pozice, 4);
			pozice++;};
		if (sensors.checkSensor(5)) {
			lSensors.add(sensors.vratJmenoSenzoru(5));
			priSenzory.put(pozice, 5);
			pozice++;};
		if (sensors.checkSensor(10)) {
			lSensors.add(sensors.vratJmenoSenzoru(10));
			priSenzory.put(pozice, 10);
			pozice++;};
		if (sensors.checkSensor(2)) {
			lSensors.add(sensors.vratJmenoSenzoru(2));
			priSenzory.put(pozice, 2);
			pozice++;};
		if (sensors.checkSensor(6)) {
			lSensors.add(sensors.vratJmenoSenzoru(6));
			priSenzory.put(pozice, 6);
			pozice++;};
		if (sensors.checkSensor(8)) {
			lSensors.add(sensors.vratJmenoSenzoru(8));
			priSenzory.put(pozice, 8);
			pozice++;};
		if (sensors.checkSensor(11)) {
			lSensors.add(sensors.vratJmenoSenzoru(11));
			priSenzory.put(pozice, 11);
			pozice++;};
	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
