package com.example.compass;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager sm;
    //get access to sensors
    private Sensor mSensor;
    //represent a sensor
    
    //Set TextView for sensor value
    private TextView mag_x;
    private TextView mag_y;
    private TextView mag_z;
    private TextView mag_h;
    
    private TextView aX;
    private TextView aY;
    private TextView aZ;
    private TextView mX;
    private TextView mY;
    private TextView mZ;
    
    //---------------------
	TextView tv;
	TextView tvDegree;
//    private SensorManager sm;
    
    //we need two sensors in this application
    private Sensor aSensor;
//    private Sensor mSensor;
    
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3];
    
	
	
   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initialization();
        calculateOrientation();
//        //Find view from layout file
//        mag_x = (TextView)findViewById(R.id.Xaxis);
//        mag_y = (TextView)findViewById(R.id.Yaxis);
//        mag_z = (TextView)findViewById(R.id.Zaxis);
//        mag_h = (TextView)findViewById(R.id.magnetic_field);
//        
//        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        //Get an instance of SensorManager for accessing sensors
//        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        //Determine a default sensor type, in this case is magnetometer
    }
    
	private void initialization() {
		 //Find view from layout file
        
        aX = (TextView)findViewById(R.id.aX);
        aY = (TextView)findViewById(R.id.aY);
        aZ = (TextView)findViewById(R.id.aZ);
        mX = (TextView)findViewById(R.id.mX);
        mY = (TextView)findViewById(R.id.mY);
        mZ = (TextView)findViewById(R.id.mZ);
        
        tv = (TextView)findViewById(R.id.tv);
    	tvDegree = (TextView)findViewById(R.id.degree);
        
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        //Get an instance of SensorManager for accessing sensors
        mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //Determine a default sensor type, in this case is magnetometer
	    aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      //Determine a default sensor type, in this case is accelerometer
        
        
		
	
//	 sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//     //Get an instance of SensorManager for accessing sensors
////	 aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//     //Determine a default sensor type, in this case is magnetometer
//     mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//     //Determine a default sensor type, in this case is magnetometer
//	
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
    	
    	//set the values from sensor
    	if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
    	    magneticFieldValues[0] = x;
    	    magneticFieldValues[1] = y;
    	    magneticFieldValues[2] = z;
    	    mX.setText("mXaxis:"+ magneticFieldValues[0]);
    		mY.setText("mYaxis:"+ magneticFieldValues[1]);
    		mZ.setText("mZaxis:"+ magneticFieldValues[2]);
    	}
    	
    	if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
    		accelerometerValues[0] = x;
    		accelerometerValues[1] = y;
    		accelerometerValues[2] = z;
    		aX.setText("aXaxis:"+ accelerometerValues[0]);
    		aY.setText("aYaxis:"+ accelerometerValues[1]);
    		aZ.setText("aZaxis:"+ accelerometerValues[2]);
    	}
    	
    	calculateOrientation();
    }
    
    private void calculateOrientation() {
    	
    	float[] values = new float[3];
    	float[] R = new float[9];
  	
    	SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
    	SensorManager.getOrientation(R, values);

    	float degree = (float) Math.toDegrees(values[0]);
    	
    	//float rot_x = (float) Math.toDegrees(values[1]);
    	
    	//float rot_y = (float) Math.toDegrees(values[2]);
    	
    	if ((degree >= -5) && (degree < 5)) {
    		tv.setText("North");
    	} else if ((degree >= 85) && (degree < 95)){
    		tv.setText("East");
    	} else if ((degree >= 175) || (degree < -175)){
    		tv.setText("South");
    	} else if ((degree >= -95) && (degree < -85)){
    		tv.setText("West");
    	} else {
    		tv.setText("reading direction...");
    	}
    
    		
    	tvDegree.setText("Degree: " + degree);
    }
    
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    	
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    	//register the sensor when user returns to the activity
    	sm.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
    	//register the sensor when user returns to the activity
    	
    }
    
    protected void onPause(){
    	super.onPause();
    	sm.unregisterListener(this);
    	//disable the sensor
    }
	
	
	
	
	
    
    
}
