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
    public ResponseEntity login(@RequestBody User user) {
        String uuid;

        Optional<User> optionalUser = securityService.authenticate(user);

        if (optionalUser.isPresent()){
            uuid = securityService.createSession(optionalUser.get());

            UserVO userVO = new UserVO(uuid, optionalUser.get().getNickname());
            logger.info("User with uuid = {} successfully logged in", uuid);
            return ResponseEntity.ok(userVO);
        }
        logger.error("Session of user: {} is not valid", user.getEmail());
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity logout(@RequestHeader("uuid") String uuid) {

        boolean isLoggedOut = securityService.logoutSession(uuid);
        if (isLoggedOut){
            logger.info("User with uuid = {} successfully logout", uuid);
            return ResponseEntity.ok().build();
        }
        logger.error("Session with uuid = {} is not valid", uuid);
        return ResponseEntity.badRequest().build();
    }
}
