package com.nas.fireevacuation.activity.evacutation.model.evacuation_student_model

//data class EvacuationStudentModel(
//    var found: String,
//    var id: String,
//    var photo: String,
//    var present: String,
//    var registration_id: String,
//    var assembly_point: String,
//    var assembly_point_id: String,
//    var class_id: String,
//    var class_name: String,
//    var created_at: String,
//    var section: String,
//    var staff_id: String,
//    var staff_name: String,
//    var student_name: String,
//    var subject: String,
//    var updated_at: String,
//    var created_by: String,
//    var updated_by: String
//    )

public class EvacuationStudentModel() {

    var found: String = ""
    var id: String = ""
    var photo: String= ""
    var present: String= ""
    var registration_id: String= ""
    var assembly_point: String= ""
    var assembly_point_id: String= ""
    var class_id: String= ""
    var class_name: String= ""
    var created_at: String= ""
    var section: String= ""
    var staff_id: String= ""
    var staff_name: String= ""
    var student_name: String= ""
    var subject: String= ""
    var updated_at: String= ""
    var created_by: String= ""
    var updated_by: String= ""

    constructor(
        found: String,
        id: String,
        photo: String,
        present: String,
        registration_id: String,
        assembly_point: String,
        assembly_point_id: String,
        class_id: String,
        class_name: String,
        created_at: String,
        section: String,
        staff_id: String,
        staff_name: String,
        student_name: String,
        subject: String,
        updated_at: String,
        created_by: String,
        updated_by: String
    ) : this() {
        this.found = found
        this.id = id
        this.photo = photo
        this.present = present
        this.registration_id = registration_id
        this.assembly_point = assembly_point
        this.assembly_point_id = assembly_point_id
        this.class_id = class_id
        this.class_name = class_name
        this.created_at = created_at
        this.section = section
        this.staff_id = staff_id
        this.staff_name = staff_name
        this.student_name = student_name
        this.subject = subject
        this.updated_at = updated_at
        this.created_by = created_by
        this.updated_by = updated_by
    }




}






