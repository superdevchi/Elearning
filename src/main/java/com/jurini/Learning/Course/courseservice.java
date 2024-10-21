package com.jurini.Learning.Course;


import com.jurini.Learning.Aws.awsservice;
import com.jurini.Learning.Course.coursemodels.course_student;
import com.jurini.Learning.Course.coursemodels.coursedata;
import com.jurini.Learning.Course.coursemodels.courseurldata;
import com.jurini.Learning.User.UserData.userdata;
import com.jurini.Learning.User.UserData.userdatarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class courseservice {

    @Autowired
    userdatarepository userrepository;

    @Autowired
    com.jurini.Learning.Course.courserepositories.courserepository courserepository;

    @Autowired
    com.jurini.Learning.Course.courserepositories.courseurlrepository courseurlrepository;

    @Autowired
    com.jurini.Learning.Course.courserepositories.course_student_repository course_student_repository;

    @Autowired
    awsservice awsservice;



    public coursedata createcourse(Integer admin, coursedata course) {

        Optional<userdata> userdata = userrepository.findById(admin);
         if (userdata.isEmpty()) {
             System.out.println("user not found");
         }
       course.setTeacher(userdata.get());
        return courserepository.save(course);
    }

    public List<coursedata> getAllCourses() {
        return courserepository.findAll();
    }

    public courseurldata upload(Integer ID, MultipartFile file) {
        courseurldata courseurldata = new courseurldata();
        coursedata course = courserepository.findById(ID).get();
        if (course != null) {
            courseurldata.setCoursedata(course);
            if(file != null) {
                //upload to aws and get link
                awsservice.uploadFileImage(file);
                courseurldata.setUrl(awsservice.GETURL());
            }else{System.out.println("file is null"); return null;}
        }else {System.out.println("course not found"); return null;}
        courseurlrepository.save(courseurldata);
        return courseurldata;
    }

    public course_student joincourse(Integer studentid, Integer courseid) {
        course_student enroll = new course_student();
        coursedata course = courserepository.findById(courseid).get();

        if (course != null) {
            userdata user = userrepository.findById(studentid).get();
            if (user != null) {

                enroll.setCourse(course);
                enroll.setStudent(user);
            }else{System.out.println("user not found"); return null;}
        }else {System.out.println("course not found"); return null;}
        course_student_repository.save(enroll);
        return enroll;
    }

    public List<course_student>findbystudentid(Integer studentid) {
        return course_student_repository.findCoursesByStudentId(studentid);
    }
}
