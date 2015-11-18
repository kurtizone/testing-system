package com.testing.edu.controller;

import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/application/")
/**
 * Used in main application form (application-sending.html)
 * for creating verifications
 * and sending notifications about that to customerID's email
 */
public class ClientApplicationController {

    Logger logger = Logger.getLogger(ClientApplicationController.class);

    @Autowired
    private UserService usersService;

    @Autowired
    private GroupsService groupsService;

    @RequestMapping(value = "users/available/username/{username}", method = RequestMethod.GET)
    public Boolean isValidUsername(@PathVariable String username) {
        boolean isAvailable = false;
        if (username != null) {
            isAvailable = usersService.isUsernameExist(username);
        }
        return isAvailable;
    }

    /**
     * return all groups
     * @return ist of groups wrapped into DeviceLightDTO
     */
    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public List<GroupDTO> getAll() {
        return groupsService.getAllGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getTitle()))
                .collect(Collectors.toList());
    }

}
