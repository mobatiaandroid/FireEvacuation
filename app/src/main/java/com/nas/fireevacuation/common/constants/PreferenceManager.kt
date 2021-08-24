package com.nas.fireevacuation.common.constants

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {
    companion object {
        private const val sharedPrefNas = "NAS_EVAC"
        fun setAccessToken(context: Context, accessToken: String) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("access_token", accessToken)
            editor.apply()
        }
        fun getAccessToken(context: Context?): String {
            val tokenValue: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            tokenValue = sharedPreferences.getString("access_token", "").toString()
            return tokenValue
        }
        fun setStaffName(context: Context, staffName: String) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("staff_name", staffName)
            editor.apply()
        }
        fun getStaffName(context: Context?): String {
            val staffName: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            staffName = sharedPreferences.getString("staff_name", "").toString()
            return staffName
        }
        fun setStaffID(context: Context, staffID: String) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("staff_id", staffID)
            editor.apply()
        }
        fun getStaffID(context: Context?): String {
            val staffID: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            staffID = sharedPreferences.getString("staff_id", "").toString()
            return staffID
        }
    }
}