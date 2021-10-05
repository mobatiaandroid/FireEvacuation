package com.nas.fireevacuation.activity.evacutation.model.post

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    var found: String = "",
    var id: String = "",
    var photo: String = "",
    var present: String = "",
    var registration_id: String = "",
    var assembly_point: String = "",
    var assembly_point_id: String = "",
    var class_id: String = "",
    var class_name: String = "",
    var created_at: String = "" ,
    var section: String = "",
    var staff_id: String = "",
    var staff_name: String = "",
    var student_name: String = "",
    var subject: String= "",
    var updated_at: String = "",
    var created_by: String = "",
    var updated_by: String = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "found" to found,
            "id" to id,
            "photo" to photo,
            "present" to present,
            "registration_id" to registration_id,
            "assembly_point" to assembly_point,
            "assembly_point_id" to assembly_point_id,
            "class_id" to class_id,
            "class_name" to class_name,
            "created_at" to created_at,
            "section" to section,
            "staff_id" to staff_id,
            "student_name" to student_name,
            "subject" to subject,
            "updated_at" to updated_at,
            "created_by" to created_by,
            "updated_by" to updated_by

        )
    }
}
