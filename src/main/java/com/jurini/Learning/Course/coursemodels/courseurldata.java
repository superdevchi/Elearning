package com.jurini.Learning.Course.coursemodels;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courseurl")
public class courseurldata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursedata_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private coursedata coursedata;

}
