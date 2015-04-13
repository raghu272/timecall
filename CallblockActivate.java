package com.example.raghu272.timecall;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallblockActivate extends ActionBarActivity {

    CheckBox blockAll_cb;//,blockcontacts_cb;
    BroadcastReceiver CallBlocker;
    TelephonyManager telephonyManager;
    ITelephony telephonyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callblock);

        ///////////////////////////////////////////////////////////////////////////////////////////
        initviews();
        final Intent intentAlarm;
        intentAlarm = new Intent(this, AlarmReceiver.class);
        blockAll_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                CallBlocker =new BroadcastReceiver()
                {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // TODO Auto-generated method stub
                        telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                        //Java Reflections
                        Class c = null;
                        try {
                            c = Class.forName(telephonyManager.getClass().getName());
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Method m = null;
                        try {
                            m = c.getDeclaredMethod("getITelephony");
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        m.setAccessible(true);
                        try {
                            telephonyService = (ITelephony)m.invoke(telephonyManager);
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
                    }//onReceive()

                    PhoneStateListener callBlockListener = new PhoneStateListener()
                    {

                        public void onCallStateChanged(int state, String incomingNumber)
                        {

                            if(state==TelephonyManager.CALL_STATE_RINGING)
                            {
                                super.onCallStateChanged(state, incomingNumber);               //Trying to get the incoming number
                                //System.out.println("incomingNumber : "+incomingNumber);
                                Toast.makeText(getBaseContext(), incomingNumber, Toast.LENGTH_LONG).show();
//to push
                                if(blockAll_cb.isChecked())
                                {
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    intentAlarm.putExtra("PERIOD",incomingNumber);
                                    sendBroadcast(intentAlarm);
                                   // alarmManager.set(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(), PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

                                    telephonyService.endCall();

                                }
                            }
                        }
                    };
                };//BroadcastReceiver
                IntentFilter filter= new IntentFilter("android.intent.action.PHONE_STATE");
                registerReceiver(CallBlocker, filter);
            }
        });
    }


    public void initviews()
    {
        blockAll_cb=(CheckBox)findViewById(R.id.cbBlockAll);
        //blockcontacts_cb=(CheckBox)findViewById(R.id.cbBlockContacts);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (CallBlocker != null)
        {
            unregisterReceiver(CallBlocker);
            CallBlocker = null;
        }
    }



    //////////////////////////////////////////////////////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
