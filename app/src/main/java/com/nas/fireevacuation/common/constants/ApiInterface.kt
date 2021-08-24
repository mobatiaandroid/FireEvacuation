package com.nas.fireevacuation.common.constants


import com.nas.fireevacuation.activity.create_account.model.CreateAccountModel
import com.nas.fireevacuation.activity.sign_in.model.signin_model.SignInModel
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_home.model.StudentModel
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {

    /***Access Token Generation***/
    @FormUrlEncoded
    @POST("oauth/access_token")
    fun accessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") userName: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    /***Sign Up***/
    @FormUrlEncoded
    @POST("api/StaffApp/signup_V1")
    fun signUpAPICall(
        @Field("access_token") accessToken: String,
        @Field("email") emailID: String,
        @Field("deviceid") deviceID: String,
        @Field("devicetype") deviceType: String
    ): Call<CreateAccountModel>

    /***Log In***/
    @FormUrlEncoded
    @POST("api/StaffApp/login_V1")
    fun loginAPICall(
        @Field("access_token") accessToken: String,
        @Field("email") emailID: String,
        @Field("password") password: String,
        @Field("deviceid") deviceID: String,
        @Field("devicetype") deviceType: String
    ): Call<SignInModel>

    /***Year Groups***/
    @FormUrlEncoded
    @POST("api/StaffApp/year_groups")
    fun yearGroupsAPICall(
        @Field("access_token") accessToken: String
    ): Call<YearGroups>


    /***Students***/
    @FormUrlEncoded
    @POST("api/StaffApp/students_V1")
    fun  studentsAPICall(
        @Field("access_token") accessToken: String,
        @Field("class_id") classID: String
    ): Call<StudentModel>


    /***Assembly Points***/
    @FormUrlEncoded
    @POST("api/StaffApp/assembly_points_V1")
    fun assemblyPoints(
        @Field("access_token") accessToken: String
    ): Call<ResponseBody>

    /***Evacuation Start***/
    @FormUrlEncoded
    @POST("api/StaffApp/evacuation_start_V1")
    fun evacuationStart(
        @Field("access_token") accessToken: String,
        @Field("staff_id") staffID: String,
        @Field("class_id") classID: String,
        @Field("assembly_point_id") assemblyID: String
    ): Call<ResponseBody>
}