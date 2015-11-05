package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String numberGradebook;

    public StudentDTO() {

    }

    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.numberGradebook = numberGradebook;
    }
}
