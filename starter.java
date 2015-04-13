package com.example.raghu272.timecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class starter extends BroadcastReceiver {


    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "instarter", Toast.LENGTH_SHORT).show();
        Intent newIntent = new Intent(context, CallblockActivate.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }


}
