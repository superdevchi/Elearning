package com.jurini.Learning.Course.courserepositories;

import com.jurini.Learning.Course.coursemodels.course_student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface course_student_repository extends JpaRepository<course_student, Integer> {

    @Query(value = "select * from course_student where student_id = ?1" , nativeQuery = true)
     List<course_student> findCoursesByStudentId(Integer studentId);
}
