package gp.gameproto.controller;

import gp.gameproto.db.entity.User;
import gp.gameproto.dto.*;
import gp.gameproto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    //회원가입
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

    //로그인
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

    // 로그아웃
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler()
                .logout(request,response, SecurityContextHolder.getContext().getAuthentication());
    }


    // 회원정보 가져오기
    @GetMapping("/user/info/{userid}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("userid") Long id){
        User user = userService.findById(id);
        // else 처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(user));
    }

    // 유저 정보 불러오기(이름, 소개, 이미지)
    @GetMapping("/user/preview/info/{userid}")
    public ResponseEntity<UserPreviewResponse> findPreviewUserById(@PathVariable("userid") Long id){
        User user = userService.findById(id);
        // else 처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserPreviewResponse(user));
    }

    // 유저 정보 수정
    @PutMapping("/user/info/{userid}")
    public ResponseEntity<User> updateUserInfo(@PathVariable("userid") Long id, @RequestBody UpdateUserRequest request){
        // 실패 처리
        User user = userService.updateInfo(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    // 유저 소개글 수정
    @PutMapping("/user/bio/{userid}")
    public ResponseEntity<User> updateUserBio(@PathVariable("userid") Long id, @RequestBody UpdateUserBioRequest request){
        User user = userService.updateBio(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    // 유저 닉네임 수정
    @PutMapping("/user/name/{userid}")
    public ResponseEntity<User> updateUserName(@PathVariable("userid") Long id, @RequestBody UpdateUserNameRequest request){
        User user = userService.updateName(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

}
