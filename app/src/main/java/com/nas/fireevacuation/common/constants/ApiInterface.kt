package com.nas.fireevacuation.common.constants


import com.nas.fireevacuation.activity.create_account.model.CreateAccountModel
import com.nas.fireevacuation.activity.evacutation.model.evacuation_model.EvacuationModel
import com.nas.fireevacuation.activity.my_profile.model.LogoutModel
import com.nas.fireevacuation.activity.sign_in.model.signin_model.SignInModel
import com.nas.fireevacuation.activity.sign_in.model.subjects_model.SubjectsModel
import com.nas.fireevacuation.activity.sign_in.model.year_groups_model.YearGroups
import com.nas.fireevacuation.activity.staff_home.model.assembly_points_model.AssemblyPointsModel
import com.nas.fireevacuation.activity.staff_home.model.student_attendance_model.StudentAttendanceModel
import com.nas.fireevacuation.activity.staff_home.model.students_model.StudentModel
import okhttp3.ResponseBody
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
    ): Call<AssemblyPointsModel>

    /***Evacuation Start***/
    @FormUrlEncoded
    @POST("api/StaffApp/evacuation_start_V1")
    fun evacuationStart(
        @Field("access_token") accessToken: String,
        @Field("staff_id") staffID: String,
        @Field("class_id") classID: String,
        @Field("assembly_point_id") assemblyID: String
    ): Call<EvacuationModel>

    /***Log Out***/
    @FormUrlEncoded
    @POST("api/StaffApp/logout_V1")
    fun logOut(
        @Field("access_token") accessToken: String,
        @Field("staff_id") staffID: String,
        @Field("deviceid") deviceID: String,
        @Field("devicetype") deviceType: String
    ): Call<LogoutModel>

    /***Change Password***/
    @FormUrlEncoded
    @POST("api/StaffApp/change_password_V1")
    fun changePassword(
        @Field("access_token") accessToken: String,
        @Field("staff_id") staffID: String,
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPassword: String
    ): Call<ResponseBody>

    /***Recover Account***/
    @FormUrlEncoded
    @POST("api/StaffApp/forgot_password_V1")
    fun forgotPassword(
        @Field("access_token") accessToken:String,
        @Field("email") emailID: String
    ): Call<ResponseBody>

    /***Mark Attendance***/
    @FormUrlEncoded
    @POST("api/StaffApp/mark_attendance")
    fun markAttendance(
        @Field("access_token") accessToken: String,
        @Field("student_id") studentID: String,
        @Field("present") present: String
    ): Call<ResponseBody>

    /***Subjects***/
    @FormUrlEncoded
    @POST("api/StaffApp/subjects")
    fun subjectsAPICall(
        @Field("access_token") accessToken: String
    ): Call<SubjectsModel>

    /***Albums***/
    @FormUrlEncoded
    @POST("api/StaffApp/albums")
    fun albumsAPICall(
        @Field("access_token") accessToken: String,
        @Field("page_number") pageNumber: String
    ): Call<ResponseBody>

    /***Photos***/
    @FormUrlEncoded
    @POST("api/StaffApp/photos")
    fun photosAPICall(
        @Field("access_token") accessToken: String,
        @Field("album_id") albumID: String,
        @Field("page_number") pageNumber: String
    ): Call<ResponseBody>

    /***Photos***/
    @FormUrlEncoded
    @POST("api/StaffApp/videos")
    fun videosAPICall(
        @Field("access_token") accessToken: String,
        @Field("page_number") pageNumber: String
    ): Call<ResponseBody>

    /***Update Attendance***/
    @FormUrlEncoded
    @POST("api/StaffApp/mark_attendance")
    fun attendanceUpdate(
        @Field("access_token") accessToken: String,
        @Field("student_id") studentID: String,
        @Field("present") present: String
    ): Call<StudentAttendanceModel>
}