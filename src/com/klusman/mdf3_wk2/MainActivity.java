package com.klusman.mdf3_wk2;



import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Bundle;
import android.os.Handler;

import android.provider.MediaStore;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;

import android.content.Context;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
	
	
	MediaPlayer mp;
	Button AudioBtn;
	Button StopBtn;
	private SensorManager sensorManager;
	private Sensor proximitySensor;
	ImageView iv;
	private final int VIB_NOTE_ID = 1;
	Uri allsongsuri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
	Handler handler;
	long startTime;
	boolean paused = false;
	Handler mHandler = new Handler();
	double x;
	Context _context = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      	//iv.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);
        
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        AudioBtn = (Button) findViewById(R.id.audioBtn);
        AudioBtn.setTextColor(getResources().getColor(R.color.Green) );

        sensorReg();
        
        
        AudioBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if(AudioBtn.getText().toString().compareTo("Play Audio") == 0){
						mp = MediaPlayer.create(MainActivity.this, R.raw.handlebars);
						mp.start();
						AudioBtn.setText("Pause Audio");
						StopBtn.setText("Stop Audio Service");
						AudioBtn.setTextColor(getResources().getColor(R.color.Yellow) );
						notifyMe();
						
					}else if(AudioBtn.getText().toString().compareTo("Pause Audio") == 0){
						
						mp.pause();
						AudioBtn.setText("Resume Audio");
						AudioBtn.setTextColor(getResources().getColor(R.color.LightBlue) );
						notifyMe();
						
					}else if(AudioBtn.getText().toString().compareTo("Resume Audio") == 0){
						
						mp.start();
						AudioBtn.setText("Pause Audio");
						AudioBtn.setTextColor(getResources().getColor(R.color.Yellow) );
						notifyMe();
					}
				
			}
			

			
		});
        
        
        StopBtn = (Button)findViewById(R.id.stopBtn);
        StopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopMusic();	
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
	}

	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor == proximitySensor)
		{
			x = event.values[0];
			

				
				
			if(AudioBtn.getText().toString().compareTo("Pause Audio") == 0){ // check to see if the music SHOULD be playing at this time
				checkProx();
			}else{
				
			}
				
				

		}
		
	}
	public void stopMusic(){
		mp.stop();
		AudioBtn.setText("Play Audio");
		AudioBtn.setTextColor(getResources().getColor(R.color.Green) );
	}
    
	public void runTimer() {
		long TimeNow = System.currentTimeMillis();
		final long EndTime = TimeNow + 5000;

	      while((x == 0.0) && (EndTime >= System.currentTimeMillis() )){
	            		
	          if(EndTime <= System.currentTimeMillis() ){
	          	notifyMe();
	          	checkProx();
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
	
	
	public void notifyMe(){  // Haptic Vibrations for buttons
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification();
		note.defaults = Notification.DEFAULT_VIBRATE;

		nm.notify(VIB_NOTE_ID, note);
		
	}
	
	public void checkProx(){
		if(x == 0.0){  // proximity - close
			if(mp.isPlaying()){
				mp.pause();
				paused = true;
				//runTimer();
				myToast("paused");						
			}
		}else{  // proximity - far
			mp.start();
			paused = false;
			myToast("play");
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

