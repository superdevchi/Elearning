package com.jurini.Learning.Course.courserepositories;

import com.jurini.Learning.Course.coursemodels.coursedata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface courserepository extends JpaRepository<coursedata, Integer> {
}
