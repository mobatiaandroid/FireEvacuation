package com.nas.fireevacuation.common.constants

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


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
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            className = sharedPreferences.getString("class_name", "").toString()
            return className
        }
        fun setAbsentList(context: Context, absentList: ArrayList<Lists>?) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var absentList2: ArrayList<Lists>
            absentList2 = absentList!!
            val gson = Gson()
            if (absentList2 == null) {
                absentList2 = ArrayList()
            }
            val json = gson.toJson(absentList2)
            editor.putString("absent_list", json)
            editor.apply()
        }
        fun getAbsentList(context: Context): ArrayList<Lists> {
            var absentList: ArrayList<Lists> = ArrayList()
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("absent_list", null)
            Log.e("Absent",json.toString())
            val type: Type = object : TypeToken<ArrayList<Lists?>?>() {}.type
            absentList = gson.fromJson<Any>(json, type) as ArrayList<Lists>
            if (absentList == null) {
                absentList = ArrayList()
            }
            return absentList
        }
        fun setPresentList(context: Context, presentList:ArrayList<Lists>) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(presentList)
            editor.putString("present_list", json)
            editor.apply()
        }
        fun getPresentList(context: Context): ArrayList<Lists> {
            val presentList: ArrayList<Lists>
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("present_list", null)
            val type: Type = object : TypeToken<ArrayList<Lists?>?>() {}.type
            presentList = gson.fromJson<Any>(json, type) as ArrayList<Lists>
            return presentList
        }
        fun setStudentList(context: Context, studentlist:ArrayList<Lists>) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(studentlist)
            editor.putString("student_list", json)
            editor.apply()

        }
        fun getStudentList(context: Context): ArrayList<Lists> {
            val studentList: ArrayList<Lists>
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("student_list", null)
            val type: Type = object : TypeToken<ArrayList<Lists?>?>() {}.type
            studentList = gson.fromJson<Any>(json, type) as ArrayList<Lists>
            return studentList
        }

        fun setAssemblyPoints(context: Context, assemblyPointsList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.Lists>) {
            val sharedPreferences: SharedPreferences =
                context!!.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(assemblyPointsList)
            editor.putString("assemblyPointsList", json)
            editor.apply()
        }
        fun getAssemblyPoints(context: Context): ArrayList<com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.Lists> {
            val assemblyPointsList: ArrayList<com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.Lists>
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefNas, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("student_list", null)
            val type: Type = object : TypeToken<ArrayList<Lists?>?>() {}.type
            assemblyPointsList = gson.fromJson<Any>(json, type) as ArrayList<com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.Lists>
            return assemblyPointsList
        }
    }
}