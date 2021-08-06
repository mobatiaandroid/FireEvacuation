package com.nas.fireevacuation.common.constants


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


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
    @POST("api/StaffApp/signup")
    fun signUpAPICall(
        @Field("access_token") accessToken: String,
        @Field("email") emailID: String,
        @Field("deviceid") deviceID: String,
        @Field("devicetype") deviceType: String
    ): Call<ResponseBody>

    /***Log In***/
    @FormUrlEncoded
    @POST("")
    fun loginAPICall(
        @Field("access_token") accessToken: String,
        @Field("email") emailID: String,
        @Field("password") password: String,
        @Field("deviceid") deviceID: String,
        @Field("devicetype") deviceType: String
    ): Call<ResponseBody>
}