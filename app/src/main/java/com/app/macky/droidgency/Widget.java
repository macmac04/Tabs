package com.app.macky.droidgency;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    HomeFragment homeFragment = new HomeFragment();
    public static String REFRESH_ACTION = "com.app.macky.droidgency.REFRESH";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {


            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);

        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        final Intent buttonIntent = new Intent(context, Widget.class);
       // buttonIntent.setAction(Widget.REFRESH_ACTION);
        buttonIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        buttonIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        buttonIntent.setData(Uri.parse(buttonIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent buttongPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.btnSOS, buttongPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()==null) {
            Bundle extras = intent.getExtras();
            if(extras!=null) {
                int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                // do something for the widget that has appWidgetId = widgetId
                if (widgetId == 0 ) {
                    Toast.makeText(context, "Action", Toast.LENGTH_LONG).show();

                }
            }
        }
        else {
            super.onReceive(context, intent);
        }
    }
}


