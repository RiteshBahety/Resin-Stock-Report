package com.resin.sms;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Telephony;

/**
 * Launch the default SMS app.
 * Fill the message body with data entered by user.
 * User will fill the recipients before sending the SMS.
 */
public class MessageSender {
    public void sendSMS(String body, Context context) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.putExtra("sms_body", body);

        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context);
        if(defaultSmsPackageName != null)
            smsIntent.setPackage(defaultSmsPackageName);

        try {
            context.startActivity(smsIntent);
        }
        catch(ActivityNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * TODO: Fill text message for Whatsapp, gmail etc
         */
/**
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra("sms_body", body);

        try {
            context.startActivity(shareIntent);
        }
        catch(ActivityNotFoundException e) {
            e.printStackTrace();
        }*/
    }

}
