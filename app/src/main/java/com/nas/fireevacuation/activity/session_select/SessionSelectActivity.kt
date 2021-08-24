package com.nas.fireevacuation.activity.session_select

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.sign_in.SignInActivity
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.Lists
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_home.StaffHomeActivity
import com.nas.fireevacuation.activity.welcome.WelcomeActivity
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.CommonMethods
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionSelectActivity : AppCompatActivity() {
    lateinit var context: Context
    var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_session_select)
        progressBarDialog = ProgressBarDialog(context)
        showSelectSessionPopUp()
    }
    private fun showSelectSessionPopUp() {
        var yearGroupsResponse: YearGroups
        var yearGroupsArrayList: ArrayList<Lists> = ArrayList()
        var i: Int = 0
        var yearGroupsList: ArrayList<String> = ArrayList()
        val call: Call<YearGroups> = ApiClient.getClient.yearGroupsAPICall(
            PreferenceManager.getAccessToken(context)
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<YearGroups> {
            override fun onResponse(call: Call<YearGroups>, response: Response<YearGroups>) {
                progressBarDialog!!.hide()
                if (!response.body()!!.equals("")) {
                    yearGroupsResponse = response.body()!!
                    if (yearGroupsResponse.responsecode.equals("200")) {
                        if (yearGroupsResponse.response.response.equals("success")) {
                            if (yearGroupsResponse.response.statuscode.equals("303")) {
                                yearGroupsArrayList = yearGroupsResponse.response.data.lists as ArrayList<Lists>
                                while(i<yearGroupsArrayList.size) {
                                    yearGroupsList.add(yearGroupsArrayList[i].year_group)
                                    i++
                                }

                            }
                        }
                    } else if(yearGroupsResponse.responsecode.equals("402")) {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Invalid Access Token")
                        CommonMethods.getAccessTokenAPICall(context)
                    } else {
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
                    }
                }
            }
            override fun onFailure(call: Call<YearGroups>, t: Throwable) {
                progressBarDialog!!.hide()
                CommonMethods.showLoginErrorPopUp(context,"Alert","Some Error Occurred")
            }

        })
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.session_select_popup)
        dialog.setCancelable(false)
        var sessionSelect = dialog.findViewById<View>(R.id.sessionSelect)
        var subjectSelect = dialog.findViewById<View>(R.id.subjectSelect)
        var selectedSession = dialog.findViewById<View>(R.id.selectedSession) as TextView
        var selectedSubject = dialog.findViewById<View>(R.id.selectedSubject) as TextView
        var closeButton = dialog.findViewById<View>(R.id.close)
        var checkInButton = dialog.findViewById<View>(R.id.checkIn)
        var position = -1
        dialog.show()
        sessionSelect.setOnClickListener {
            var yearGroupsSelector: Array<String> = yearGroupsList.toTypedArray()
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Select Session")
            var checkedItem = -1
            builder.setSingleChoiceItems(yearGroupsSelector, checkedItem) { dialog, which ->
                checkedItem = which
            }
            builder.setPositiveButton("OK") { dialog, which ->
                                    selectedSession.text = yearGroupsSelector[checkedItem]
                                    position = checkedItem
            }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
        closeButton.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
        checkInButton.setOnClickListener {
            if(selectedSession.text.equals("")) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Please Select Session")
            } else {
                val intent = Intent(context, StaffHomeActivity::class.java)
                intent.putExtra("classID", yearGroupsArrayList[position].id)
                intent.putExtra("className", selectedSession.text)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()
            }
        }
    }
}