package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDTO {

    private Integer id;
    private String title;
    private Integer multiplier;
    private Integer hours;

    public SubjectDTO() {
    }

    public SubjectDTO(Integer id, String title, Integer multiplier, Integer hours) {
        this.id = id;
        this.title = title;
        this.multiplier = multiplier;
        this.hours = hours;
    }
}
