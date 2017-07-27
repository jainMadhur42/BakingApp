package anonestep.com.backingapp.WidgetServices;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Madhur Jain on 7/21/2017.
 */

public class WidgetServices extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager
                .INVALID_APPWIDGET_ID);
        return (new ListProvider(getBaseContext(), intent));
    }
}
