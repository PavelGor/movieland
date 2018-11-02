package com.gordeev.movieland.controller;

import com.gordeev.movieland.entity.User;
import com.gordeev.movieland.service.SecurityService;
import com.gordeev.movieland.service.impl.security.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SecurityController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    protected ResponseEntity login(@RequestBody User user) {
        String uuid;
        //if user exist in DB
        Optional<User> optionalUser = securityService.authenticate(user);
        //add him to cache
        if (optionalUser.isPresent()){
            uuid = securityService.createSession(optionalUser.get());
            //and generate UserVO
            UserVO userVO = new UserVO(uuid, optionalUser.get().getNickname());
            logger.info("User with uuid = {} successfully logged in", uuid);
            return ResponseEntity.ok(userVO);
        }
        logger.info("Session of user: {} is not valid", user.getEmail());
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    protected ResponseEntity logout(@RequestHeader("uuid") String uuid) {

        boolean isLoggedOut = securityService.logoutSession(uuid);
        if (isLoggedOut){
            logger.info("User with uuid = {} successfully logout", uuid);
            return ResponseEntity.ok().build();
        }
        logger.info("Session with uuid = {} is not valid", uuid);
        return ResponseEntity.badRequest().build();
    }
}
