package com.nas.fireevacuation.activity.staff_home.model.student_attendance_model

data class StudentAttendanceModel(
    val `data`: Data,
    val message: String,
    val responsecode: String,
    val validation_errors: List<Any>
)