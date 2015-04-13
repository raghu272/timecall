package com.example.raghu272.timecall;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
//push
public class Timesetter extends ActionBarActivity {
	TimePicker myTimePicker;
	Button buttonstartSetDialog;
	
	TimePickerDialog timePickerDialog;
	final static int RQS_1 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timesetter);
		buttonstartSetDialog = (Button)findViewById(R.id.button2);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){

   @Override
   public void onClick(View v) {
    openTimePickerDialog(false);
    
   }});

	}
	
	private void openTimePickerDialog(boolean is24r){
		  Calendar calendar = Calendar.getInstance();
		  
		  timePickerDialog = new TimePickerDialog(
		    Timesetter.this, 
		    onTimeSetListener, 
		    calendar.get(Calendar.HOUR_OF_DAY), 
		    calendar.get(Calendar.MINUTE), 
		    is24r);
		  timePickerDialog.setTitle("Set Start Time");  
		        
		  timePickerDialog.show();

		 }
		    
	OnTimeSetListener onTimeSetListener
    = new OnTimeSetListener(){

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

   Calendar calNow = Calendar.getInstance();
   Calendar calSet = (Calendar) calNow.clone();

   calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
   calSet.set(Calendar.MINUTE, minute);
   calSet.set(Calendar.SECOND, 0);
   calSet.set(Calendar.MILLISECOND, 0);
   
   if(calSet.compareTo(calNow) <= 0){
    //Today Set time passed, count to tomorrow
    calSet.add(Calendar.DATE, 1);
   }
   
   scheduleAlarm(calSet);
  }};

	
	///////////////////////////////////////////////////////////////////////////
	 public void scheduleAlarm(Calendar targetCal)
	    {
	            // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time, 
	            // we fetch  the current time in milliseconds and added 1 day time
	            // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day        
	            //Long time = new GregorianCalendar().getTimeInMillis()+10*1000;

	            // create an Intent and set the class which will execute when Alarm triggers, here we have
	            // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
	            // alarm triggers and 
	            //we will write the code to send SMS inside onRecieve() method pf Alarmreceiver class
                Intent intentCallBlock= new Intent(this,CallblockActivate.class);

                startActivity(intentCallBlock);
                //sendBroadcast(intentCallBlock);
	            // create the object
	            AlarmManager callblockmanager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

	            //set the alarm for particular time
	            //callblockmanager.set(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentCallBlock, PendingIntent.FLAG_UPDATE_CURRENT));
	           Toast.makeText(this, "Activity Scheduled", Toast.LENGTH_LONG).show();
	         
	    }
	
	
	///////////////////////////////////////////////////////////////////////////
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timesetter, menu);
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
