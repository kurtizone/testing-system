package com.testing.edu.controller;

import com.testing.edu.dto.admin.UsersPageItem;
import com.testing.edu.entity.User;
import com.testing.edu.service.UserService;
import com.testing.edu.service.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class WelcomeController {

    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private UserService userService;

    


    /**
     * Responds details about current principal.
     *
     * @param principal - a user who made a request
     * @return "guest" if not authenticated or UserDetails for authenticated user.
     */
    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public Object getUser(Principal principal) {
        return principal == null ? null : securityUserDetailsService.loadUserByUsername(principal.getName());
    }

    @RequestMapping(value = "/loginuser", method = RequestMethod.GET)
    public UsersPageItem getEmployee(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails userDetails) {
        UsersPageItem usersPageItem = new UsersPageItem();
        User user =  userService.findByUsername(userDetails.getUsername());
        usersPageItem.setUsername(user.getUsername());
        usersPageItem.setEmail(user.getEmail());
        return  usersPageItem;
    }
    
    /**
     * Find the role of the login user
     *
     * @return role
     */

}

