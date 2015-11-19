package com.testing.edu.dto.admin;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private Long groupId;
    private String groupTitle;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Boolean enable;

    public StudentDTO() {

    }

    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.numberGradebook = numberGradebook;
    }
    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook, Long groupId,
                      String groupTitle, String username, String email, String phone) {
        this(id, lastName, firstName, middleName, numberGradebook, groupId, groupTitle);
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook,
                      String groupTitle, String username, String email, String phone, Boolean enable) {
        this(id, lastName, firstName, middleName, numberGradebook, null, groupTitle, username, email, phone);
        this.enable = enable;
    }
    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook,
                      Long groupId, String groupTitle, String username, String email, String phone, Boolean enable) {
        this(id, lastName, firstName, middleName, numberGradebook, groupId, groupTitle, username, email, phone);
        this.enable = enable;
    }



    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook, Long groupId) {
        this(id, lastName, firstName, middleName, numberGradebook);
        this.groupId = groupId;
    }

    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook, Long groupId,
                      String groupTitle) {
        this(id, lastName, firstName, middleName, numberGradebook);
        this.groupId = groupId;
        this.groupTitle = groupTitle;
    }

    public StudentDTO(Long id, String lastName, String firstName, String middleName, String numberGradebook,
                      String groupTitle) {
        this(id, lastName, firstName, middleName, numberGradebook);
        this.groupTitle = groupTitle;
    }
}
