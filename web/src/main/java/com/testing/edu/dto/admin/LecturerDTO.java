package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String academicStatus;
    private String degree;
    private String username;
    private String email;
    private String phone;

    public LecturerDTO() {

    }

    public LecturerDTO(Long id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public LecturerDTO(Long id, String lastName, String firstName, String middleName,
                       String academicStatus, String degree) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.academicStatus = academicStatus;
        this.degree = degree;
    }
}
