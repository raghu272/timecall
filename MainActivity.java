package com.example.raghu272.timecall;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;

public class MainActivity extends ActionBarActivity {
   Button btn;
   EditText txtAct;      //to save the activity input by the user
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        txtAct = (EditText)findViewById(R.id.editText1);
        btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	//Check if the input textbox is empty or not
            	if (txtAct.getText().toString().matches("")) {
                    Toast.makeText(getBaseContext(), "Input cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),Timesetter.class);
                startActivity(i);
                //////along with starting a new activity store the user data from text box in shared preferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ACTIVITY", txtAct.getText().toString());
                editor.commit();
            }
        });
    }


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
