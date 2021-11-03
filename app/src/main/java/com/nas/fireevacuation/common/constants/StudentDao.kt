package com.nas.fireevacuation.common.constants

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student)

    @Query("SELECT * FROM student")
    fun getAll(): ArrayList<Student>

    @Query("SELECT * FROM student WHERE class_id = :id AND present = 1")
    fun getStudentByClassID(id: String): ArrayList<Student>

}