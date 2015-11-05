package com.testing.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Routing between pages
 */
@Controller
public class Router {

    /**
     * Displays welcome page
     *
     * @return welcome page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "/resources/app/welcome/index.html";
    }

    /**
     * Displays admin page
     *
     * @return admin page
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "/resources/app/admin/index.html";
    }

    /**
     * Displays student page
     *
     * @return student page
     */
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student() {
        return "/resources/app/student/index.html";
    }

    /**
     * Displays student page
     *
     * @return student page
     */
    @RequestMapping(value = "/lecturer", method = RequestMethod.GET)
    public String lecturer() {
        return "/resources/app/lecturer/index.html";
    }

}
