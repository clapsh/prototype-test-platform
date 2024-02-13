package gp.gameproto.service;

import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.AddUserRequest;
import gp.gameproto.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String save(AddUserRequest dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()){
            return "이메일이 존재합니다.";
        }
        System.out.println("UserService.save"+ dto);
        userRepository.save(User.builder()
                .email(dto.getEmail())
                .pw(bCryptPasswordEncoder.encode(dto.getPw()))
                .name(dto.getName())
                .bio(dto.getBio())
                .gender(dto.getGender())
                .nation(dto.getNation())
                .age(dto.getAge())
                .imgPath(dto.getImgPath())
                .createdAt(dto.getCreatedAt())
                .favCategory1(dto.getFavCategory1())
                .favCategory2(dto.getFavCategory2())
                .favCategory3(dto.getFavCategory3())
                .build());
        return "회원가입 성공";
    }

    @Transactional(readOnly=true)
    public String login(UserLoginRequest dto){
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()){
            return "해당 유저를 찾지 못했습니다.";
        }
        if (bCryptPasswordEncoder.matches(dto.getPw(), user.get().getPassword())){
            return "로그인 성공!";
        }
        return "비밀번호가 일치하지 않습니다.";
    }

}

