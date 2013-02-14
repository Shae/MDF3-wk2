package com.klusman.mdf3_wk2;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class batteryCheck extends BroadcastReceiver {

	Context _context;
	
	 @Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        _context = context;
	        
	        if(action.equals("android.intent.action.BATTERY_LOW")){
	            Log.i("BATTERY CHECK", "BATTERY LOW!!!");
	            myToast("Battery Low!");
	           
	        }else if(action.equals("android.intent.action.BATTERY_OKAY")){
	        	Log.i("BATTERY CHECK", "BATTERY OKAY!!!");
	        	myToast("Battery OKAY!");
	        }
//	        else if(action.equals("android.intent.action.ACTION_POWER_CONNECTED")){  // only here to force broadcast
//	        	Log.i("BATTERY CHECK", "POWER CONNECTED");
//	        	myToast("POWER CONNECTED!");
//	        }
//	        else if(action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")){  // only here to force broadcast
//	        	Log.i("BATTERY CHECK", "POWER DISCONNECTED");
//	        	myToast("POWER DISCONNECTED!");
//	        }
	        
	    }

	 
	 public void myToast(String text){  // Toast Template
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(_context, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast

}
