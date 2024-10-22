package com.jurini.Learning.Aws;


import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Data
@Getter
@Service
public class aws {

    private String accesskey;
    private String secrectkey;
    private String bucketname;

}
