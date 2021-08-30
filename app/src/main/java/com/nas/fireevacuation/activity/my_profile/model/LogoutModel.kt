package com.nas.fireevacuation.activity.my_profile.model

data class LogoutModel(
    val data: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)