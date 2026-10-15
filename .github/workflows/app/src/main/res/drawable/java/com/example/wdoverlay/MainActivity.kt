package com.example.wdoverlay

import android.app.Activity
import android.graphics.Color
import android.widget.ScrollView
import android.widget.TextView

object CrashCatcher {
    fun install(a: Activity) {
        val prefs = a.getSharedPreferences("crash", Activity.MODE_PRIVATE)
        val old = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            try {
                prefs.edit().putString("trace", android.util.Log.getStackTraceString(e)).commit()
            } catch (_: Throwable) {
            }
            old?.uncaughtException(t, e)
        }
    }

    /** True if the last run crashed; the error is then shown on screen. */
    fun show(a: Activity): Boolean {
        val prefs = a.getSharedPreferences("crash", Activity.MODE_PRIVATE)
        val trace = prefs.getString("trace", null) ?: return false
        prefs.edit().remove("trace").commit()
        val tv = TextView(a).apply {
            text = "BBBlabs crashed last time:\n\n" + trace.take(3000)
            setTextColor(Color.WHITE)
            textSize = 12f
            setPadding(30, 30, 30, 30)
            setTextIsSelectable(true)
        }
        a.setContentView(ScrollView(a).apply { setBackgroundColor(Color.BLACK); addView(tv) })
        return true
    }
}
