package com.example.helloemfsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
    //get access to sensors
    private Sensor mMagneticField;
    //represent a sensor
    
    //Set TextView for sensor value
    private TextView mag_x;
    private TextView mag_y;
    private TextView mag_z;
    private TextView mag_h;
	
   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Find view from layout file
        mag_x = (TextView)findViewById(R.id.Xaxis);
        mag_y = (TextView)findViewById(R.id.Yaxis);
        mag_z = (TextView)findViewById(R.id.Zaxis);
        mag_h = (TextView)findViewById(R.id.magnetic_field);
        
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Get an instance of SensorManager for accessing sensors
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //Determine a default sensor type, in this case is magnetometer
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    private float x,y,z;
    private double h;
    @Override
    public void onSensorChanged(SensorEvent event){
    	//read sensor value from SensorEvent
    	x = event.values[0];
    	y = event.values[1];
    	z = event.values[2];
    	//calculate the total magnetic field
    	h = Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]);
    	
    	//set the string value from sensor to text
    	if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
    		mag_x.setText("Xaxis:"+ x);
    		mag_y.setText("Yaxis:"+ y);
    		mag_z.setText("Zaxis:"+ z);
    		mag_h.setText("magnetic_field:"+ h);
    	}
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    	
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    	//register the sensor when user returns to the activity
    }
    
    protected void onPause(){
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    	//disable the sensor
    }
    
    
}
