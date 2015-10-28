package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDTO {

    private Long id;
    private String title;
    private String multiplier;
    private Integer hours;

    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String title, String multiplier, Integer hours) {
        this.id = id;
        this.title = title;
        this.multiplier = multiplier;
        this.hours = hours;
    }
}
