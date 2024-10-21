package com.jurini.Learning.Course.courserepositories;

import com.jurini.Learning.Course.coursemodels.courseurldata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface courseurlrepository extends JpaRepository<courseurldata,Integer> {
}
