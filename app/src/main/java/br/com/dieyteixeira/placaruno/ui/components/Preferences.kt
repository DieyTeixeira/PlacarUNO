package br.com.dieyteixeira.placaruno.ui.components

import android.content.Context
import android.content.SharedPreferences
import br.com.dieyteixeira.placaruno.ui.screens.ButtonLayout

object PreferenceManager {
    private const val PREFS_NAME = "app_preferences"
    private const val LAYOUT_KEY = "layout_key"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveLayout(context: Context, layout: ButtonLayout) {
        val editor = getPreferences(context).edit()
        editor.putString(LAYOUT_KEY, layout.name)
        editor.apply()
    }

    fun getSavedLayout(context: Context): ButtonLayout {
        val prefs = getPreferences(context)
        val layoutName = prefs.getString(LAYOUT_KEY, ButtonLayout.COLUMN.name)
        return ButtonLayout.valueOf(layoutName ?: ButtonLayout.COLUMN.name)
    }
}
