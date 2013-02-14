package com.klusman.mdf3_wk2;



import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      	//iv.setVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);
        
        mp = MediaPlayer.create(MainActivity.this, R.raw.handlebars);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorReg();
        
        AudioBtn = (Button) findViewById(R.id.audioBtn);
        AudioBtn.setTextColor(getResources().getColor(R.color.Green) );

        
        AudioBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(AudioBtn.getText().toString().compareTo("Play Audio") == 0){
					AudioBtn.setText("Pause Audio");
					//iv.setVisibility(View.VISIBLE);
					//Resources res = getResources(); /** from an Activity */
					//iv.setImageDrawable(res.getDrawable(R.drawable.album_art));
					//iv.setImageResource(R.drawable.album_art);
					mp.start();
					AudioBtn.setTextColor(getResources().getColor(R.color.Yellow) );
					notifyMe();
				}else{
					AudioBtn.setText("Play Audio");
					mp.pause();
					AudioBtn.setTextColor(getResources().getColor(R.color.Green) );
					notifyMe();
				}
				
			}
			
			
		});
        
        StopBtn = (Button)findViewById(R.id.stopBtn);
        StopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopMusic();
				//iv.setVisibility(View.INVISIBLE);
				
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
			double x = event.values[0];
			//myToast("Distance Changed");
			if(AudioBtn.getText().toString().compareTo("Pause Audio") == 0){
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
	public void stopMusic(){
		mp.stop();
		AudioBtn.setText("Play Audio");
		AudioBtn.setTextColor(getResources().getColor(R.color.Green) );
		//iv.setImageResource(0);
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
	
	
	public void notifyMe(){
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification();
		note.defaults = Notification.DEFAULT_VIBRATE;
		nm.notify(VIB_NOTE_ID, note);
	}
	
	public void myToast(String text){  // Toast Template
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(MainActivity.this, textIN, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};// end myToast
	
	
}
