package com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model

data class EvacuationStudentModel(
    var found: String,
    var id: String,
    var name: String,
    var photo: String,
    val present: String,
    val registration_id: String,
    var assembly_point: String,
    val assembly_point_id: String,
    val class_id: String,
    val class_name: String,
    val created_at: String,
    val section: String,
    val staff_id: String,
    val staff_name: String,
    val student_name: String,
    val subject: String,
    val updated_at: String
)

