package com.nas.fireevacuation.activity.sign_in.model.signin_model

data class SignInModel(
    val data: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)