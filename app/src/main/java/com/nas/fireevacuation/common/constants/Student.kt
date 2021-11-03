package com.nas.fireevacuation.common.constants

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Student(
    @PrimaryKey val sid: Int,
    @ColumnInfo(name = "class_id") val classID: String,
    @ColumnInfo(name = "student_id") val studentID: String,
    @ColumnInfo(name = "student_name") val studentName: String,
    @ColumnInfo(name = "present") val present: String,
    @ColumnInfo(name = "found") val found: String,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "registration_id") val registrationID: String
)
