package org.sse.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sse.authservice.model.UserAuthInfo;
import org.sse.authservice.service.AuthService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author cbc
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public void createNewUser(@RequestBody UserAuthInfo userAuthInfo,
                              HttpServletResponse response) {
        int status = authService.register(userAuthInfo.getUsername(),
                userAuthInfo.getPassword());
        if (status == -1) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

//    @PostMapping(value = "/login")
//    public void login() {
//
//    }
}
