package com.kiof.boussole.global

import android.content.Context
import androidx.preference.PreferenceManager

class PrefUtil {
    companion object {

        private const val REMOVE_ADS_ID = "com.kiof.boussole.remove_ads"

        fun getRemoveAds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(REMOVE_ADS_ID, 0)
        }

        fun setRemoveAds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(REMOVE_ADS_ID, seconds)
            editor.apply()
        }
    }
}