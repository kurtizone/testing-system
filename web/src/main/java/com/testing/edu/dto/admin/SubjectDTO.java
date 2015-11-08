package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectDTO {

    private Long id;
    private String title;
    private String multiplier;
    private Integer hours;
    private Long numberOfGroups;
    private Long numberOfTests;


    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String title, String multiplier, Integer hours) {
        this.id = id;
        this.title = title;
        this.multiplier = multiplier;
        this.hours = hours;
    }

    public SubjectDTO(Long id, String title, String multiplier, Integer hours, Long numberOfGroups, Long numberOfTests) {
        this(id, title, multiplier, hours);
        this.numberOfGroups = numberOfGroups;
        this.numberOfTests = numberOfTests;
    }

}
