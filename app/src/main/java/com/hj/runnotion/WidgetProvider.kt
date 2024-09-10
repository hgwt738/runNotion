package com.hj.runnotion

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val sharedPreferences = context.getSharedPreferences("MyWidgetPrefs", Context.MODE_PRIVATE)
        val notionURL = sharedPreferences.getString("notion_url", Constants.DEFAULT_URL)

        for (id in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Notion 링크 세팅
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(notionURL))
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)

            // 현 날짜, 주 계산
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("MMMM", Locale.KOREA)
            val currentMonth = dateFormat.format(calendar.time)
            val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)

            val dateText = "$currentMonth $weekOfMonth Week"
            views.setTextViewText(R.id.widget_text, dateText)

            appWidgetManager.updateAppWidget(id, views)
        }
    }
}