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
        fun setClassID(context: Context, classID:String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("class_id", classID)
            editor.apply()
        }
        fun getClassID(context: Context?): String {
            val classID: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            classID = sharedPreferences.getString("class_id", "").toString()
            return classID
        }
        fun setClassName(context: Context, className: String) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("class_name", className)
            editor.apply()
        }
        fun getClassName(context: Context): String {
            val className: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            className = sharedPreferences.getString("class_name", "").toString()
            return className
        }
    }
}