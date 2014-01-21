package dev.kataev.wagnerclock;

import java.util.Calendar;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class WagnerClock extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, 1000, 
        		PendingIntent.getBroadcast(context, 0, new Intent("dev.kataev.wagnerclock.CLOCK_UPDATE"), PendingIntent.FLAG_UPDATE_CURRENT));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
    {
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(PendingIntent.getBroadcast(context, 0, new Intent("dev.kataev.wagnerclock.CLOCK_UPDATE"), PendingIntent.FLAG_UPDATE_CURRENT));
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals("dev.kataev.wagnerclock.CLOCK_UPDATE"))
        {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WagnerClock.class));
            for(int appWidgetID : appWidgetIds) {
                
           	RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.wagner_wigdet_layout);

            Calendar calendar = Calendar.getInstance();
                 
            
            
            remoteViews.setTextViewText(R.id.clock_string, 
                		String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND)));
                
                
            try {appWidgetManager.updateAppWidget(appWidgetID, remoteViews); } catch(Exception e) {}
            }
        }
    }
	
}
