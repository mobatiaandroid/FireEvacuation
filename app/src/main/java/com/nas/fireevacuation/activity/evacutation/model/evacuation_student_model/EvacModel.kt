package com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model

data class EvacModel(
    val assembly_point_id: String,
    val class_id: String,
    val staff_id: String,
    val students: List<StudentX>
)