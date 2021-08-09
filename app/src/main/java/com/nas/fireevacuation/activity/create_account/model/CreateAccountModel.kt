package com.nas.fireevacuation.activity.create_account.model

import com.nas.fireevacuation.activity.sign_in.model.Response

data class CreateAccountModel(
    val response: Response,
    val responsecode: String
)