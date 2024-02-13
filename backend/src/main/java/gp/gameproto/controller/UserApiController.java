package gp.gameproto.controller;

import gp.gameproto.dto.AddUserRequest;
import gp.gameproto.dto.UserLoginRequest;
import gp.gameproto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddUserRequest request){
        String savedResult = userService.save(request);

        HttpStatus httpstatus = HttpStatus.CREATED;
        if (savedResult == "이메일이 존재합니다."){
            httpstatus = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(httpstatus)
                .body(savedResult);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request){
        String loginResult = userService.login(request);

        HttpStatus httpstatus = HttpStatus.OK;
        if (loginResult == "해당 유저를 찾지 못했습니다."){
            httpstatus = HttpStatus.UNAUTHORIZED;
        }
        else if (loginResult == "비밀번호가 일치하지 않습니다."){
            httpstatus = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(httpstatus)
                .body(loginResult);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler()
                .logout(request,response, SecurityContextHolder.getContext().getAuthentication());
    }
}
