package com.example.raghu272.timecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {


	/////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
    public void onReceive(Context context, Intent intent)
    {
            // TODO Auto-generated method stub
        
            
              // here you can start an activity or service depending on your need
             // for ex you can start an activity to vibrate phone or to ring the phone   
                             
            String phoneNumberReciver=intent.getStringExtra("PERIOD");;// phone number to which SMS is to be send
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String str=prefs.getString("ACTIVITY","");
            String message="Hello I'm currently "+str+". Will get back to you soon. This is an automated message.";// message to send
            SmsManager sms = SmsManager.getDefault(); 
            sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
            // Show the toast  like in above screen shot
            Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
     }


}
