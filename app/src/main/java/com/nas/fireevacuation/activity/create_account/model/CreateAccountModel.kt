package com.nas.fireevacuation.activity.create_account.model

data class CreateAccountModel(
    val data: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)