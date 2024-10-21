package com.jurini.Learning.Course;


import com.jurini.Learning.Course.coursemodels.course_student;
import com.jurini.Learning.Course.coursemodels.coursedata;
import com.jurini.Learning.Course.coursemodels.courseurldata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/course")
public class coursecontroller {
    @Autowired
    courseservice courseservice;



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/{ID}")
    public ResponseEntity<coursedata>createcourse(@RequestBody coursedata coursedata, @PathVariable Integer ID) {
        return ResponseEntity.ok().body(courseservice.createcourse(ID, coursedata));
    }

    @GetMapping("/listcourse")
    public ResponseEntity<List<coursedata>>updatecourse(){
        return ResponseEntity.ok(courseservice.getAllCourses());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create/{ID}/upload")
    public ResponseEntity<courseurldata>uploadcourse(@PathVariable Integer ID, @PathVariable("file") MultipartFile file) {
        return ResponseEntity.ok(courseservice.upload(ID, file));
    }

    @PostMapping("/join-course/{USERID}/{COURSEID}")
    public ResponseEntity<course_student>joincourse(@PathVariable Integer USERID, @PathVariable Integer COURSEID) {
        return ResponseEntity.ok(courseservice.joincourse(USERID,COURSEID));
    }

    @GetMapping("/confirmentrollment/{STUDENTID}")
    public ResponseEntity<List<course_student>>verifyenrollment(@PathVariable Integer STUDENTID) {
        return ResponseEntity.ok(courseservice.findbystudentid(STUDENTID));
    }
}
