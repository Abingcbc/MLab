package org.sse.authservice.controller;

import com.alibaba.fastjson.JSONObject;
import jdk.security.jarsigner.JarSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.sse.authservice.model.Result;
import org.sse.authservice.model.UserAuthInfo;
import org.sse.authservice.service.AuthService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author cbc
 */
@RestController
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public void createNewUser(@RequestBody UserAuthInfo user,
                              HttpServletResponse response) {
        int status = authService.register(user.getUsername(),
                user.getPassword(), user.getEmail());
        if (status == -1) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else if (status == 0){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

//    @PostMapping(value = "/user/update")
//    public Result updateUserInfo(@RequestBody JSONObject jsonObject) {
//
//    }

}
