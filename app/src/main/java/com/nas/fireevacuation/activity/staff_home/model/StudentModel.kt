package com.nas.fireevacuation.activity.staff_home.model

data class StudentModel(
    val `data`: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)