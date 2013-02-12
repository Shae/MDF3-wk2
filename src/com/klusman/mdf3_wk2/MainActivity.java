package com.klusman.mdf3_wk2;



import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	MediaPlayer mp;
	Button AudioBtn;
	private SensorManager sensorManager;
	private Sensor proximitySensor;
	//private TextView distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        mp = MediaPlayer.create(MainActivity.this, R.raw.handlebars);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorReg();
        AudioBtn = (Button) findViewById(R.id.audioBtn);
        AudioBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(AudioBtn.getText().toString().compareTo("Play Audio") == 0){
					AudioBtn.setText("Stop Audio");
					mp.start();

						
				}else{
					AudioBtn.setText("Play Audio");
					mp.pause();
				}
				
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor == proximitySensor)
		{
			double x = event.values[0];
			//myToast("Distance Changed");
			if(AudioBtn.getText().toString().compareTo("Stop Audio") == 0){
				if(x == 0.0){
					if(mp.isPlaying()){
						mp.pause();
						myToast("paused");
					}
				}else{
						mp.start();
						myToast("play");
				}
			}
		}
		
	}
    
	private void sensorReg(){
        /////////// PROX
        if (proximitySensor == null)
        {
        	myToast("No Proximity Sensor Found");
        }
        else
        {
        	sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        	Log.i("SENSOR", "prox registered");
        }
        
     
        
	}
	
	
	public void myToast(String text){  // Toast Template
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(MainActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};// end myToast
}
