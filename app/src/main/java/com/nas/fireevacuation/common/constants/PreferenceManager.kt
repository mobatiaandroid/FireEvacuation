package com.nas.fireevacuation.common.constants

import android.content.Context
import android.content.SharedPreferences
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel

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
        fun setAbsentList(context: Context, absentList:ArrayList<Lists>) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("absent_list", absentList.toString())
            editor.apply()
        }
        fun getAbsentList(context: Context): String {
            val absentList: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            absentList = sharedPreferences.getString("absent_list", "").toString()
            return absentList
        }
        fun setPresentList(context: Context, presentList:ArrayList<Lists>) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("present_list", presentList.toString())
            editor.apply()
        }
        fun getPresentList(context: Context): String {
            val presentList: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            presentList = sharedPreferences.getString("present_list", "").toString()
            return presentList
        }
        fun setStudentList(context: Context, studentlist:ArrayList<Lists>) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("student_list", studentlist.toString())
            editor.apply()
        }
        fun getStudentList(context: Context): String {
            val studentlist: String
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            studentlist = sharedPreferences.getString("student_list", "").toString()
            return studentlist
        }
    }
}