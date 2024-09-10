package com.hj.runnotion

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import java.util.*
import kotlin.random.Random

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val sharedPreferences = context.getSharedPreferences("MyWidgetPrefs", Context.MODE_PRIVATE)
        val notionURL = sharedPreferences.getString("notion_url", Constants.DEFAULT_URL)
        val randomLogo = getRandomLogo()

        for (id in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            setNotionLink(context, views, notionURL)

            val dateText = getCurrentDateText()
            views.setTextViewText(R.id.widget_text, dateText)

            views.setImageViewResource(R.id.widget_icon, randomLogo)

            appWidgetManager.updateAppWidget(id, views)
        }
    }

    private fun getCurrentDateText(): String {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val monthText = "${month}월"

        val weekText = when (val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)) {
            1 -> "첫째 주"
            2 -> "둘째 주"
            3 -> "셋째 주"
            4 -> "넷째 주"
            5 -> "다섯째 주"
            else -> "${weekOfMonth}주"
        }

        return "$monthText $weekText"
    }

    private fun setNotionLink(context: Context, views: RemoteViews, notionURL: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(notionURL))
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)
    }

    private fun getRandomLogo(): Int {
        val logoList = arrayOf(
            R.drawable.ic_normal,
            R.drawable.ic_fire,
            R.drawable.ic_water,
            R.drawable.ic_grass,
            R.drawable.ic_electric,
            R.drawable.ic_ice,
            R.drawable.ic_fighting,
            R.drawable.ic_poison,
            R.drawable.ic_ground,
            R.drawable.ic_flying,
            R.drawable.ic_psychic,
            R.drawable.ic_bug,
            R.drawable.ic_rock,
            R.drawable.ic_ghost,
            R.drawable.ic_dragon,
            R.drawable.ic_dark,
            R.drawable.ic_steel,
            R.drawable.ic_fairy
        )
        return logoList[Random.nextInt(logoList.size)]
    }
}
