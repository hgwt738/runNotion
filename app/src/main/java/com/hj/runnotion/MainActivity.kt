package com.hj.runnotion

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.hj.runnotion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyWidgetPrefs", Context.MODE_PRIVATE)

        val savedLink = sharedPreferences.getString("notion_url", Constants.DEFAULT_URL)
        binding.root.findViewById<EditText>(R.id.link_input).setText(savedLink)

        binding.root.findViewById<Button>(R.id.save_button).setOnClickListener {
            val newLink = binding.root.findViewById<EditText>(R.id.link_input).text.toString()
            saveLink(newLink)
        }
    }

    private fun saveLink(link: String) {
        with(sharedPreferences.edit()) {
            putString("notion_url", link)
            apply()
        }
        updateWidget()
    }

    private fun updateWidget() {
        val intent = Intent(this, WidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        }
        val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(
            ComponentName(application, WidgetProvider::class.java)
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}