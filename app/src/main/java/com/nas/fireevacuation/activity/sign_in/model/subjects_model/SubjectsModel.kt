package com.nas.fireevacuation.activity.sign_in.model.subjects_model

data class SubjectsModel(
    val `data`: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)