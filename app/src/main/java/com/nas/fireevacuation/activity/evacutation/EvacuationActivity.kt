package com.nas.fireevacuation.activity.evacutation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.create_account.model.CreateAccountModel
import com.nas.fireevacuation.activity.evacutation.model.EvacuationModel
import com.nas.fireevacuation.common.constants.ApiClient
import com.nas.fireevacuation.common.constants.PreferenceManager
import com.nas.fireevacuation.common.constants.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvacuationActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var firebaseID: String
    lateinit var firebaseReference: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evacuation)
        context = this
        progressBarDialog = ProgressBarDialog(context)
        firebaseID = String()
        firebaseReference = String()
        evacuationCall()
    }

    private fun evacuationCall() {
        var evacuationResponse: EvacuationModel
        val call: Call<EvacuationModel> = ApiClient.getClient.evacuationStart(
            PreferenceManager.getAccessToken(context),
            PreferenceManager.getStaffID(context),
            PreferenceManager.getClassID(context),
            PreferenceManager.getAssemblyPoint(context)
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<EvacuationModel> {
            override fun onResponse(
                call: Call<EvacuationModel>,
                response: Response<EvacuationModel>){
                progressBarDialog!!.hide()
                if(!response.body()!!.equals("")) {
                    evacuationResponse = response.body()!!
                    if (evacuationResponse.responsecode.equals("100")) {
                        firebaseReference = evacuationResponse.data.firebase_referance
                        firebaseID = evacuationResponse.data.id.toString()
                    }
                }
            }

            override fun onFailure(call: Call<EvacuationModel>, t: Throwable) {
                progressBarDialog!!.hide()
            }
        })
    }
}
