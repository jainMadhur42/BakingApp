package com.example.madhurjain.autoringtonechange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ${Madhur} on 1/30/2017.
 */

public class MessageReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase("message_auto_send_successfully")) {
            Toast.makeText(context, "Sent Successfully", Toast.LENGTH_LONG).show();
        } else if (action.equalsIgnoreCase("message_auto_send_unsuccessfully")) {
            Toast.makeText(context, "Not Sent Successfully", Toast.LENGTH_LONG).show();
        }


    }
}
